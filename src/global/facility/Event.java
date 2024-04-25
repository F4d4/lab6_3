package global.facility;

import global.tools.Validatable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;



import java.util.Objects;

/**
 * Класс События
 */

public class Event implements Validatable  , Serializable {
    private static final AtomicLong idGenerator = new AtomicLong(1);
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private long minAge; //Поле не может быть null
    private EventType eventType; //Поле не может быть null

    @JsonCreator
    public Event( @JsonProperty("name") String name ,@JsonProperty("minAge") long minAge , @JsonProperty("eventType") EventType eventType){
        this.id = idGenerator.getAndIncrement();
        this.name = name;
        this.minAge=minAge;
        this.eventType=eventType;
    }
    @Override
    public String toString(){
        String conclusion = "";
        conclusion+= "ID = " + id + " | ";
        conclusion+= "Название собития = " + name + " | ";
        conclusion+= "Минимальный восзраст = " + minAge + " | ";
        conclusion+= "Тип события = " + eventType + " | ";
        return conclusion;
    }

    /**
     * Проверяет валидность полей класса
     */
    @Override
    public boolean validate(){
        if (id <= 0) return false;
        if (name == null || name.isEmpty()) return false;
        if(eventType==null) return false;
        return true;
    }
    public long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public long getMinAge(){
        return minAge;
    }

    public EventType getEventType(){
        return eventType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) && Objects.equals(name, event.name)
                && Objects.equals(minAge, event.minAge) && Objects.equals(eventType, event.eventType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, minAge, eventType);
    }
}