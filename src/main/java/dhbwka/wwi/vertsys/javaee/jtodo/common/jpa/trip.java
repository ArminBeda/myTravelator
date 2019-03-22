/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.jtodo.common.jpa;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 *
 * @author jka
 */
@Entity
public class trip implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    @Column(name="Reiseziel" , length = 64)
    @NotNull(message = "Bitte tragen Sie das Reiseziel ein.")
    private String reiseziel;
    
    @Column(name="Ort", length = 64)
    @NotNull(message = "Bitte tragen Sie den Ort ein.")
    private String ort;
    
    @Column(name="Status")
    @NotNull(message = "Bitte tragen Sie den Status ein.")
    private String status;
    
    @NotNull(message = "Bitte tragen Sie das Startdatum ein.")
    private Date von_date;
    
    @NotNull(message = "Bitte tragen Sie das Enddatum ein")
    private Date bis_date;
    
    
    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">

    public trip(Long id, String reiseziel, String ort, String status, Date von_date, Date bis_date) {
        this.id = id;
        this.reiseziel = reiseziel;
        this.ort = ort;
        this.status = status;
        this.von_date = von_date;
        this.bis_date = bis_date;
    }
    
    //</editor-fold>
   
   //<editor-fold desc="Getter and Setter">

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReiseziel() {
        return reiseziel;
    }

    public void setReiseziel(String reiseziel) {
        this.reiseziel = reiseziel;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public Date getVon_date() {
        return von_date;
    }

    public void setVon_date(Date von_date) {
        this.von_date = von_date;
    }

    public Date getBis_date() {
        return bis_date;
    }

    public void setBis_date(Date bis_date) {
        this.bis_date = bis_date;
    }
  
    
    //</editor-fold>


  
}