/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package service;

import dhbwka.wwi.vertsys.javaee.myTravelator.trips.jpa.Trip;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import service.dataClasses.TripDTO;
import service.dataClasses.TripFacade;

/**
 *
 * @author yusefoenkol
 */
@Stateless
@Path("api/trip")
public class TripFacadeREST extends AbstractFacade<Trip> {

    @PersistenceContext(unitName = "default")
    private EntityManager em;
    
    @EJB
    TripFacade tripFacade;

    public TripFacadeREST() {
        super(Trip.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Trip entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Trip entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    /*@GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Task find(@PathParam("id") Long id) {
        return super.find(id);
    }*/

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Trip find(@PathParam("id") Long id) {
        return super.find(id);
    }
    
   /* @GET
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public List<Trip> findAll() {
        return super.findAll();
    }*/
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TripDTO> findAllDTO() {
        return tripFacade.findAllTrip();
    }

    @GET
    @Path("{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Trip> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
