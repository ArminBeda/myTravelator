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

import dhbwka.wwi.vertsys.javaee.myTravelator.common.web.WebUtils;
import dhbwka.wwi.vertsys.javaee.myTravelator.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.myTravelator.trips.ejb.CountryBean;
import dhbwka.wwi.vertsys.javaee.myTravelator.trips.ejb.TripBean;
import dhbwka.wwi.vertsys.javaee.myTravelator.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.myTravelator.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.myTravelator.trips.jpa.Trip;
import dhbwka.wwi.vertsys.javaee.myTravelator.trips.jpa.TripStatus;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Seite zum Anlegen oder Bearbeiten einer Aufgabe.
 */
@WebServlet(urlPatterns = "/app/trips/trip/*")
public class TripEditServlet extends HttpServlet {

    @EJB
    TripBean tripBean;

    @EJB
    CountryBean countryBean;

    @EJB
    UserBean userBean;

    @EJB
    ValidationBean validationBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Kategorien und Stati für die Suchfelder ermitteln
        request.setAttribute("categories", this.countryBean.findAllSorted());
        request.setAttribute("statuses", TripStatus.values());

        // Zu bearbeitende Aufgabe einlesen
        HttpSession session = request.getSession();

        Trip trip = this.getRequestedTrip(request);
        request.setAttribute("edit", trip.getId() != 0);
                                
        if (session.getAttribute("trip_form") == null) {
            // Keine Formulardaten mit fehlerhaften Daten in der Session,
            // daher Formulardaten aus dem Datenbankobjekt übernehmen
            request.setAttribute("trip_form", this.createTripForm(trip));
        }
        
        request.setAttribute("reiseart", trip.getReiseart());
        

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/trips/trip_edit.jsp").forward(request, response);
        
        session.removeAttribute("trip_form");
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
            case "save":
                this.saveTrip(request, response);
                break;
            case "delete":
                this.deleteTrip(request, response);
                break;
        }
    }

    /**
     * Aufgerufen in doPost(): Neue oder vorhandene Aufgabe speichern
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void saveTrip(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben prüfen
        List<String> errors = new ArrayList<>();

        String tripCountry = request.getParameter("trip_country");
        String tripvonDate = request.getParameter("trip_von_date");
        String tripbisDate = request.getParameter("trip_bis_date");
        String tripStatus = request.getParameter("trip_status");
        String trip_reiseart = request.getParameter("trip_reiseart");
        String tripShortText = request.getParameter("trip_short_text");
        String tripLongText = request.getParameter("trip_long_text");

        Trip trip = this.getRequestedTrip(request);

        if (tripCountry != null && !tripCountry.trim().isEmpty()) {
            try {
                trip.setCountry(this.countryBean.findById(Long.parseLong(tripCountry)));
            } catch (NumberFormatException ex) {
                // Ungültige oder keine ID mitgegeben
            }
        }

        Date vonDate = WebUtils.parseDate(tripvonDate);
        Date bisDate = WebUtils.parseDate(tripbisDate);

        if (vonDate != null) {
            trip.setvonDate(vonDate);
        } else {
            errors.add("Das Datum muss dem Format dd.mm.yyyy entsprechen.");
        }

        if (bisDate != null) {
            trip.setbisDate(bisDate);
        } else {
            errors.add("Das Datum muss dem Format dd.mm.yyyy entsprechen.");
        }

        if (vonDate.compareTo(bisDate)>0){
            errors.add("Von Datum muss vor Bis Datum sein.");
        }

        try {
            trip.setStatus(TripStatus.valueOf(tripStatus));
        } catch (IllegalArgumentException ex) {
            errors.add("Der ausgewählte Status ist nicht vorhanden.");
        }

        trip.setShortText(tripShortText);
        trip.setLongText(tripLongText);
        
        trip.setReiseart(trip_reiseart);

        this.validationBean.validate(trip, errors);

        // Datensatz speichern
        if (errors.isEmpty()) {
            this.tripBean.update(trip);
        }

        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            response.sendRedirect(WebUtils.appUrl(request, "/app/trips/list/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("trip_form", formValues);

            response.sendRedirect(request.getRequestURI());
        }
    }

    /**
     * Aufgerufen in doPost: Vorhandene Aufgabe löschen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void deleteTrip(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Datensatz löschen
        Trip trip = this.getRequestedTrip(request);
        this.tripBean.delete(trip);

        // Zurück zur Übersicht
        response.sendRedirect(WebUtils.appUrl(request, "/app/trips/list/"));
    }

    /**
     * Zu bearbeitende Aufgabe aus der URL ermitteln und zurückgeben. Gibt
     * entweder einen vorhandenen Datensatz oder ein neues, leeres Objekt
     * zurück.
     *
     * @param request HTTP-Anfrage
     * @return Zu bearbeitende Aufgabe
     */
    private Trip getRequestedTrip(HttpServletRequest request) {
        // Zunächst davon ausgehen, dass ein neuer Satz angelegt werden soll
        Trip trip = new Trip();
        trip.setOwner(this.userBean.getCurrentUser());
        trip.setvonDate(new Date(System.currentTimeMillis()));
        trip.setbisDate(new Date(System.currentTimeMillis()));

        // ID aus der URL herausschneiden
        String tripId = request.getPathInfo();

        if (tripId == null) {
            tripId = "";
        }

        tripId = tripId.substring(1);

        if (tripId.endsWith("/")) {
            tripId = tripId.substring(0, tripId.length() - 1);
        }

        // Versuchen, den Datensatz mit der übergebenen ID zu finden
        try {
            trip = this.tripBean.findById(Long.parseLong(tripId));
        } catch (NumberFormatException ex) {
            // Ungültige oder keine ID in der URL enthalten
        }

        return trip;
    }

    /**
     * Neues FormValues-Objekt erzeugen und mit den Daten eines aus der
     * Datenbank eingelesenen Datensatzes füllen. Dadurch müssen in der JSP
     * keine hässlichen Fallunterscheidungen gemacht werden, ob die Werte im
     * Formular aus der Entity oder aus einer vorherigen Formulareingabe
     * stammen.
     *
     * @param trip Die zu bearbeitende Aufgabe
     * @return Neues, gefülltes FormValues-Objekt
     */
    private FormValues createTripForm(Trip trip) {
        Map<String, String[]> values = new HashMap<>();

        values.put("trip_owner", new String[]{
            trip.getOwner().getUsername()
        });

        if (trip.getCountry() != null) {
            values.put("trip_country", new String[]{
                "" + trip.getCountry().getId()
            });
        }

        values.put("trip_von_date", new String[]{
            WebUtils.formatDate(trip.getvonDate())
        });

        values.put("trip_bis_date", new String[]{
            WebUtils.formatDate(trip.getbisDate())
        });

        values.put("trip_status", new String[]{
            trip.getStatus().toString()
        });

        values.put("trip_short_text", new String[]{
            trip.getShortText()
        });

        values.put("trip_long_text", new String[]{
            trip.getLongText()
        });

        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }

}
