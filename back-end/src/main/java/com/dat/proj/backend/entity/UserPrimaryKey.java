package com.dat.proj.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class UserPrimaryKey implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;

    public UserPrimaryKey(String name, String mobileNumber) {
        this.name = name;
        this.mobileNumber = mobileNumber;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Override
    public String toString() {
        return "UserPrimaryKey{" +
                "name='" + name + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                '}';
    }
}
