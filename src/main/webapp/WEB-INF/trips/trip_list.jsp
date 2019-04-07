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
        Liste der Reisen
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/trip_list.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/trips/trip/new/"/>">Neuer Trip</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/trips/categories/"/>">Länder bearbeiten</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <%-- Suchfilter --%>
        <form method="GET" class="horizontal" id="search">
            <input type="text" name="search_text" value="${param.search_text}" placeholder="Ort"/>

            <select name="search_country">
                <option value="">Alle Länder</option>

                <c:forEach items="${categories}" var="country">
                    <option value="${country.id}" ${param.search_country == country.id ? 'selected' : ''}>
                        <c:out value="${country.name}" />
                    </option>
                </c:forEach>
            </select>

            <select name="search_status">
                <option value="">Status</option>

                <c:forEach items="${statuses}" var="status">
                    <option value="${status}" ${param.search_status == status ? 'selected' : ''}>
                        <c:out value="${status.label}"/>
                    </option>
                </c:forEach>
            </select>

            <button class="" type="submit">
                Suchen
            </button>
        </form>

        <%-- Gefundene Aufgaben --%>
        <c:choose>
            <c:when test="${empty trips}">
                <p>
                    Es wurden keine Reisen gefunden.
                </p>
            </c:when>
            <c:otherwise>
                <jsp:useBean id="utils" class="dhbwka.wwi.vertsys.javaee.myTravelator.common.web.WebUtils"/>
                
                <table>
                    <thead>
                        <tr>
                            <th>Ort</th>
                            <th>Land</th>
                            <th>Eigentümer</th>
                            <th>Status</th>
                            <th>Von</th>
                            <th>Bis</th>
                            <th>Reiseart</th>
                        </tr>
                    </thead>
                    <c:forEach items="${trips}" var="trip">
                        <tr class="clickable-row" data-href="<c:url value="/app/trips/trip/${trip.id}/"/>">
                            <td>
                                <c:out value="${trip.shortText}"/>
                            </td>
                            <td>
                                <c:out value="${trip.country.name}"/>
                            </td>
                            <td>
                                <c:out value="${trip.owner.username}"/>
                            </td>
                            <td>
                                <c:out value="${trip.status.label}"/>
                            </td>
                            <td>
                                <c:out value="${utils.formatDate(trip.vonDate)}"/>
                            </td>
                            <td>
                                <c:out value="${utils.formatDate(trip.bisDate)}"/>
                            </td>
                            <td>
                                <c:out value="${trip.reiseart}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<script>
    jQuery(document).ready(function($) {
    $(".clickable-row").click(function() {
        window.location = $(this).data("href");
    });
});
</script>
    </jsp:attribute>
</template:base>