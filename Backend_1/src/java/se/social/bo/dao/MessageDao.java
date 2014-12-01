/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.social.bo.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import se.social.persist.TMessage;
import se.social.persist.TUser;
import se.social.persist.data.UserMessage;


/**
 *
 * @author Teddy
 */
public final class MessageDao {

    //private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("SocialBackendPU");
    //private static EntityManager em  = emf.createEntityManager();
    private MessageDao() {
    }

    public static boolean sendMessage(UserMessage message) {
        boolean returnValue = false;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BackendPU");
        EntityManager em = emf.createEntityManager();

        TMessage messagePersist = new TMessage();

        messagePersist.setContent(message.getContent());
        messagePersist.setSendtime(new Date());
        messagePersist.setReadmessage(false);
        try {
            em.getTransaction().begin();

            Query q = em.createNamedQuery("TUser.findByUsername", TUser.class);
            TUser sender = (TUser) q.setParameter("username", message.getSender()).getSingleResult();
            TUser reciever = (TUser) q.setParameter("username", message.getReciever()).getSingleResult();

            messagePersist.setReciever(reciever);
            messagePersist.setSender(sender);

            em.persist(messagePersist);
            em.getTransaction().commit();
            returnValue = true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Error: " + e);
            returnValue = false;
        } finally {
           em.close();
        }

        return returnValue;
    }

    @SuppressWarnings("empty-statement")
    public static Collection<UserMessage> getConvo(String sender, String recivier) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BackendPU");
        EntityManager em = emf.createEntityManager();

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
        } finally {
           em.close();
        }

        if (persistentData != null) {
            for (TMessage item : persistentData) {
                //System.out.println(item.getMessageId());
                //System.out.println(item.getSender().getUsername());

                //System.out.println(item.getReciever().getUsername());

                //System.out.println(item.getSendtime());

                //System.out.println(item.getContent());

                //System.out.println(item.getReadmessage());

                UserMessage newItem = new UserMessage(item.getMessageId(), item.getSender().getUsername(), item.getReciever().getUsername(), item.getSendtime(), item.getContent(), item.getReadmessage());
                returndata.add(newItem);
            }
            //	public UserMessage(int message_id, int sender, int reciever, Date sendtime, String content, boolean readmessage){
        }

        return returndata;
    }

}
