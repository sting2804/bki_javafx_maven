package com.privat.bki.utils


import java.sql.Date
import java.time.LocalDate


/**
 * Created by sting on 2/1/15.
 */

class SqlDateConverter implements Converter<Date, LocalDate> {
    @Override
    LocalDate from(java.sql.Date date) {
        return date == null ? null : date.toLocalDate()
    }
    @Override
    Date to(LocalDate ld) {
        return ld == null ? null : Date.valueOf(ld)
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
