/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.social.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import se.social.persist.data.PostComment;
import se.social.persist.data.UserPost;
import se.social.rest.PostRest;


/**
 *
 * @author Teddy
 */
@ManagedBean
@RequestScoped
public class WallBean {

    
        
    private String PostText;
    
    private String wallOwner;
    
    @ManagedProperty(value = "#{userBean}")
    private userBean user;
    
    //Getter and setter
    
    /**
     * Creates a new instance of WallBean
     */
    
    private List<UserPost> posts;
       
    
    
    public WallBean() {
    }
    /**
     * @return the posts
     */
    public List<UserPost> getPosts() {
        this.fetchWall();
            return posts;
    }

    /**
     * @param posts the posts to set
     */
    public void setPosts(List<UserPost> posts) {
        this.posts = posts;
    }
    
    public List<UserPost> fetchWall() {
        PostRest rest = new PostRest();
        System.out.println("Trying to get wall!");
                Gson gson = new Gson();


        System.out.println("Response from server: ");
        
        List<UserPost> posts = gson.fromJson(rest.getWall("Urbansson"), new TypeToken<List<UserPost>>(){}.getType());
        for (UserPost post : posts) {
            //System.out.println("At client: " + post.getContent());

              for (PostComment comment : post.getComments()) {
              //    System.out.println("comment: " + comment.getSender());
           
              //    System.out.println("comment: " + comment.getContent());
              }
        }
      
        this.posts = posts;
        return posts;
    }

    public void createPost() {
        
        System.out.println("Sending Post create");
        
        
        PostRest rest = new PostRest();

        UserPost post = new UserPost(0, "Urbansson", user.getUserName(), PostText, new Date(),  null);
        Gson gson = new Gson();

        String data = gson.toJson(post, UserPost.class);
            
        System.out.println("Response from Server:" + rest.addPost(data));
        
        
    
    }

    public void createComment(int id) {
        
        
        int pos= 0;
        for (UserPost post : posts) {
            //if(post.getPostId() == id)
                //break;
            pos++;
        }

        System.out.println("Making comment with id: " + id +" comment ");
        /*
        System.out.println("Sending comment create");
        
        PostRestClient rest = new PostRestClient();

        PostComment comment = new PostComment(id, "Urbansson", commentTxt[pos], null);
        //UserPost post = new UserPost(0, "Urbansson", "dolphan", "Hejsan på din wall", new Date(),  null);
        Gson gson = new Gson();

        String data = gson.toJson(comment, PostComment.class);
            
        System.out.println("Response from Server:" + rest.addComment(data));
*/
    }
    /**
     * @return the user
     */
    public userBean getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(userBean user) {
        this.user = user;
    }

    /**
     * @return the PostText
     */
    public String getPostText() {
        return PostText;
    }

    /**
     * @param PostText the PostText to set
     */
    public void setPostText(String PostText) {
        this.PostText = PostText;
    }


}
