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

import dhbwka.wwi.vertsys.javaee.myTravelator.trips.ejb.CountryBean;
import dhbwka.wwi.vertsys.javaee.myTravelator.trips.jpa.Country;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author jka
 */
@Stateless
public class CountryFacade {
    
    @EJB
    CountryBean countryBean;
    
    
    public List<CountryDTO> findCountry(String name) {
        List<Country> country_results = countryBean.findCountry(name);
    return country_results.stream().map((trip)->{
        CountryDTO countryDTO = new CountryDTO(trip);
        return countryDTO;
    }).collect(Collectors.toList());
    }
  
    
}
