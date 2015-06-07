package com.privat.bki.desktopapp.utils;

import org.springframework.http.HttpStatus;

/**
 * Created by sting on 6/7/15.
 */
public class RestUtil {
    public static boolean isError(HttpStatus status) {
        HttpStatus.Series series = status.series();
        return (HttpStatus.Series.CLIENT_ERROR.equals(series)
                || HttpStatus.Series.SERVER_ERROR.equals(series));
    }
}
