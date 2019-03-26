/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.jtodo.settings.web;

import dhbwka.wwi.vertsys.javaee.jtodo.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.jtodo.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.jtodo.common.jpa.User;
import dhbwka.wwi.vertsys.javaee.jtodo.common.jpa.User.Password;
import dhbwka.wwi.vertsys.javaee.jtodo.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.jtodo.common.web.WebUtils;
import dhbwka.wwi.vertsys.javaee.jtodo.dashboard.ejb.DashboardSection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author yusefoenkol
 */
/**
 * Servlet für die Benutzereinstellungen.
 */
@WebServlet(urlPatterns = {"/app/settings/"})
public class SettingsServlet extends HttpServlet {

    @EJB
    ValidationBean validationBean;

    @EJB
    UserBean userBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getSession().setAttribute("User", this.userBean.getCurrentUser());

        //HttpSession session = request.getSession();
        // Anfrage an die JSP weiterleiten
        //request.getSession().setAttribute("signup_username", username);
        //request.getSession().setAttribute("signup_last_name", signup_last_name);
        request.getRequestDispatcher("/WEB-INF/change/change_userdata.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = this.userBean.getCurrentUser();

        // Formulareingaben auslesen        
        String password_old = request.getParameter("password_old");
        String password_new = request.getParameter("password_new");
        //setAttribute("User", this.userBean.getCurrentUser());
        //request.getParameter("password");
        //String password2 = request.getParameter("signup_password2");
        String first_name = request.getParameter("first_name");
        String last_name = request.getParameter("last_name");

        //user.setUsername(username);
        //user.setFirst_name(first_name);
        //user.setLast_name(last_name);
        List<String> errors = this.validationBean.validate(user);

        if (errors.isEmpty()) {
            if (first_name.isEmpty()) {
            } else {
                this.userBean.updateFirstName(user, first_name);
            }

            if (last_name.isEmpty()) {
            } else {
                this.userBean.updateLastName(user, last_name);
            }
            /* try {
                this.userBean.updateCredentials(user, first_name, last_name);
            } catch (UserBean.UserAlreadyExistsException ex) {
                errors.add(ex.getMessage());
            }*/
            if (password_new.isEmpty()) {
            } else if (password_old.isEmpty()) {
                errors.add("Das Passwort konnte nicht geändert werden, bitte tragen Sie das Alte, sowie das Neue Passwort ein.");
            } else {

                try {
                    this.userBean.changePassword(user, password_old, password_new);
                } catch (UserBean.InvalidCredentialsException ex) {
                    errors.add(ex.getMessage());
                }
            }

        }

        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            //request.login(username, password.password);
            response.sendRedirect(WebUtils.appUrl(request, "/app/dashboard/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("signup_form", formValues);

            response.sendRedirect(request.getRequestURI());
        }
    }

}
