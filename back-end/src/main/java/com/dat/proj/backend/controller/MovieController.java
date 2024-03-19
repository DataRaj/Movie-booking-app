package com.dat.proj.backend.controller;

import com.dat.proj.backend.entity.Movie;
import com.dat.proj.backend.entity.Theatre;
import com.dat.proj.backend.repository.MovieRepository;
import com.dat.proj.backend.repository.TheatreRepository;
import com.dat.proj.backend.util.ResponseParser;
import io.jsonwebtoken.lang.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping(value = "/v1")
@Component
@Configuration
public class MovieController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ResponseParser responseParser;

    @Autowired
    private MovieRepository movieRepo;

    @Autowired
    private TheatreRepository TheatreRepo;

    /**
     * Register a movie
     * TODO : Only ADMIN user is allowed to use this API
     * @param movie
     * @return
     */
    @RequestMapping(value = "/registerMovie", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> registerMovie(@RequestBody Movie movie) {
        try {
            this.LOGGER.info("registerMovie() called with input: " + movie.toString());
            validateInput(movie);
            Movie newMovie = movie;
            String movieId = newMovie.getName().replaceAll("\\s", "");
            newMovie.setMovieId(movieId);
            newMovie.setCreatedOn(LocalDateTime.now());
            this.LOGGER.info("registerMovie() setting movie id as  " + movieId);
            this.movieRepo.save(newMovie);
            this.LOGGER.info("registerMovie() saved movie with id   " + movieId + " in DB");
            return new ResponseEntity<>(this.responseParser.build(HttpStatus.CREATED.value(),
                    "Successfully registered new movie with id: "+movieId, "Successfully registered new movie with id: "+movieId), HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            this.LOGGER.error("Error registering movie  " + e.getMessage());
            return new ResponseEntity<>(
                    this.responseParser.build(HttpStatus.BAD_REQUEST.value(), e.getMessage(), e.getMessage()),
                    HttpStatus.BAD_REQUEST);

        } catch (Exception ex) {
            this.LOGGER.error("Error registering movie object " + ex.getMessage());
            return new ResponseEntity<>(this.responseParser.build(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    ex.getMessage(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private void validateInput(Movie movie) {
        try {
            Assert.notNull(movie, "User object must not be null");
            Assert.hasLength(movie.getName(), "movie name must not be null or empty");
            Assert.hasLength(movie.getPosterUrl(), "Poster url must not be null or empty");
            Assert.hasLength(movie.getDuration(), "Movie duration must not be null or empty");
            Assert.hasLength(movie.getReleaseYear(), "Movie duration must not be null or empty");
            Assert.hasLength(movie.getTrailerUrl(), "Movie trailer url must not be null or empty");
            Assert.hasLength(movie.getActiveDateEnd().toString(), "Movie activate date end  must not be null or empty");
            Assert.hasLength(movie.getActiveDateStart().toString(),
                    "Movie active date start must not be null or empty");
            Assert.hasLength(movie.getLanguage().toString(), "Movie language must not be null or empty");
            Assert.hasLength(movie.getType().toString(), "Movie type must not be null or empty");
            Assert.notNull(movie.getTheaterId(), "Theatre id must not be null");
            Optional<Theatre> existingTheatre = this.TheatreRepo.findById(movie.getTheaterId());
            Assert.isTrue(existingTheatre.get() != null, "Invalid Theatre id passed, no Theatre found with this id ");
        } catch (IllegalArgumentException e) {
            this.LOGGER.error(e.getMessage());
            throw new IllegalArgumentException(e.getMessage());

        }

    }

}
