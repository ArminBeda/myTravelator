/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.myTravelator.trips.jpa;

import dhbwka.wwi.vertsys.javaee.myTravelator.common.jpa.User;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Eine zu erledigende Aufgabe.
 */
@Entity
@XmlRootElement
public class Trip implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "trip_ids")
    @TableGenerator(name = "trip_ids", initialValue = 0, allocationSize = 50)
    private long id;

    @ManyToOne
    @NotNull(message = "Der Trip muss einem Benutzer geordnet werden.")
    private User owner;

    @ManyToOne
    private Country country;

    @Column(length = 50)
    @NotNull(message = "Der Ort darf nicht leer sein.")
    @Size(min = 1, max = 50, message = "Der Ort muss zwischen ein und 50 Zeichen lang sein.")
    private String shortText;

    @Lob
    @NotNull
    private String longText;

    @NotNull(message = "Das Datum darf nicht leer sein.")
    private Date vonDate;

    @NotNull(message = "Das Datum darf nicht leer sein.")
    private Date bisDate;

    @NotNull(message = "Bitte tragen Sie die Reiseart ein.")
    private String reiseart;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TripStatus status = TripStatus.OPEN;

    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Trip() {
    }

    public Trip(User owner, Country country, String shortText, String longText, Date vonDate, Date bisDate, String reiseart) {
        this.owner = owner;
        this.country = country;
        this.shortText = shortText;
        this.longText = longText;
        this.vonDate = vonDate;
        this.bisDate = bisDate;
        this.reiseart= reiseart;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter und Getter">
    
    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public String getLongText() {
        return longText;
    }

    public void setLongText(String longText) {
        this.longText = longText;
    }

    public Date getvonDate() {
        return vonDate;
    }

    public void setvonDate(Date vonDate) {
        this.vonDate = vonDate;
    }

    public Date getbisDate() {
        return bisDate;
    }

    public void setbisDate(Date bisDate) {
        this.bisDate = bisDate;
    }

    public TripStatus getStatus() {
        return status;
    }

    public void setStatus(TripStatus status) {
        this.status = status;
    }

    public String getReiseart() {
        return reiseart;
    }

    public void setReiseart(String reiseart) {
        this.reiseart = reiseart;
    }

}
//</editor-fold>

