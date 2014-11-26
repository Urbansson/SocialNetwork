/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.social.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import se.social.data.UserData;
import se.social.data.UserMessage;
import se.social.entities.TMessage;
import se.social.entities.TUser;

/**
 *
 * @author Teddy
 */
public class MessageDao {
    
    private EntityManager em;

    public MessageDao() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SocialBackendPU");
        em = emf.createEntityManager();
        
    }
    
    @SuppressWarnings("empty-statement")
    public Collection<UserMessage> getConvo(String sender, String recivier){
    
        
        Collection<UserMessage> returndata = new ArrayList<UserMessage>();
        Collection<TMessage> persistentData = null;

        try {
            em.getTransaction().begin();
           
            Query q = em.createNamedQuery("TUser.findByUsername", TUser.class);
            TUser entitySender = (TUser) q.setParameter("username", sender).getSingleResult();
            TUser entityRecivier = (TUser) q.setParameter("username", recivier).getSingleResult();

            System.out.println(entitySender.getFirstname());
            System.out.println(entityRecivier.getFirstname());

            q = em.createNamedQuery("TMessage.getConvo", TMessage.class);
            persistentData = q.setParameter("sender", entitySender).setParameter("reciever", entityRecivier).getResultList();
            
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            persistentData = null;
            System.out.println("Error " + e);
        }
        
        if(persistentData != null){
            Iterator<TMessage> i;
            for (TMessage item : persistentData) {
                UserMessage newItem = new UserMessage(item.getMessageId(),item.getSender().getUsername(),item.getReciever().getUsername(), item.getSendtime(),item.getContent(),item.getReadmessage());
                returndata.add(newItem);
            }   
            //	public UserMessage(int message_id, int sender, int reciever, Date sendtime, String content, boolean readmessage){
        }
               
        return returndata;
    }
    
    
}
