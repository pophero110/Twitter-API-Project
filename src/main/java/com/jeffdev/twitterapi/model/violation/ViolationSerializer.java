package com.jeffdev.twitterapi.model.violation;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * A custom serializer for converting a Violation object to JSON format.
 */
public class ViolationSerializer extends JsonSerializer<Violation> {

    @Override
    public void serialize(Violation violation, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("fieldName", violation.getFieldName());
        gen.writeStringField("message", violation.getMessage());
        gen.writeEndObject();
    }
}