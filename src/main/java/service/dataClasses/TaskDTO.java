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

import dhbwka.wwi.vertsys.javaee.jtodo.common.jpa.User;
import dhbwka.wwi.vertsys.javaee.jtodo.tasks.jpa.Category;
import dhbwka.wwi.vertsys.javaee.jtodo.tasks.jpa.Task;
import dhbwka.wwi.vertsys.javaee.jtodo.tasks.jpa.TaskStatus;
import java.util.Date;

/**
 *
 * @author jka
 */
public class TaskDTO {
//    private User owner;
    private UserDTO owner;
    private Category category;
    private String shortText;
    private String longText;
    private Date vonDate;
    private Date bisDate;
    private String reiseart;
    private TaskStatus taskStatus;

    public TaskDTO() {
    }

    public TaskDTO(User owner, Category category, String shortText, String longText, Date vonDate, Date bisDate, String reiseart, TaskStatus taskStatus) {
        this.owner = new UserDTO(owner);
        this.category = category;
        this.shortText = shortText;
        this.longText = longText;
        this.vonDate = vonDate;
        this.bisDate = bisDate;
        this.reiseart = reiseart;
        this.taskStatus = taskStatus;
    }
    
    public TaskDTO(Task task){
        this.owner = new UserDTO(task.getOwner()); 
        this.category = task.getCategory();
        this.shortText = task.getShortText();
        this.longText = task.getLongText();
        this.reiseart = task.getReiseart();
        this.taskStatus = task.getStatus();
        this.vonDate = task.getvonDate();
        this.bisDate = task.getbisDate();
    }
    

   public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public String getLongText() {
        return longText;
    }

    public void setLongText(String longText) {
        this.longText = longText;
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

    public String getReiseart() {
        return reiseart;
    }

    public void setReiseart(String reiseart) {
        this.reiseart = reiseart;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}
