package com.thord.atb.data.json.deserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
	private final DateTimeFormatter FOMATTER2 = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
	private final DateTimeFormatter FOMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	@Override
	public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
		try {
			return LocalDateTime.parse(jp.getValueAsString(), FOMATTER);
		} catch (DateTimeParseException ex) {
			return LocalDateTime.parse(jp.getValueAsString(), FOMATTER2);
		}
	}
}
