package com.dat.proj.backend.service.impl;

import com.dat.proj.backend.entity.*;
import com.dat.proj.backend.enums.NotificationType;
import com.dat.proj.backend.enums.PaymentMethod;
import com.dat.proj.backend.enums.Status;
import com.dat.proj.backend.repository.*;
import com.dat.proj.backend.util.CinemaGruvhException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GenericServiceImpl {
    private static final String DO_NOT_REPLY_DUMMY_BMS_COM = "doNotReply@dummy.bms.com";

    @Autowired
    private TheatreRepository theaterRepo;

    @Autowired
    private MovieRepository movieRepo;

    @Autowired
    private ScreenRepository screenRepo;

    @Autowired
    private SeatMatrixRepository matrixRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UrlServiceImpl urlService;

    @Autowired
    private NotificationRepository notificationRepo;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public List<String> getSupportedCities() {
        return this.theaterRepo.getSupportdCities();
    }

    /**
     * This service method will return available movies in a city
     *
     * @param city
     * @return
     * @throws JsonProcessingException
     */
    public JSONArray getAvailableMovies(String city) throws JsonProcessingException {
        this.LOGGER.info("getAvailableMovies() called in generic service with city: " + city);
        List<Movie> availableMovies = this.movieRepo.getAvailableMovies(city);
        this.LOGGER.info("getAvailableMovies() got " + availableMovies.size() + " movies in city: " + city);
        JSONArray result = new JSONArray();
        for (Movie movie : availableMovies) {
            ObjectMapper mapper = new ObjectMapper();
            String movieJson = mapper.writeValueAsString(movie);
            JSONObject obj = new JSONObject(Integer.parseInt(movieJson));
            obj.put("updatedOn", movie.getUpdatedOn().toString());
            obj.put("createdOn", movie.getCreatedOn().toString());
            obj.put("activeDateStart", movie.getActiveDateStart().toString());
            obj.put("activeDateEnd", movie.getActiveDateEnd());

            result.add(obj);
        }
        this.LOGGER.info("getAvailableMovies() successfully returned the movies in city: " + city);
        return result;
    }

    /**
     * This service method will return the screen details for a city which are
     * showing a movie below is the returned format of JSON:
     *
     * { "movieId1": [ { "screens1": true }, { "screens2": true } ] }
     *
     * @param movie
     * @param city
     * @return
     * @throws JsonProcessingException
     */
    public JSONObject getScreensShowingMovie(String theaterId, String movieId, String city)
            throws JsonProcessingException {
        this.LOGGER.info(
                "getScreensShowingMovie() getting screens which are showing movie : " + movieId + " in city: " + city);
        List<Theatre> theatersShowingThisMovie = this.theaterRepo.getScreensShowingMovie(theaterId, city);
        this.LOGGER.info("getScreensShowingMovie() got " + theatersShowingThisMovie.size()
                + " screens which are showing movie : " + movieId + " in city: " + city);
        JSONObject result = new JSONObject();
        for (Theatre theater : theatersShowingThisMovie) {
            List<Screen> screens = this.screenRepo.findScreenByTheaterIdAndMovieId(theater.getTheatreId(), movieId);
            JSONArray screensArray = new JSONArray();
            for (Screen screen : screens) {
                ObjectMapper mapper = new ObjectMapper();
                String screenJsonObj = mapper.writeValueAsString(screen);
                JSONObject obj = new JSONObject(Integer.parseInt(screenJsonObj)); // again here created a
                screensArray.add(obj); // just added the add instead of put
            }
            result.put(movieId, screensArray);

        }
        this.LOGGER.info(
                "getScreensShowingMovie() successfully returned screens for movie : " + movieId + " in city: " + city);
        return result;

    }

    /**
     * [ { "seat1A": { "availability": true, "type": "royal" } }, { "seat1A": {
     * "availability": true, "type": "royal" } } ]
     *
     * @param screen
     * @return
     * @throws JsonProcessingException
     */
    public JSONArray getSeatMatrix(String movieId, String theaterId, String screenStartsAt)
            throws JsonProcessingException {
        this.LOGGER.info("getSeatMatrix() : getting available seats for theater :  " + theaterId + " movieId: "
                + movieId + " and start time: " + screenStartsAt);
        List<SeatMatrix> seatsMatrix = this.matrixRepository.getSeatMatrixForscreen(movieId, theaterId, screenStartsAt);
        JSONArray result = new JSONArray();
        this.LOGGER.info("getSeatMatrix() : result size for  available seats for theater :  " + theaterId + " movieId: "
                + movieId + " and start time: " + screenStartsAt + " is " + seatsMatrix.size());
        for (SeatMatrix seat : seatsMatrix) {
            ObjectMapper mapper = new ObjectMapper();
            String seatJson = mapper.writeValueAsString(seat);
            JSONObject obj = new JSONObject(Integer.parseInt(seatJson)); //initially it wasn't parse
            obj.put("createdOn", seat.getCreatedOn().toString());
            obj.put("theaterId", seat.getPrimaryKey().getTheaterId());
            obj.put("movieId", seat.getPrimaryKey().getMovieId());
            obj.put("seatNumber", seat.getPrimaryKey().getSeatNumber());
            obj.put("ShowStartsAt", seat.getPrimaryKey().getScreenStartsAt());
            if (obj.has("primaryKey"))
                obj.remove("primaryKey");
            result.add(obj);
        }
        this.LOGGER.info("getSeatMatrix() : successfully got  available seats for theater :  " + theaterId
                + " movieId: " + movieId + " and start time: " + screenStartsAt);
        return result;
    }

    /**
     * This service method will be responsible for below things: 1. Check if the
     * seats are already booked, if no then book these seats for this user in this
     * theater for this movie 2. do the payment , dummy payment details 3. if
     * payment is success then send the notification related to tickets 4. if
     * payment not success then mark booked seats as unbook , we can also book the
     * seats only if the payment is success but since there is a dummy payment data
     * here so I am just booking seats irrespective of payment status 5. return the
     * booking details to user 6. I am generating a tiny URL and that URL to user so
     * that user can retrieve the tickets
     *
     * Instead of making whole method synchronized , I have made logic of method
     * synchronized to improve performance Why block of this method synchronized: so
     * that not more than 2 threads can access it same time. But for now whole logic
     * is inside synchronized block
     *
     * @param seatsToBook
     * @param userName
     * @return
     * @throws JsonProcessingException
     * @throws JSONException
     */
    public JSONObject bookSeats(List<SeatMatrix> seatsToBook, String userName)
            throws JSONException, JsonProcessingException, CinemaGruvhException {
        StringBuilder sb = new StringBuilder();
        /**
         * TODO : move some of the logic out side synchronized block to optimize
         * performance
         */
        synchronized (seatsToBook) {

            this.LOGGER.info("bookSeats() serivce for username: " + userName);
            int totalPrice = 0;
            // update seat matrix first
            for (SeatMatrix seat : seatsToBook) {
                Optional<SeatMatrix> seatFromDb = this.matrixRepository.findById(seat.getPrimaryKey());
                SeatMatrix oSeatMatrix = seatFromDb.get();
                if (oSeatMatrix != null && oSeatMatrix.isBooked()) {
                    throw new CinemaGruvhException(
                            "The seat number: " + seat.getPrimaryKey().getSeatNumber() + " is already booked");
                }
                oSeatMatrix.setBooked(true);
                totalPrice += oSeatMatrix.getPrice();
                this.matrixRepository.save(oSeatMatrix);
                sb.append(seat.getPrimaryKey().getSeatNumber() + " ,");
            }
            String seatNumbers = sb.toString();
            seatNumbers = (seatNumbers.endsWith(",")) ? seatNumbers.substring(0, seatNumbers.length() - 2)
                    : seatNumbers;
            this.LOGGER.info("bookSeats()  seats booked are  " + seatNumbers);
            this.LOGGER.info("Initiating the payment for this booking....This will be with dummy data.");
            /**
             * TODO : apply the offer codes if applicable TODO : update the booking table
             * for the user , this is for booking history
             */
            SeatMatrix seat = seatsToBook.get(0);
            Booking booking = new Booking();
            booking.setCreatedOn(LocalDateTime.now());//added toString method
            booking.setUserId(userName);
            booking.setMovieId(seat.getPrimaryKey().getMovieId());
            booking.setTheatreId(seat.getPrimaryKey().getTheaterId());
            booking.setSeatBooked(seatsToBook.size());//added String.valurOf() method to convert int to string.
            booking.setSeatNumbers(seatNumbers);
            booking.setTotalPrice(totalPrice);//added String.valurOf() method to convert int to string.
            this.bookingRepository.save(booking);
            this.LOGGER.info("bookSeats()  saved booking history.....");
            /**
             * payment
             */
            Payment dummyPayment = doPayment(booking);
            // update payment id in booking
            booking.setPaymentId(dummyPayment); // use toString method to convert localtime to string.
            booking.setUpdatedOn(LocalDateTime.now()); // use toString method to convert localtime to string.
            this.bookingRepository.save(booking);
            this.LOGGER.info("bookSeats() updated payment id in booking");

            // send the notification and update the notification id in booking table
            User user = this.userRepository.findUserByUserName(booking.getUserId());
            if (user == null)
                throw new CinemaGruvhException("There is no user exists with user name: " + booking.getUserId());

            Notification sentNotification = sendNotification(booking, user);
            booking.setNotificationId(sentNotification.getId()); //added toString()
            booking.setUpdatedOn(LocalDateTime.now()); // added toString()
            this.bookingRepository.save(booking);
            this.LOGGER.info("bookSeats() Notification sent , also updated the notification id to booking");

            return makeReturnedData(booking, user);

        }

    }

    private JSONObject makeReturnedData(Booking booking, User user) throws CinemaGruvhException {
        JSONObject result = new JSONObject();
        /**
         * user details:
         */
        JSONObject userDetails = new JSONObject();
        userDetails.put("bookedBy", user.getFirstName() + " " + user.getLastName());
        result.put("userDtails", userDetails);

        JSONObject movieDetails = new JSONObject();
        Optional<Movie> movie = this.movieRepo.findById(booking.getMovieId());
        Movie movieFromDb = movie.get();
        if (movieFromDb == null)
            throw new CinemaGruvhException("There is no movie found with id  " + booking.getMovieId());
        movieDetails.put("movieName", movieFromDb.getName());
        result.put("movieDetails", movieDetails);

        JSONObject theaterDetails = new JSONObject();
        Optional<Theatre> theater = this.theaterRepo.findById(booking.getTheatreId());
        Theatre theaterFromDb = theater.get();
        if (theaterFromDb == null)
            throw new CinemaGruvhException("There is no theater found with id  " + booking.getTheatreId());
        theaterDetails.put("theaterName", theaterFromDb.getName());
        theaterDetails.put("theaterAddress", theaterFromDb.getAddress());
        result.put("theaterDetails", theaterDetails);

        JSONObject ticketDetails = new JSONObject();
        ticketDetails.put("ticketNumbers", booking.getSeatNumbers());
        ticketDetails.put("totalPrice", booking.getTotalPrice());
        result.put("ticketDetails", ticketDetails);

        return result;

    }

    private Payment doPayment(Booking booking) {
        Payment dummyPayment = new Payment();
        dummyPayment.setBookingId(booking.getBookingId());
        dummyPayment.setAmount(booking.getTotalPrice());
        dummyPayment.setStatus(Status.SUCCESS);
        dummyPayment.setCreatedOn(LocalDateTime.now());
        dummyPayment.setMethod(PaymentMethod.CREDITCARD);
        dummyPayment.setSourceDetails("HDFC CC NO: 123456789123");
        /**
         * TODO : if payment is not success then un-book the seats
         */

        Payment paymentFromDb = this.paymentRepository.save(dummyPayment);
        this.LOGGER.info("bookSeats()  saved dummy payment ...");
        return paymentFromDb;
    }

    private Notification sendNotification(Booking booking, User user) {
        Notification tobeSendNotification = new Notification();
        tobeSendNotification.setBookingId(booking.getBookingId());
        tobeSendNotification.setStatus(Status.SUCCESS);
        tobeSendNotification.setReceiverEmail(user.getEmail());
        tobeSendNotification.setReceiverMobileNo(user.getMobileNumber());
        tobeSendNotification.setReceiverType(NotificationType.EMAIL);
        tobeSendNotification.setSenderEmail(DO_NOT_REPLY_DUMMY_BMS_COM);
        setTinyUrl(tobeSendNotification, booking, booking.getUserId());
        tobeSendNotification.setCreatedOn(LocalDateTime.now());
        tobeSendNotification.setSendTime(LocalDateTime.now());
        Notification sentNotification = this.notificationRepo.save(tobeSendNotification);
        return sentNotification;
    }

    private void setTinyUrl(Notification tobeSendNotification, Booking booking, String userName) {
        String bookingurl = "https://bms.com/?bookingid = 2&username =" + userName + "&totalAmount="
                + booking.getTotalPrice() + "&totalSeatBoooked=" + booking.getSeatBooked();
        Url url = new Url();
        url.setCreatedOn(LocalDateTime.now());
        url.setOriginalUrl(bookingurl);
        String shorturl = this.urlService.convertToShortUrl(url);
        tobeSendNotification.setTinyUrl(shorturl);
    }

}

