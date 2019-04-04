/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.myTravelator.trips.web;

import dhbwka.wwi.vertsys.javaee.myTravelator.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.myTravelator.trips.ejb.CountryBean;
import dhbwka.wwi.vertsys.javaee.myTravelator.trips.ejb.TripBean;
import dhbwka.wwi.vertsys.javaee.myTravelator.trips.jpa.Country;
import dhbwka.wwi.vertsys.javaee.myTravelator.trips.jpa.Trip;
import dhbwka.wwi.vertsys.javaee.myTravelator.trips.jpa.TripStatus;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet für die tabellarische Auflisten der Aufgaben.
 */
@WebServlet(urlPatterns = {"/app/trips/list/"})
public class TripListServlet extends HttpServlet {

    @EJB
    private CountryBean countryBean;
    
    @EJB
    private TripBean tripBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Kategorien und Stati für die Suchfelder ermitteln
        request.setAttribute("categories", this.countryBean.findAllSorted());
        request.setAttribute("statuses", TripStatus.values());

        // Suchparameter aus der URL auslesen
        String searchText = request.getParameter("search_text");
        String searchCountry = request.getParameter("search_country");
        String searchStatus = request.getParameter("search_status");

        // Anzuzeigende Aufgaben suchen
        Country country = null;
        TripStatus status = null;

        if (searchCountry != null) {
            try {
                country = this.countryBean.findById(Long.parseLong(searchCountry));
            } catch (NumberFormatException ex) {
                country = null;
            }
        }

        if (searchStatus != null) {
            try {
                status = TripStatus.valueOf(searchStatus);
            } catch (IllegalArgumentException ex) {
                status = null;
            }

        }

        List<Trip> trips = this.tripBean.search(searchText, country, status);
        request.setAttribute("trips", trips);
        

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/trips/trip_list.jsp").forward(request, response);
    }
}
