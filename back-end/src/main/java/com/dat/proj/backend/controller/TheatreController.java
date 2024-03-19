package com.dat.proj.backend.controller;

import com.dat.proj.backend.entity.Theatre;
import com.dat.proj.backend.entity.User;
import com.dat.proj.backend.repository.TheatreRepository;
import com.dat.proj.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.dat.proj.backend.util.ResponseParser;

import java.time.LocalDateTime;
//import java.com.dat.proj.backend.util.logging.Logger;

@RestController
@RequestMapping(value = "/v1")
@Component
@Configuration
public class TheatreController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TheatreRepository TheatreRepo;
    @Autowired
    private ResponseParser responseParser;
    @Autowired
    private UserRepository userRepo;

    /**
     * register a Theatre
     * @param theatre
     * @return
     */
    @RequestMapping(value = "/registerTheatre", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> registerMovie(@RequestBody Theatre theatre) {
        try {
            this.LOGGER.info("registerTheatre() called with input: " + theatre.toString());
            validateInput(theatre);
            Theatre newTheatre = theatre;
            String TheatreId = newTheatre.getName().replaceAll("\\s", "");
            newTheatre.setTheatreId(TheatreId);
            newTheatre.setCreatedOn(LocalDateTime.now());
            this.LOGGER.info("registerTheatre() stting Theatre id as  " + TheatreId);
            this.TheatreRepo.save(newTheatre);
            this.LOGGER.info("registerTheatre() successfully saved Theatre with id:  " + TheatreId);
            return new ResponseEntity<>(this.responseParser.build(HttpStatus.CREATED.value(),
                    "Successfully registered new Theatre with thaterId: " + TheatreId,
                    "Successfully registered new Theatre with TheatreId: " + TheatreId), HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            this.LOGGER.error("Error registering Theatre object " + e.getMessage());
            return new ResponseEntity<>(
                    this.responseParser.build(HttpStatus.BAD_REQUEST.value(), e.getMessage(), e.getMessage()),
                    HttpStatus.BAD_REQUEST);

        } catch (Exception ex) {
            this.LOGGER.error("Error registering Theatre object " + ex.getMessage());
            return new ResponseEntity<>(this.responseParser.build(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    ex.getMessage(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    private void validateInput(Theatre Theatre) {
        try {
            Assert.notNull(Theatre, "User object must not be null");
            Assert.hasLength(Theatre.getName(), "Theatre name must not be null or empty");
            Assert.hasLength(Theatre.getAddress(), "Theatre address must not be null or empty");
            Assert.hasLength(Theatre.getCity(), "Theatre city must not be null or empty");
            Assert.hasLength(Theatre.getCountry(), "Theatre country must not be null or empty");
            Assert.hasLength(Theatre.getLanguages(), "Theatre language must not be null or empty");
            Assert.hasLength(Theatre.getUserName(), "Theatre user name must not be null or empty");
            User existingUser = this.userRepo.findUserByUserName(Theatre.getUserName());
            Assert.isTrue(existingUser != null,
                    "Invalid user name passed, no user found with id:  " + Theatre.getUserName());
            Assert.isTrue((existingUser.getUserType().toString().equals("ADMIN")) , "User is not allowed to add the Theatre, Only admin user can add a Theatre");

        } catch (IllegalArgumentException e) {
            this.LOGGER.error(e.getMessage());
            throw new IllegalArgumentException(e.getMessage());

        }
    }

}
