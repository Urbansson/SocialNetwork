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
import se.social.persist.TComments;
import se.social.persist.TPost;
import se.social.persist.TUser;
import se.social.persist.data.PostComment;
import se.social.persist.data.UserPost;


/**
 *
 * @author Teddy
 */
public final class WallDao {

    //Make so you cant make a object
    private WallDao() {
    }

    public static Collection<UserPost> getWall(String username) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BackendPU");
        EntityManager em = emf.createEntityManager();
        TUser user = null;
        Collection<TPost> posts;

        Collection<UserPost> returnPosts = new ArrayList<UserPost>();

        try {
            em.getTransaction().begin();

            Query q = em.createNamedQuery("TUser.findByUsername", TUser.class);
            user = (TUser) q.setParameter("username", username).getSingleResult();

            q = em.createNamedQuery("TPost.findByrecievingwall", TPost.class);
            posts = (Collection<TPost>) q.setParameter("user", user).getResultList();

            System.out.println("POSTS FOUND: " + posts.size());
            for (TPost post : posts) {
                ArrayList<PostComment> tmpcomments = new ArrayList<PostComment>();
                for (TComments comment : post.getTCommentsCollection()) {
                    PostComment tmpcomment = new PostComment(post.getPostId(), comment.getAuthor().getUsername(), comment.getContent(), comment.getPublishtime());
                    tmpcomments.add(tmpcomment);
                }
                UserPost tmppost = new UserPost(post.getPostId(), post.getRecievingwall().getUsername(), post.getAuthor().getUsername(), post.getContent(), post.getPublishtime(), tmpcomments);
                returnPosts.add(tmppost);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            user = null;
        } finally {
            em.close();
        }
        //System.out.println(user.getFirstname());
        return returnPosts;
    }

    public static boolean addPost(String reciver, String sender, String content) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BackendPU");
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
            senderUser = (TUser) q.setParameter("username", sender).getSingleResult();

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

    public static boolean addComment(int postId, String sender, String content) {

        boolean success = false;

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BackendPU");
        EntityManager em = emf.createEntityManager();

        TUser senderUser;

        TComments commentpersist = new TComments();

        commentpersist.setContent(content);
        commentpersist.setPublishtime(new Date());

        System.out.println("Trying to add comment");
        try {
            em.getTransaction().begin();

            Query q = em.createNamedQuery("TUser.findByUsername", TUser.class);
            senderUser = (TUser) q.setParameter("username", sender).getSingleResult();

            q = em.createNamedQuery("TPost.findByPostId", TPost.class);
            TPost post = (TPost) q.setParameter("postId", postId).getSingleResult();

            System.out.println(post.getContent());

            commentpersist.setAtpost(post);
            commentpersist.setAuthor(senderUser);

            em.persist(commentpersist);
            em.getTransaction().commit();
            success = true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("EROROROOROROROROROROROROROROOR: " + e);
            success = false;
        } finally {
            em.close();
        }

        return success;

    }

}
