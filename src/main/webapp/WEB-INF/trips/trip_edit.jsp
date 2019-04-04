<%-- 
    Copyright © 2018 Dennis Schulmeister-Zimolong

    E-Mail: dhbw@windows3.de
    Webseite: https://www.wpvs.de/

    Dieser Quellcode ist lizenziert unter einer
    Creative Commons Namensnennung 4.0 International Lizenz.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        <c:choose>
            <c:when test="${edit}">
                Trip bearbeiten
            </c:when>
            <c:otherwise>
                Trip anlegen
            </c:otherwise>
        </c:choose>
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/trip_edit.css"/>" />
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script>
        $( function() {
          $( ".datepicker" ).datepicker({dateFormat: "dd.mm.yy" , dayNames: ["Sonntag", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag"], dayNamesMin: [ "So", "Mo", "Di", "Mi", "Do", "Fr", "Sa" ], monthNames: [ "Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember" ]});
        } );
        </script>
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>
        
        <div class="menuitem">
            <a href="<c:url value="/app/trips/list/"/>">Reiseliste</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <form method="post" class="stacked">
            <div class="column">
                <%-- CSRF-Token --%>
                <input type="hidden" name="csrf_token" value="${csrf_token}">

                <%-- Eingabefelder --%>
                <label for="trip_owner">Eigentümer:</label>
                <div class="side-by-side">
                    <input type="text" name="trip_owner" value="${trip_form.values["trip_owner"][0]}" readonly="readonly">
                </div>

                <label for="trip_country">Land:</label>
                <div class="side-by-side">
                    <select onchange="newCountry(this)" name="trip_country">
                        <option value="">Bitte ein Land auswählen</option>
                        <c:forEach items="${categories}" var="country">
                            <option value="${country.id}" ${trip_form.values["trip_country"][0] == country.id.toString() ? 'selected' : ''}>
                                <c:out value="${country.name}" />
                            </option>
                        </c:forEach>
                        <option id="new" value="<c:url value="/app/trips/categories/"/>">+ Neues Country hinzufügen</option>
                    </select>
                </div>

                <label for="trip_due_date">
                    Zeitraum:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                   <!-- <input type="text" name="trip_von_date" value="${trip_form.values["trip_von_date"][0]}">-->
                    <input class="datepicker" type="text" name="trip_von_date" placeholder="Von:" value="${trip_form.values["trip_von_date"][0]}">
                    <input class="datepicker" type="text" name="trip_bis_date" placeholder="Bis:" value="${trip_form.values["trip_bis_date"][0]}">
                    <!--<input type="text" name="trip_bis_time" value="${trip_form.values["trip_bis_date"][0]}">-->
                </div>

                <label for="trip_status">
                    Status:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side margin">
                    <select name="trip_status">
                        <c:forEach items="${statuses}" var="status">
                            <option value="${status}" ${trip_form.values["trip_status"][0] == status ? 'selected' : ''}>
                                <c:out value="${status.label}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <label for="trip_short_text">
                    Ort:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="trip_short_text" value="${trip_form.values["trip_short_text"][0]}">
                </div>
                
                <label for="trip_owner">Reiseart</label>
                <div class="side-by-side">
                    <input type="text" name="trip_reiseart" value="${reiseart}">
                </div>

                <label for="trip_long_text">
                    Beschreibung:
                </label>
                <div class="side-by-side">
                    <textarea name="trip_long_text"><c:out value="${trip_form.values['trip_long_text'][0]}"/></textarea>
                </div>

                <%-- Button zum Abschicken --%>
                <div class="side-by-side">
                    <button class="icon-pencil" type="submit" name="action" value="save">
                        Sichern
                    </button>

                    <c:if test="${edit}">
                        <button class="icon-trash" type="submit" name="action" value="delete">
                            Löschen
                        </button>
                    </c:if>
                </div>
            </div>

            <%-- Fehlermeldungen --%>
            <c:if test="${!empty trip_form.errors}">
                <ul class="errors">
                    <c:forEach items="${trip_form.errors}" var="error">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </c:if>
        </form>                 
        <script>
        function newCountry(option){
            if(option[option.selectedIndex].id==="new"){
                window.location = option[option.selectedIndex].value;
            }
        }
        </script>
    </jsp:attribute>
</template:base>