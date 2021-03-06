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
import dhbwka.wwi.vertsys.javaee.myTravelator.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.myTravelator.trips.jpa.Country;
import dhbwka.wwi.vertsys.javaee.myTravelator.trips.jpa.Trip;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Seite zum Anzeigen und Bearbeiten der Kategorien. Die Seite besitzt ein
 * Formular, mit dem ein neue Kategorie angelegt werden kann, sowie eine Liste,
 * die zum Löschen der Kategorien verwendet werden kann.
 */
@WebServlet(urlPatterns = {"/app/trips/categories/"})
public class CountryListServlet extends HttpServlet {

    @EJB
    CountryBean countryBean;

    @EJB
    TripBean tripBean;

    @EJB
    ValidationBean validationBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Alle vorhandenen Kategorien ermitteln
        request.setAttribute("categories", this.countryBean.findAllSorted());

        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/trips/country_list.jsp");
        dispatcher.forward(request, response);

        // Alte Formulardaten aus der Session entfernen
        HttpSession session = request.getSession();
        session.removeAttribute("categories_form");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Angeforderte Aktion ausführen        
        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "create":
                this.createCountry(request, response);
                break;
            case "delete":
                this.deleteCategories(request, response);
                break;
        }
    }

    /**
     * Aufgerufen in doPost(): Neue Kategorie anlegen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void createCountry(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben prüfen
        String name = request.getParameter("name");

        Country country = new Country(name);
        List<String> errors = this.validationBean.validate(country);

        // Neue Kategorie anlegen
        if (errors.isEmpty()) {
            this.countryBean.saveNew(country);
        }

        // Browser auffordern, die Seite neuzuladen
        if (!errors.isEmpty()) {
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("categories_form", formValues);
        }

        response.sendRedirect(request.getRequestURI());
    }

    /**
     * Aufgerufen in doPost(): Markierte Kategorien löschen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void deleteCategories(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Markierte Kategorie IDs auslesen
        String[] countryIds = request.getParameterValues("country");

        if (countryIds == null) {
            countryIds = new String[0];
        }

        // Kategorien löschen
        for (String countryId : countryIds) {
            // Zu löschende Kategorie ermitteln
            Country country;

            try {
                country = this.countryBean.findById(Long.parseLong(countryId));
            } catch (NumberFormatException ex) {
                continue;
            }

            if (country == null) {
                continue;
            }

            // Bei allen betroffenen Aufgaben, den Bezug zur Kategorie aufheben
            List<Trip> trips = country.getTrips();

            if (trips != null) {
                trips.forEach((Trip trip) -> {
                    trip.setCountry(null);
                    this.tripBean.update(trip);
                });
            }

            // Und weg damit
            this.countryBean.delete(country);
        }

        // Browser auffordern, die Seite neuzuladen
        response.sendRedirect(request.getRequestURI());
    }

}
