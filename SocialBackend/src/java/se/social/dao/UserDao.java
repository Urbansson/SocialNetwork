/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.social.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import se.social.data.UserData;
import se.social.entities.TUser;

/**
 *
 * @author Teddy
 */
public class UserDao {

    private EntityManager em;

    public UserDao() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SocialBackendPU");
        em = emf.createEntityManager();
        
    }
    
    public boolean close(){
        em.flush();
        em.close();
        return true;
    }

    public UserData getUserById(int id){
    TUser user = null;

        try {
            em.getTransaction().begin();
            user = (TUser) em.find(TUser.class, id);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            user = null;
        }
        
        System.out.println(user.getFirstname());
        return null;
    }
    
    
    public UserData getUserByUsername(String username){
    TUser user = null;
    UserData returndata = null;
    
        try {
            em.getTransaction().begin();
           
            Query q = em.createNamedQuery("TUser.findByUsername", TUser.class);
            user = (TUser) q.setParameter("username", username).getSingleResult();
            
            //user = (UserData) em.find(UserData.class, id);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            user = null;
        }
        
        if(user != null){
            returndata = new UserData(user.getUsername(), user.getFirstname(), user.getLastname(), user.getEmail(), user.getPasswrd());
        }
        return returndata;
    }
    
    public UserData getUserByEmail(String email){
    TUser user = null;
    UserData returndata = null;
    
        try {
            em.getTransaction().begin();
           
            Query q = em.createNamedQuery("TUser.findByEmail", TUser.class);
            user = (TUser) q.setParameter("email", email).getSingleResult();
            
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            user = null;
        }
        
        if(user != null){
            returndata = new UserData(user.getUsername(), user.getFirstname(), user.getLastname(), user.getEmail(), user.getPasswrd());
        }
        return returndata;
    }
    
    public boolean registerNewUser(UserData user) {
        boolean returnValue = false;
        
        TUser userPersist = new TUser(); 
        
        userPersist.setUsername(user.getUserName());
        userPersist.setFirstname(user.getFirstName());
        userPersist.setLastname(user.getLastName());
        userPersist.setEmail(user.getEmail());
        userPersist.setPasswrd(user.getPassword());

        try {
            em.getTransaction().begin();
            em.persist(userPersist);
            em.getTransaction().commit();
            returnValue = true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            returnValue = false;
        }

        return returnValue;
    }

}
