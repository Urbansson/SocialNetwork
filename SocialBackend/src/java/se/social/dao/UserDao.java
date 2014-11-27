/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.social.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import se.social.data.UserData;
import se.social.entities.TFriendship;
import se.social.entities.TFriendshipPK;
import se.social.entities.TUser;

/**
 *
 * @author Teddy
 */
public class UserDao {

//    private EntityManager em;
    private UserDao() {

    }

    public static UserData getUserById(int id) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SocialBackendPU");
        EntityManager em = emf.createEntityManager();
        TUser user = null;

        try {
            em.getTransaction().begin();
            user = (TUser) em.find(TUser.class, id);
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

    public static UserData getUserByUsername(String username) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SocialBackendPU");
        EntityManager em = emf.createEntityManager();
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
        } finally {
            em.close();
        }

        if (user != null) {
            returndata = new UserData(user.getUsername(), user.getFirstname(), user.getLastname(), user.getEmail(), user.getPasswrd());
        }
        return returndata;
    }

    public static UserData getUserByEmail(String email) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SocialBackendPU");
        EntityManager em = emf.createEntityManager();
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
        } finally {
            em.close();
        }

        if (user != null) {
            returndata = new UserData(user.getUsername(), user.getFirstname(), user.getLastname(), user.getEmail(), user.getPasswrd());
        }
        return returndata;
    }

    /**
     *
     * @param frendee
     * @param frender
     * @return
     */
    public static boolean sendFriendRequest(String frendee, String frender) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SocialBackendPU");
        EntityManager em = emf.createEntityManager();

        boolean returnValue = false;
        TFriendship persist = new TFriendship();

        try {
            em.getTransaction().begin();

            Query q = em.createNamedQuery("TUser.findByUsername", TUser.class);
            TUser Tfrendee = (TUser) q.setParameter("username", frendee).getSingleResult();
            TUser Tfrender = (TUser) q.setParameter("username", frender).getSingleResult();

            TFriendshipPK pk = new TFriendshipPK(Tfrender.getUserId(), Tfrendee.getUserId());
            TFriendshipPK pkinverse = new TFriendshipPK(Tfrendee.getUserId(), Tfrender.getUserId());

            persist.setTUser(Tfrendee);
            persist.setTUser1(Tfrender);
            persist.setTFriendshipPK(pk);
            persist.setAccepted(false);

            //Bad version of checking if frendship already exist!
            if (em.find(TFriendship.class, pk) != null) {
                em.find(TFriendship.class, pk).setAccepted(true);
            } else if (em.find(TFriendship.class, pkinverse) != null) {
                em.find(TFriendship.class, pkinverse).setAccepted(true);
            } else {
                em.persist(persist);
            }

            em.getTransaction().commit();
            returnValue = true;

        } catch (Exception e) {
            try {
                em.getTransaction().rollback();

            } catch (Exception e2) {
            }
            returnValue = false;
            System.out.println("Error: " + e);
        } finally {
            em.close();
        }

        return returnValue;
    }

    //Should not be needed can be handled in above method
    public static boolean removeFriend(String user, String toRemove) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SocialBackendPU");
        EntityManager em = emf.createEntityManager();
        boolean returnValue = false;

        try {
            em.getTransaction().begin();

            Query q = em.createNamedQuery("TUser.findByUsername", TUser.class);
            TUser Tuser = (TUser) q.setParameter("username", user).getSingleResult();
            TUser TtoRemove = (TUser) q.setParameter("username", toRemove).getSingleResult();

            TFriendshipPK pk = new TFriendshipPK(Tuser.getUserId(), TtoRemove.getUserId());
            TFriendshipPK pkinverse = new TFriendshipPK(TtoRemove.getUserId(), Tuser.getUserId());

            TFriendship friendship = em.find(TFriendship.class, pk);
            TFriendship friendshipinv = em.find(TFriendship.class, pkinverse);

            if (friendship != null) {
                em.remove(friendship);
                returnValue = true;

            } else if (friendshipinv != null) {
                em.remove(friendshipinv);
                returnValue = true;

            } else {
                returnValue = false;
            }

            em.flush();
            em.getTransaction().commit();

        } catch (Exception e) {
            try {
                em.getTransaction().rollback();
            } catch (Exception e2) {
            }
            returnValue = false;
            System.out.println("Error: " + e);
        } finally {
            em.close();
        }

        return returnValue;
    }

    public static boolean registerNewUser(UserData user) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SocialBackendPU");
        EntityManager em = emf.createEntityManager();
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
        } finally {
            em.close();
        }

        return returnValue;
    }

    public static Collection<UserData> getFriends(String user) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SocialBackendPU");
        EntityManager em = emf.createEntityManager();
        Collection<TFriendship> persistentFriends;
        Collection<UserData> friends = new HashSet<UserData>();

        try {
            em.getTransaction().begin();

            Query q = em.createNamedQuery("TUser.findByUsername", TUser.class);
            TUser Tuser = (TUser) q.setParameter("username", user).getSingleResult();

            persistentFriends = Tuser.getTFriendshipCollection();
            persistentFriends.addAll(Tuser.getTFriendshipCollection1());

            for (TFriendship friend : persistentFriends) {
                if (Objects.equals(Tuser.getUserId(), friend.getTUser().getUserId())) {
                    friends.add( new UserData(friend.getTUser1().getUsername(), friend.getTUser1().getFirstname(), friend.getTUser1().getLastname(), friend.getTUser1().getEmail(), null));                      
                } else {
                    friends.add( new UserData(friend.getTUser().getUsername(), friend.getTUser().getFirstname(), friend.getTUser().getLastname(), friend.getTUser().getEmail(), null));
                }
            }
            em.flush();
            em.getTransaction().commit();

        } catch (Exception e) {
            try {
                em.getTransaction().rollback();
            } catch (Exception e2) {
            }
            System.out.println("Error: " + e);
        } finally {
            em.close();
        }

        return friends;
    }

}
