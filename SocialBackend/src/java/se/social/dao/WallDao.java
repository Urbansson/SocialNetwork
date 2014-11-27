/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.social.dao;

import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import se.social.data.UserData;
import se.social.entities.TPost;
import se.social.entities.TUser;

/**
 *
 * @author Teddy
 */
public final class WallDao {
    
    //Make so you cant make a object
    private WallDao(){}
    
        public static UserData getWall(String username) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SocialBackendPU");
        EntityManager em = emf.createEntityManager();
        TUser user = null;
       Collection<TPost> posts;

        try {
            em.getTransaction().begin();
            
            Query q = em.createNamedQuery("TUser.findByUsername", TUser.class);
            user = (TUser) q.setParameter("username", username).getSingleResult();
            
            q = em.createNamedQuery("TPost.findByrecievingwall", TPost.class);
            user = (TUser) q.setParameter("user", user).getResultList();
            
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            user = null;
        } finally {
            em.close();
        }
        //System.out.println(user.getFirstname());
        return null;
    }
        
    public static boolean addPost(String reciver, String sender, String content){
    
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SocialBackendPU");
        EntityManager em = emf.createEntityManager();
        boolean success = false;
        
        TUser reciverUser;
        TUser senderUser;

        TPost postpersist = new TPost();
        
        postpersist.setContent(content);
        postpersist.setPublishtime(new Date());
        
        try {
            em.getTransaction().begin();
            
            Query q = em.createNamedQuery("TUser.findByUsername", TUser.class);
            
            reciverUser = (TUser) q.setParameter("username", reciver).getSingleResult();
            senderUser = (TUser) q.setParameter("username", reciver).getSingleResult();

            postpersist.setRecievingwall(reciverUser);
            postpersist.setAuthor(senderUser);
            
            em.persist(postpersist);
            
            em.getTransaction().commit();
            success = true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            success = false;
        } finally {
            em.close();
        }
        
        return success;
    }
    
}
