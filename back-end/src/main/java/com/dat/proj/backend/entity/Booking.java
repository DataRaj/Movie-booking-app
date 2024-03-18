package com.dat.proj.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

public class Booking implements Serializable {
    @Id
    @Column(name = "booking_id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String bookingId;

    @Column(name = "user_name")
    private String userId;

    @Column(name = "theatre_id")
    private String theatreId;

    @Column(name = "payment_id")
    private String paymentId;

    @Column(name = "movie_id")
    private String movieId;

    @Column(name = "notification_id")
    private String notificationId;

    @Column(name = "total_price")
    private String totalPrice;
}
