package com.dat.proj.backend.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "screen")
public class Screen implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private ScreenPk screenDetails;

    @Column(name = "ends_at")
    private String endsAt;

    public ScreenPk getScreenDetails() {
        return screenDetails;
    }

    public void setScreenDetails(ScreenPk screenDetails) {
        this.screenDetails = screenDetails;
    }

    public String getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(String endsAt) {
        this.endsAt = endsAt;
    }

    @Override
    public String toString() {
        return "Screen [screenDetails=" + screenDetails + ", endsAt=" + endsAt + "]";
    }
}

