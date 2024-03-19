package com.dat.proj.backend.util;

import java.io.Serializable;

public class CinemaGruvhException extends Throwable implements Serializable {
    private static final long serialVersionUID = 1L;

    public CinemaGruvhException(String errorMsg) {
        super(errorMsg);
    }
}
