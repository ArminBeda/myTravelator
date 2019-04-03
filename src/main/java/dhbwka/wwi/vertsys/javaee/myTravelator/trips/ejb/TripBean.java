/*Tip
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.myTravelator.trips.ejb;

import dhbwka.wwi.vertsys.javaee.myTravelator.common.ejb.EntityBean;
import dhbwka.wwi.vertsys.javaee.myTravelator.trips.jpa.Country;
import dhbwka.wwi.vertsys.javaee.myTravelator.trips.jpa.Trip;
import dhbwka.wwi.vertsys.javaee.myTravelator.trips.jpa.TripStatus;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Einfache EJB mit den üblichen CRUD-Methoden für Aufgaben
 */
@Stateless
@RolesAllowed("app-user")
public class TripBean extends EntityBean<Trip, Long> { 
   
    public TripBean() {
        super(Trip.class);
    }
    
    /**
     * Alle Aufgaben eines Benutzers, nach Fälligkeit sortiert zurückliefern.
     * @param username Benutzername
     * @return Alle Aufgaben des Benutzers
     */
    public List<Trip> findByUsername(String username) {
        return em.createQuery("SELECT t FROM Trip t WHERE t.owner.username = :username ORDER BY t.vonDate, t.bisDate")
                 .setParameter("username", username)
                 .getResultList();
    }
    
    /**
     * Suche nach Aufgaben anhand ihrer Bezeichnung, Kategorie und Status.
     * 
     * Anders als in der Vorlesung behandelt, wird die SELECT-Anfrage hier
     * mit der CriteriaBuilder-API vollkommen dynamisch erzeugt.
     * 
     * @param search In der Kurzbeschreibung enthaltener Text (optional)
     * @param country Kategorie (optional)
     * @param status Status (optional)
     * @return Liste mit den gefundenen Aufgaben
     */
    public List<Trip> search(String search, Country country, TripStatus status) {
        // Hilfsobjekt zum Bauen des Query
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        
        // SELECT t FROM Trip t
        CriteriaQuery<Trip> query = cb.createQuery(Trip.class);
        Root<Trip> from = query.from(Trip.class);
        query.select(from);

        // ORDER BY vonDate, bisDate
        query.orderBy(cb.asc(from.get("vonDate")), cb.asc(from.get("bisDate")));
        
        // WHERE t.shortText LIKE :search
        Predicate p = cb.conjunction();
        
        if (search != null && !search.trim().isEmpty()) {
            p = cb.and(p, cb.like(from.get("shortText"), "%" + search + "%"));
            query.where(p);
        }
        
        // WHERE t.country = :country
        if (country != null) {
            p = cb.and(p, cb.equal(from.get("country"), country));
            query.where(p);
        }
        
        // WHERE t.status = :status
        if (status != null) {
            p = cb.and(p, cb.equal(from.get("status"), status));
            query.where(p);
        }
        
        return em.createQuery(query).getResultList();
    }
}
