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
import dhbwka.wwi.vertsys.javaee.jtodo.common.jpa.User;
import dhbwka.wwi.vertsys.javaee.jtodo.dashboard.ejb.DashboardContentProvider;
import dhbwka.wwi.vertsys.javaee.jtodo.dashboard.ejb.DashboardSection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author yusefoenkol
 */
/**
 * Servlet für die Benutzereinstellungen.
 */
@WebServlet(urlPatterns = {"/app/settings/"})
public class SettingsServlet extends HttpServlet{
    
    // Kacheln für Aufgaben
    @EJB(beanName = "UserBean")
    UserBean userbean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Dashboard-Rubriken und Kacheln erzeugen und im Request Context ablegen
        List<DashboardSection> sections = new ArrayList<>();
        request.setAttribute("sections", sections);

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/change/change_userdata.jsp").forward(request, response);
        
        
        User user = userbean.getCurrentUser();
        
        request.setAttribute("username", user.getUsername());
    }
    
}
