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

import dhbwka.wwi.vertsys.javaee.myTravelator.common.jpa.User;

/**
 *
 * @author yoenkol
 */
public class UserDTO {
    
    private String username, firstname, lastname;

    public UserDTO(String username, String firstname, String lastname) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
    }
    
    public UserDTO(User user){
        this.username = user.getUsername();
        this.firstname = user.getFirst_name();
        this.lastname = user.getLast_name();
    }
    
    public UserDTO() {
    }
    
    
    //<editor-fold>

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    //</editor-fold>
    
    
    
    
}
