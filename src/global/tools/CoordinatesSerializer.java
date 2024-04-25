package global.tools;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import global.facility.Coordinates;

import java.io.IOException;

public class CoordinatesSerializer extends JsonSerializer<Coordinates> {
    @Override
    public void serialize(Coordinates coordinates, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("x", coordinates.getX());
        jsonGenerator.writeNumberField("y", coordinates.getY());
        jsonGenerator.writeEndObject();
    }
}