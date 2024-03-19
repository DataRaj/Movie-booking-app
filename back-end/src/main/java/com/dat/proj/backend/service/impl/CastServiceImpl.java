package com.dat.proj.backend.service.impl;


import java.util.List;

import com.dat.proj.backend.entity.Cast;
import com.dat.proj.backend.repository.CastRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CastServiceImpl {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CastRepository castRepo;

    /**
     * The service method will return the casts for a movie, it will query the DB for movieId
     * @param movieId
     * @return
     * @throws JsonProcessingException
     */
    public JSONArray findByMovieId(String movieId){
        this.LOGGER.info("findByMovieId() cast service called with input " + movieId);
        List<Cast> casts = this.castRepo.getCastByMovieId(movieId);
        this.LOGGER.info("findByMovieId() Found  " + casts.size() + " no of casts for movie " + movieId);
        JSONArray result = new JSONArray();
        for (Cast cast : casts) {
            ObjectMapper mapper = new ObjectMapper();
            String castString = null;
            try {
                castString = mapper.writeValueAsString(cast);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            JSONObject object = null;
            try {
                object = new JSONObject(castString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            result.put(object);
        }
        this.LOGGER.info("findByMovieId() Successfully returned the casts for the movie " + movieId);
        return result;
    }

}

