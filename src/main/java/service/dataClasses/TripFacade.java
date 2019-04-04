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

import dhbwka.wwi.vertsys.javaee.myTravelator.trips.ejb.TripBean;
import dhbwka.wwi.vertsys.javaee.myTravelator.trips.jpa.Trip;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author jka
 */
@Stateless
public class TripFacade {
    
    @EJB
    TripBean tripBean;
    
    public List<TripDTO> findAllTrip(){
        List<Trip> trips= tripBean.findAll();
        return trips.stream().map((trip) -> {
            TripDTO tripDTO = new TripDTO(trip);
            return tripDTO;
        }).collect(Collectors.toList());
    } 
    
    public List<TripDTO> findAllDestination(String ort){
        List<Trip> result_trips = tripBean.findTripsDestination(ort);
        return result_trips.stream().map((trip)->
        {
            TripDTO tripDTO = new TripDTO(trip);
            return tripDTO;
        }).collect(Collectors.toList());
    }
}
