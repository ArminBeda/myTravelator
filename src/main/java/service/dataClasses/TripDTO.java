/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package service.dataClasses;

import dhbwka.wwi.vertsys.javaee.myTravelator.trips.jpa.Trip;
import dhbwka.wwi.vertsys.javaee.myTravelator.trips.jpa.TripStatus;
import java.util.Date;

/**
 *
 * @author jka
 */
public class TripDTO {
    private long id;
    private UserDTO owner;
    private String destination;
    private String description;
    private String typeOfTravel;
    private StatusDTO status;
    private String vonDate;
    private String bisDate;

    public TripDTO() {
    }
   
    
    public TripDTO(Trip trip){
        this.id = trip.getId();
        this.owner = new UserDTO(trip.getOwner());
        this.destination = trip.getShortText();
        this.description = trip.getLongText();
        this.typeOfTravel = trip.getReiseart();
        this.vonDate = trip.getvonDate().toString();
        this.bisDate = trip.getbisDate().toString();
        this.status = new StatusDTO(trip.getStatus());
        
        
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }


    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
    

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypeOfTravel() {
        return typeOfTravel;
    }

    public void setTypeOfTravel(String typeOfTravel) {
        this.typeOfTravel = typeOfTravel;
    }

    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
    }

    public String getVonDate() {
        return vonDate;
    }

    public void setVonDate(String vonDate) {
        this.vonDate = vonDate;
    }

    public String getBisDate() {
        return bisDate;
    }

    public void setBisDate(String bisDate) {
        this.bisDate = bisDate;
    }



    
    
}
