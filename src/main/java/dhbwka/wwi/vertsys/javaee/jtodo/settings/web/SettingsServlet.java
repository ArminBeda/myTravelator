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
import dhbwka.wwi.vertsys.javaee.jtodo.dashboard.ejb.DashboardSection;
import java.io.IOException;
import java.util.ArrayList;
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
 *
 * @author yusefoenkol
 */
/**
 * Servlet für die Benutzereinstellungen.
 */
@WebServlet(urlPatterns = {"/app/settings/"})
public class SettingsServlet extends HttpServlet{
    
    // Kacheln für Aufgaben
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
    
}
