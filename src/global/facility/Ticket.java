package global.facility;

import com.fasterxml.jackson.annotation.*;
import global.tools.CoordinatesSerializer;
import global.tools.Artifact;
import global.tools.EventSerializer;
import global.tools.Validatable;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


/**
 * Класс Билета
 */
public class Ticket extends Artifact implements Validatable , Serializable {
    private static final long serialVersionUID = 5760575944040770153L;
    private static final AtomicLong idGenerator = new AtomicLong(1); // генератор id, начинающийся с 1

    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой

    @JsonSerialize(using = CoordinatesSerializer.class)
    private Coordinates coordinates; //Поле не может быть null

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    private long price; //Значение поля должно быть больше 0
    private int discount; //Значение поля должно быть больше 0, Максимальное значение поля: 100
    @JsonProperty("ticketType")
    private TicketType type; //Поле может быть null

    @JsonSerialize(using = EventSerializer.class)
    private Event event; //Поле не может быть null

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("eventMinage")
    private long eventMinage;


    @JsonCreator
    public Ticket(@JsonProperty("name") String name , @JsonProperty("coordinates") Coordinates coordinates,@JsonProperty("price") long price,@JsonProperty("discount") int discount ,@JsonProperty("ticketType") TicketType type, @JsonProperty("event") Event event){
        this.id = idGenerator.getAndIncrement();
        this.name = name;
        this.coordinates = coordinates;
        this.price = price;
        this.discount = discount;
        this.type = type;
        this.event = event;
        this.creationDate = LocalDateTime.now();

        this.eventMinage = event.getMinAge();

    }

    @Override
    public long getId(){
        return id;
    }
    @Override
    public String toString(){
        String conclusion = "";
        conclusion+= "ID = " + id + " | ";
        conclusion+= "Имя = " + name + " | ";
        conclusion+= "Дата создания = " + creationDate.format(DateTimeFormatter.ISO_DATE_TIME) + " | ";
        conclusion+= "цена = " + price + " | ";
        conclusion+= "скидка = " + discount + " | ";
        conclusion+= "Тип билета = " + type + " | ";
        conclusion += "Коорднаты = " + coordinates + " | ";
        conclusion += event;
        return conclusion;

    }
    @Override
    public int compareTo(Artifact artifact) {
        return (int)(this.id - artifact.getId());
    }

    /**
     * Проверяет валидность полей билета
     */
    @Override
    public boolean validate(){
        if (id <= 0) return false;
        if (name == null || name.isEmpty()) return false;
        if (creationDate == null) return false;
        if (coordinates == null || !coordinates.validate()) return false;
        if(price<=0) return false;
        if(discount < 0 ||  discount>100) return false;
        if(event == null) return false;
        return true;
    }

    public long getEventMinage(){
        return event.getMinAge();
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }


    public long getPrice() {
        return price;
    }

    public int getDiscount() {
        return discount;
    }

    public TicketType getTicketType() {
        return type;
    }

    public Event getEvent() {
        return event;
    }

    // Сеттеры
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setPrice() {
        this.price = price;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void setTicketType(String ticketType) {
        this.type = type;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id)&&Objects.equals(name, ticket.name)&&Objects.equals(coordinates, ticket.coordinates)&&Objects.equals(price, ticket.price)&&Objects.equals(discount, ticket.discount)&&Objects.equals(type, ticket.type)&&Objects.equals(event, ticket.event)&&Objects.equals(creationDate, ticket.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, price,discount,type,event);
    }

    public void printFields() {
        System.out.println("Ticket ID: " + id);
        System.out.println("Ticket Name: " + name);
        System.out.println("coordinates: " + coordinates);
        System.out.println("creationDate: " + creationDate);
        System.out.println("price: " + price);
        System.out.println("discount: " + discount);
        System.out.println("type: " + type);
        System.out.println("event: " + event);

    }

}