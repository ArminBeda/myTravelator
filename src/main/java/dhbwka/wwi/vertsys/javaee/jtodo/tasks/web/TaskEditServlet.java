/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.jtodo.tasks.web;

import dhbwka.wwi.vertsys.javaee.jtodo.common.web.WebUtils;
import dhbwka.wwi.vertsys.javaee.jtodo.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.jtodo.tasks.ejb.CategoryBean;
import dhbwka.wwi.vertsys.javaee.jtodo.tasks.ejb.TaskBean;
import dhbwka.wwi.vertsys.javaee.jtodo.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.jtodo.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.jtodo.tasks.jpa.Task;
import dhbwka.wwi.vertsys.javaee.jtodo.tasks.jpa.TaskStatus;
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
@WebServlet(urlPatterns = "/app/tasks/task/*")
public class TaskEditServlet extends HttpServlet {

    @EJB
    TaskBean taskBean;

    @EJB
    CategoryBean categoryBean;

    @EJB
    UserBean userBean;

    @EJB
    ValidationBean validationBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Kategorien und Stati für die Suchfelder ermitteln
        request.setAttribute("categories", this.categoryBean.findAllSorted());
        request.setAttribute("statuses", TaskStatus.values());

        // Zu bearbeitende Aufgabe einlesen
        HttpSession session = request.getSession();

        Task task = this.getRequestedTask(request);
        request.setAttribute("edit", task.getId() != 0);
                                
        if (session.getAttribute("task_form") == null) {
            // Keine Formulardaten mit fehlerhaften Daten in der Session,
            // daher Formulardaten aus dem Datenbankobjekt übernehmen
            request.setAttribute("task_form", this.createTaskForm(task));
        }
        

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/tasks/task_edit.jsp").forward(request, response);
        
        session.removeAttribute("task_form");
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
                this.saveTask(request, response);
                break;
            case "delete":
                this.deleteTask(request, response);
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
    private void saveTask(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben prüfen
        List<String> errors = new ArrayList<>();

        String taskCategory = request.getParameter("task_category");
        String taskvonDate = request.getParameter("task_von_date");
        String taskbisDate = request.getParameter("task_bis_date");
        String taskStatus = request.getParameter("task_status");
        String taskShortText = request.getParameter("task_short_text");
        String taskLongText = request.getParameter("task_long_text");

        Task task = this.getRequestedTask(request);

        if (taskCategory != null && !taskCategory.trim().isEmpty()) {
            try {
                task.setCategory(this.categoryBean.findById(Long.parseLong(taskCategory)));
            } catch (NumberFormatException ex) {
                // Ungültige oder keine ID mitgegeben
            }
        }

        Date vonDate = WebUtils.parseDate(taskvonDate);
        Date bisDate = WebUtils.parseDate(taskbisDate);

        if (vonDate != null) {
            task.setvonDate(vonDate);
        } else {
            errors.add("Das Datum muss dem Format dd.mm.yyyy entsprechen.");
        }

        if (bisDate != null) {
            task.setbisDate(bisDate);
        } else {
            errors.add("Das Datum muss dem Format dd.mm.yyyy entsprechen.");
        }

        try {
            task.setStatus(TaskStatus.valueOf(taskStatus));
        } catch (IllegalArgumentException ex) {
            errors.add("Der ausgewählte Status ist nicht vorhanden.");
        }

        task.setShortText(taskShortText);
        task.setLongText(taskLongText);

        this.validationBean.validate(task, errors);

        // Datensatz speichern
        if (errors.isEmpty()) {
            this.taskBean.update(task);
        }

        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            response.sendRedirect(WebUtils.appUrl(request, "/app/tasks/list/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("task_form", formValues);

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
    private void deleteTask(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Datensatz löschen
        Task task = this.getRequestedTask(request);
        this.taskBean.delete(task);

        // Zurück zur Übersicht
        response.sendRedirect(WebUtils.appUrl(request, "/app/tasks/list/"));
    }

    /**
     * Zu bearbeitende Aufgabe aus der URL ermitteln und zurückgeben. Gibt
     * entweder einen vorhandenen Datensatz oder ein neues, leeres Objekt
     * zurück.
     *
     * @param request HTTP-Anfrage
     * @return Zu bearbeitende Aufgabe
     */
    private Task getRequestedTask(HttpServletRequest request) {
        // Zunächst davon ausgehen, dass ein neuer Satz angelegt werden soll
        Task task = new Task();
        task.setOwner(this.userBean.getCurrentUser());
        task.setvonDate(new Date(System.currentTimeMillis()));
        task.setbisDate(new Date(System.currentTimeMillis()));

        // ID aus der URL herausschneiden
        String taskId = request.getPathInfo();

        if (taskId == null) {
            taskId = "";
        }

        taskId = taskId.substring(1);

        if (taskId.endsWith("/")) {
            taskId = taskId.substring(0, taskId.length() - 1);
        }

        // Versuchen, den Datensatz mit der übergebenen ID zu finden
        try {
            task = this.taskBean.findById(Long.parseLong(taskId));
        } catch (NumberFormatException ex) {
            // Ungültige oder keine ID in der URL enthalten
        }

        return task;
    }

    /**
     * Neues FormValues-Objekt erzeugen und mit den Daten eines aus der
     * Datenbank eingelesenen Datensatzes füllen. Dadurch müssen in der JSP
     * keine hässlichen Fallunterscheidungen gemacht werden, ob die Werte im
     * Formular aus der Entity oder aus einer vorherigen Formulareingabe
     * stammen.
     *
     * @param task Die zu bearbeitende Aufgabe
     * @return Neues, gefülltes FormValues-Objekt
     */
    private FormValues createTaskForm(Task task) {
        Map<String, String[]> values = new HashMap<>();

        values.put("task_owner", new String[]{
            task.getOwner().getUsername()
        });

        if (task.getCategory() != null) {
            values.put("task_category", new String[]{
                "" + task.getCategory().getId()
            });
        }

        values.put("task_von_date", new String[]{
            WebUtils.formatDate(task.getvonDate())
        });

        values.put("task_bis_date", new String[]{
            WebUtils.formatDate(task.getbisDate())
        });

        values.put("task_status", new String[]{
            task.getStatus().toString()
        });

        values.put("task_short_text", new String[]{
            task.getShortText()
        });

        values.put("task_long_text", new String[]{
            task.getLongText()
        });

        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }

}
