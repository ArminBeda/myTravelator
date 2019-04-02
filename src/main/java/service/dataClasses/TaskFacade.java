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

import dhbwka.wwi.vertsys.javaee.jtodo.tasks.ejb.TaskBean;
import dhbwka.wwi.vertsys.javaee.jtodo.tasks.jpa.Task;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author jka
 */
@Stateless
public class TaskFacade {
    
    @EJB
    TaskBean taskBean;
    
    public List<TaskDTO> findAllTasks(){
        List<Task> tasks= taskBean.findAll();
        return tasks.stream().map((task) -> {
            TaskDTO taskDTO = new TaskDTO(task);
            return taskDTO;
        }).collect(Collectors.toList());
    }    
}
