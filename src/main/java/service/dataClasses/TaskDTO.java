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
    private UserDTO userDto;
    private String shorttext;
    private String longtext;
    private String reiseart;
    private TaskStatus status;
    private Date vonDate;
    private Date bisDate;

    public TaskDTO() {
    }
   
    
    public TaskDTO(Task task){
        this.id = task.getId();
        this.userDto = new UserDTO(task.getOwner());
        this.shorttext = task.getShortText();
        this.longtext = task.getLongText();
        this.reiseart = task.getLongText();
        this.vonDate = task.getvonDate();
        this.bisDate = task.getbisDate();
        this.status = task.getStatus();
        
        
    }
    

    
}
