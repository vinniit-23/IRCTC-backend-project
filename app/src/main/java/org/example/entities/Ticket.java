package org.example.entities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;


import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Ticket {
    private String ticketId;
    private String userId;
    private String source;
    private String destination;
    private Date dateOfTravel;
    private Train train;

    public String getTicketInfo(){
        return String.format("Ticket ID: %s belongs to User %s from %s to %s on %s", ticketId, userId, source, destination, dateOfTravel);
    }
    public Ticket(String ticketId, String userId, String source, String destination, Date dateOfTravel, Train train) {
        this.ticketId = ticketId;
        this.userId = userId;
        this.source = source;
        this.destination = destination;
        this.dateOfTravel = dateOfTravel;
        this.train = train;
    }

    public Ticket() {
    }

    public String getTicketId() {
        return ticketId;
    }
    public String getUserId() {
        return userId;
    }
    public String getSource() {
        return source;
    }
    public String getDestination() {
        return destination;
    }
    public Date getDateOfTravel() {
        return dateOfTravel;
    }
    public Train getTrain() {
        return train;
    }
    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }
    public void setDateOfTravel(Date dateOfTravel) {
        this.dateOfTravel = dateOfTravel;
    }
    public void setTrain(Train train) {
        this.train = train;
    }

}
