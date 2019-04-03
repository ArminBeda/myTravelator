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
    private String city;
    private String description;
    private String typeOfTravel;
    private TripStatus status;
    private Date vonDate;
    private Date bisDate;

    public TripDTO() {
    }
   
    
    public TripDTO(Trip trip){
        this.id = trip.getId();
        this.owner = new UserDTO(trip.getOwner());
        this.city = trip.getShortText();
        this.description = trip.getLongText();
        this.typeOfTravel = trip.getLongText();
        this.vonDate = trip.getvonDate();
        this.bisDate = trip.getbisDate();
        this.status = trip.getStatus();
        
        
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public TripStatus getStatus() {
        return status;
    }

    public void setStatus(TripStatus status) {
        this.status = status;
    }

    public Date getVonDate() {
        return vonDate;
    }

    public void setVonDate(Date vonDate) {
        this.vonDate = vonDate;
    }

    public Date getBisDate() {
        return bisDate;
    }

    public void setBisDate(Date bisDate) {
        this.bisDate = bisDate;
    }
    

    
}
