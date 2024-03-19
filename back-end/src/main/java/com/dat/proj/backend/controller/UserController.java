package com.dat.proj.backend.controller;

import com.dat.proj.backend.entity.User;
import com.dat.proj.backend.enums.UserType;
import com.dat.proj.backend.repository.UserRepository;
import com.dat.proj.backend.security.AuthRequest;
import com.dat.proj.backend.security.JWTUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.lang.Assert;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import util.ResponseParser;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RestController
    @RequestMapping(value = "/v1")
    @Component
    @Configuration
    public class UserController{
//        logging function for /v1 route and regarding data tranfer
        private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
        private static final Pattern VALIDATION_OF_EMAIL_ADDRESS_REG =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$",
                        Pattern.CASE_INSENSITIVE);
        @Autowired
        private ResponseParser responseParser;

        @Autowired
        private JWTUtils jwtUtils;

        @Autowired
        private UserRepository userReposiitory;

        @Autowired
        private AuthenticationManager authenticationManager;


    /**
     * get the user details
     * @param userName
     * @return
     */
    @RequestMapping(value = "/getUserDetails", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getUserDetails(@RequestParam("userName") String userName) {
        this.LOGGER.info(" getUser () with input params:  " + userName);
        try {
            User user = this.userReposiitory.findUserByUserName(userName);
            this.LOGGER.info(" getUser () found the user with input username " + user.toString());

            ObjectMapper mapper = new ObjectMapper();
            String jsonUser = mapper.writeValueAsString(user);
            this.LOGGER.info(" getUser () status success! ");
            return new ResponseEntity<>(jsonUser, HttpStatus.OK);

        } catch (Exception ex) {
            this.LOGGER.info(" getUser () Error occured:  " + ex.getMessage());
            return new ResponseEntity<>(this.responseParser.build(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    ex.getMessage(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

