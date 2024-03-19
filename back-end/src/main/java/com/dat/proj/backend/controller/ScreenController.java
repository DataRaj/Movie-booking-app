package com.dat.proj.backend.controller;

import com.dat.proj.backend.entity.Movie;
import com.dat.proj.backend.entity.Screen;
import com.dat.proj.backend.entity.Theatre;
import com.dat.proj.backend.repository.MovieRepository;
import com.dat.proj.backend.repository.ScreenRepository;
import com.dat.proj.backend.repository.TheatreRepository;
import com.dat.proj.backend.util.ResponseParser;
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

import java.util.Optional;

@RestController
@RequestMapping(value = "/v1")
@Component
@Configuration
public class ScreenController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private ResponseParser responseParser;
    @Autowired
    private ScreenRepository screenRepo;
    @Autowired
    private TheatreRepository theaterRepo;

    @Autowired
    private MovieRepository movieRepo;

    /**
     * register a screen to theater
     *  TODO : Only ADMIN user is allowed to use this API
     *  TODO : validation on booking a screen on the overlapping time with other screen
     * @param screen
     * @return
     */
    @RequestMapping(value = "/registerScreen", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> registerScreen(@RequestBody Screen screen) {
        try {
            this.LOGGER.info("registerScreen() called with input: " + screen.toString());
            validateInput(screen);
            this.screenRepo.save(screen);
            this.LOGGER.info("registerScreen() successfully saved screen  ");
            return new ResponseEntity<>(this.responseParser.build(HttpStatus.CREATED.value(),
                    "Successfully registered new screen", "Successfully registered new movie"), HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            this.LOGGER.error("Error registering screen object " + e.getMessage());
            return new ResponseEntity<>(
                    this.responseParser.build(HttpStatus.BAD_REQUEST.value(), e.getMessage(), e.getMessage()),
                    HttpStatus.BAD_REQUEST);

        } catch (Exception ex) {
            this.LOGGER.error("Error registering screen object " + ex.getMessage());
            return new ResponseEntity<>(this.responseParser.build(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    ex.getMessage(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private void validateInput(Screen screen) {
        try {
            Assert.notNull(screen, "screen object must not be null");
            Assert.hasLength(screen.getEndsAt().toString(), "screen ends at  must not be null or empty");
            Assert.hasLength(screen.getScreenDetails().getMovieId().toString(), "screen must have a movie id");
            Assert.hasLength(screen.getScreenDetails().getTheaterId().toString(), "screen must have a theater id");
            Assert.hasLength(screen.getScreenDetails().getStartsAt().toString(), "screen must have a starts at time ");

            Optional<Theatre> existingTheater = this.theaterRepo.findById(screen.getScreenDetails().getTheaterId());

            Assert.isTrue(existingTheater.get() != null, "Invalid theater id passed, no theater found with this id ");

            Optional<Movie> existingMovie = this.movieRepo.findById(screen.getScreenDetails().getMovieId());

            Assert.isTrue(existingMovie.get() != null, "Invalid movie id passed, no movie found with this id ");

        } catch (IllegalArgumentException e) {
            this.LOGGER.error(e.getMessage());
            throw new IllegalArgumentException(e.getMessage());

        }

    }
}
