package com.privat.bki.business.utils;


import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by sting on 2/1/15.
 */

public class UniDateConverter implements Converter<Date, LocalDate> {
    @Override
    public LocalDate from(Date date) {
        return date == null ? null : LocalDate.ofEpochDay(date.getTime());
    }
    @Override
    public Date to(LocalDate ld) {
        return ld == null ? null : new Date(ld.toEpochDay());
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
