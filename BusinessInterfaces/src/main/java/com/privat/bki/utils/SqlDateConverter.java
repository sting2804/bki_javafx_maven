package com.privat.bki.utils;


import java.sql.Date;
import java.time.LocalDate;


/**
 * Created by sting on 2/1/15.
 */

public class SqlDateConverter implements Converter<Date, LocalDate> {
    @Override
    public LocalDate from(java.sql.Date date) {
        return date == null ? null : date.toLocalDate();
    }
    @Override
    public Date to(LocalDate ld) {
        return ld == null ? null : Date.valueOf(ld);
    }
    @Override
    public Class<Date> fromType() {
        return Date.class;
    }
    @Override
    public Class<LocalDate> toType() {
        return LocalDate.class;
    }
}
