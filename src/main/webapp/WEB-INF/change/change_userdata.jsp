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
            <form method="get" class="stacked">
                <div class="column">
                    <%-- CSRF-Token --%>
                    <input type="hidden" name="csrf_token" value="${csrf_token}">

                    <%-- Eingabefelder --%>
                    <label for="signup_username">
                        Benutzername:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="username" value="${s}">
                    </div>
                    
                    
                    <label for="signup_password2">
                        Nachname:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="signup_last_name" value="${signup_form.values["signup_last_name"][0]}">
                    </div>
                    
                    <label for="signup_password2">
                        Vorname:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="signup_first_name" value="${signup_form.values["signup_first_name"][0]}">
                    </div>
                    

                    <%-- Button zum Abschicken --%>
                    <div id="Sign_up_Register_Button" class="side-by-side">
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
