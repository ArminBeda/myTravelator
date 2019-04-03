/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.myTravelator.common.ejb;

import dhbwka.wwi.vertsys.javaee.myTravelator.common.jpa.User;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Spezielle EJB zum Anlegen eines Benutzers und Aktualisierung des Passworts.
 */
@Stateless
public class UserBean {

    @PersistenceContext
    EntityManager em;
    
    @Resource
    EJBContext ctx;

    /**
     * Gibt das Datenbankobjekt des aktuell eingeloggten Benutzers zurück,
     *
     * @return Eingeloggter Benutzer oder null
     */
    public User getCurrentUser() {
        return this.em.find(User.class, this.ctx.getCallerPrincipal().getName());
    }
    
    /**
     * Gibt alle Datenbankobjekt User zurück,
     *
     * @return Liste der registrierten User
     */
    public List<User> findAll(){
        return this.em.createQuery("SELECT u FROM User u").getResultList();
    }
    
    /**
     * Findet einen User nach seiner ID,
     *
     * @return User der gesuchten ID
     */
    public User findUser(String id){
        return em.find(User.class, id);
    }
   
    /**
     *
     * @param username
     * @param password
     * @throws UserBean.UserAlreadyExistsException
     */
    public void signup(String username, String password, String first_name, String last_name) throws UserAlreadyExistsException {
        if (em.find(User.class, username) != null) {
            throw new UserAlreadyExistsException("Der Benutzername $B ist bereits vergeben.".replace("$B", username));
        }

        User user = new User(username, password, last_name, first_name);
        user.addToGroup("app-user");
        em.persist(user);
    }

    /**
     * Passwort ändern (ohne zu speichern)
     * @param user
     * @param oldPassword
     * @param newPassword
     * @throws UserBean.InvalidCredentialsException
     */
    @RolesAllowed("app-user")
    public void changePassword(User user, String oldPassword, String newPassword) throws InvalidCredentialsException {
        if (user == null || !user.checkPassword(oldPassword)) {
            throw new InvalidCredentialsException("Das Alte Passwort stimmt nicht überein.");
        }
        user.setPassword(newPassword);
        em.merge(user);
        
    }
    
    public void updateCredentials(User user, String first_name, String last_name) throws UserAlreadyExistsException {
        //List <User> users = em.createQuery("SELECT u.username FROM User u WHERE u.username = :username").setParameter("username", username).getResultList();
        //if (users != null && users.size()>0) {
            if(first_name==null && first_name.isEmpty()){
                throw new UserAlreadyExistsException("Der Vorname darf nicht leer sein.");
            }
            if(last_name==null && last_name.isEmpty()){
                throw new UserAlreadyExistsException("Der Nachname darf nicht leer sein.");
            }
            user.setFirst_name(first_name);
            user.setLast_name(last_name);
            em.merge(user);
        
    }
    
    public void updateFirstName(User user, String vorname){
        user.setFirst_name(vorname);
        em.merge(user);
    }
    
    public void updateLastName(User user, String nachname){
        user.setLast_name(nachname);
        em.merge(user);
        
    }
    
    /**
     * Benutzer löschen
     * @param user Zu löschender Benutzer
     */
    @RolesAllowed("app-user")
    public void delete(User user) {
        this.em.remove(user);
    }
    
    /**
     * Benutzer aktualisieren
     * @param user Zu aktualisierender Benutzer
     * @return Gespeicherter Benutzer
     */
    @RolesAllowed("app-user")
    public User update(User user) {
        return em.merge(user);
    }

    public List<User> searchUser(String query) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * Fehler: Der Benutzername ist bereits vergeben
     */
    public class UserAlreadyExistsException extends Exception {

        public UserAlreadyExistsException(String message) {
            super(message);
        }
    }

    /**
     * Fehler: Das übergebene Passwort stimmt nicht mit dem des Benutzers
     * überein
     */
    public class InvalidCredentialsException extends Exception {

        public InvalidCredentialsException(String message) {
            super(message);
        }
    }

}
