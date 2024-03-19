package com.dat.proj.backend.entity;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cast")
public class Cast implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", updatable = false,nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "cast_id")
    private String movieId;

    @Column(name = "character_name")
    private String characterName;

    @Column(name = "character_occupation")
    private String characterOccupation;

    @Column(name = "description")
    private String castDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getCharacterOccupation() {
        return characterOccupation;
    }

    public void setCharacterOccupation(String characterOccupation) {
        this.characterOccupation = characterOccupation;
    }

    public String getCastDetails() {
        return castDetails;
    }

    public void setCastDetails(String castDetails) {
        this.castDetails = castDetails;
    }

    @Override
    public String toString() {
        return "Cast [id=" + id + ", movieId=" + movieId + ", characterName=" + characterName + ", characterOccupation="
                + characterOccupation + ", castDetails=" + castDetails + "]";
    }



}

