/*
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
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

/**
 * Einfache EJB mit den üblichen CRUD-Methoden für Kategorien.
 */
@Stateless
@RolesAllowed("app-user")
public class CountryBean extends EntityBean<Country, Long> {

    public CountryBean() {
        super(Country.class);
    }
    
    /**
     * Liefert Country zurück, welches Namen entspricht
     * @param name
     */
    
    public List<Country> findCountry(String name){
        return em.createQuery("Select c from Country c where c.name = :name")
                .setParameter("name", name)
                .getResultList();
    }
   

    /**
     * Auslesen aller Kategorien, alphabetisch sortiert.
     *
     * @return Liste mit allen Kategorien
     */
    public List<Country> findAllSorted() {
        return this.em.createQuery("SELECT c FROM Country c ORDER BY c.name").getResultList();
    }
}
