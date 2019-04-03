/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package service.dataClasses;

import dhbwka.wwi.vertsys.javaee.jtodo.tasks.jpa.Task;
import dhbwka.wwi.vertsys.javaee.jtodo.tasks.jpa.TaskStatus;
import java.util.Date;

/**
 *
 * @author jka
 */
public class TaskDTO {
    private long id;
    private UserDTO owner;
    private String city;
    private String description;
    private String typeOfTravel;
    private TaskStatus status;
    private Date vonDate;
    private Date bisDate;

    public TaskDTO() {
    }
   
    
    public TaskDTO(Task task){
        this.id = task.getId();
        this.owner = new UserDTO(task.getOwner());
        this.city = task.getShortText();
        this.description = task.getLongText();
        this.typeOfTravel = task.getLongText();
        this.vonDate = task.getvonDate();
        this.bisDate = task.getbisDate();
        this.status = task.getStatus();
        
        
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypeOfTravel() {
        return typeOfTravel;
    }

    public void setTypeOfTravel(String typeOfTravel) {
        this.typeOfTravel = typeOfTravel;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Date getVonDate() {
        return vonDate;
    }

    public void setVonDate(Date vonDate) {
        this.vonDate = vonDate;
    }

    public Date getBisDate() {
        return bisDate;
    }

    public void setBisDate(Date bisDate) {
        this.bisDate = bisDate;
    }
    

    
}
