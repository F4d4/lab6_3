package global.tools;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import global.facility.Event;

import java.io.IOException;

public class EventSerializer extends JsonSerializer<Event> {
    @Override
    public void serialize(Event event, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", event.getId());
        jsonGenerator.writeStringField("name", event.getName());
        jsonGenerator.writeNumberField("minAge", event.getMinAge());
        jsonGenerator.writeStringField("eventType", event.getEventType().toString());
        // Другие поля Event, которые вы хотите включить в XML
        jsonGenerator.writeEndObject();
    }
}