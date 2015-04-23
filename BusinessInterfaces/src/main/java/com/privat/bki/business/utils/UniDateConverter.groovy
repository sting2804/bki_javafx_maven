package com.privat.bki.business.utils


import java.sql.Date
import java.time.LocalDate

/**
 * Created by sting on 2/1/15.
 */

class UniDateConverter implements Converter<Date, LocalDate> {
    @Override
    LocalDate from(Date date) {
        return date == null ? null : LocalDate.ofEpochDay(date.getTime())
    }
    @Override
    Date to(LocalDate ld) {
        return ld == null ? null : new Date(ld.toEpochDay())
    }
    @Override
    Class<Date> fromType() {
        return Date.class
    }
    @Override
    Class<LocalDate> toType() {
        return LocalDate.class
    }
}
