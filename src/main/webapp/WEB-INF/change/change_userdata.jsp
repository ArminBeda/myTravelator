<%-- 
    Document   : change_userdata
    Created on : 21.03.2019, 09:38:43
    Author     : jka
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Einstellungen
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/user_styling.css"/>" />
    </jsp:attribute>


    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/tasks/list/"/>">Reiseliste</a>
        </div>
    </jsp:attribute>


    <jsp:attribute name="content">
        <div class="container">
            <form method="post" class="stacked">
                <div class="column">
                    <%-- CSRF-Token --%>
                    <input type="hidden" name="csrf_token" value="${csrf_token}">

                    <%-- Eingabefelder --%>
                    <!--<label for="user_name" >
                        Benutzername:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="user_name" value="" placeholder="Alt: ${User.getUsername()}">
                    </div>-->


                    <label for="last_name">
                        Nachname:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="last_name" value="" placeholder="Alt: ${User.getLast_name()}">
                    </div>

                    <label for="first_name">
                        Vorname:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="first_name" value="" placeholder="Alt: ${User.getFirst_name()}">
                    </div>
                    
                    <label for="password_old">
                        Altes Passwort:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="password" name="password_old" value="" placeholder="Gebe hier dein altes Passwort ein">
                    </div>
                    
                    <label for="password_new">
                        Neues Passwort:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="password" name="password_new" value="" placeholder="Gebe hier dein neues Passwort ein">
                    </div>

                    <%-- Button zum Abschicken --%>
                    <div id="Change_settings_button" class="side-by-side">
                        <button class="icon-pencil" type="submit">
                            Ändern
                        </button>
                    </div>
                </div>

                <%-- Fehlermeldungen --%>
                <c:if test="${!empty signup_form.errors}">
                    <ul class="errors">
                        <c:forEach items="${signup_form.errors}" var="error">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </c:if>
            </form>
        </div>
    </jsp:attribute>
</template:base>    
