package com.privat.bki.business.utils

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider

import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Created by sting on 4/21/15.
 */
class JsonDateSerializer extends JsonSerializer<LocalDate> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void serialize(LocalDate date, JsonGenerator generator,
                          SerializerProvider provider) throws IOException,
            JsonProcessingException {

        String dateString = date.format(formatter);
        generator.writeString(dateString);
    }
}
