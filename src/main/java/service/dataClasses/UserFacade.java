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

import dhbwka.wwi.vertsys.javaee.myTravelator.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.myTravelator.common.jpa.User;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author yusefoenkol
 */
@Stateless
public class UserFacade {

    @EJB
    UserBean userBean;

    public List<UserDTO> findAll() {
        List<User> users = userBean.findAll();
        return users.stream().map((user) -> {
            UserDTO userDTO = new UserDTO(user);
            return userDTO;
        }).collect(Collectors.toList());
    }

    public UserDTO findUser(String id) {
        return new UserDTO(userBean.findUser(id));
    }

}
