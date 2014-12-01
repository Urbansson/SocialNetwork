/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.social.persist.service;

import com.google.gson.Gson;
import java.util.Collection;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import se.social.bo.dao.UserDao;
import se.social.bo.dao.WallDao;
import se.social.persist.data.PostComment;
import se.social.persist.data.UserData;
import se.social.persist.data.UserPost;

/**
 *
 * @author Teddy
 */
@Path("PostResources")
public class PostRest {
    
    public PostRest(){}
    /**
     * Retrieves representation of an instance of se.social.dao.PostResources
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/getwall/{username}")
    @Produces("application/json")
    public String getWall(@PathParam("username") String userName) {
        //TODO return proper representation object

        Collection<UserPost> posts = WallDao.getWall(userName);
        
        Gson gson = new Gson();
        String data = gson.toJson(posts, Collection.class);

        //throw new UnsupportedOperationException();

        return data;
    }

    /**
     * PUT method for updating or creating an instance of PostResources
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
    
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    } */

    @PUT
    @Path("/addPost")
    @Consumes("application/json")
    @Produces("application/json")
    public String addPost(String content) {

        System.out.println("Adding post request recived: " + content);

        
        Gson gson = new Gson();
        UserPost data = gson.fromJson(content, UserPost.class);

        if (WallDao.addPost(data.getReciver(), data.getSender(), data.getContent())) {
            return "{success: true}";
        }
        return "{success: false}";
                
        
        //return "Hello";
    }

    @PUT
    @Path("/addComment")
    @Consumes("application/json")
    @Produces("application/json")
    public String addComment(String content) {

        System.out.println("Adding comment request recived: " + content);

        Gson gson = new Gson();
        PostComment data = gson.fromJson(content, PostComment.class);

        if (WallDao.addComment(data.getCommentId(), data.getSender(), data.getContent())) {
            return "{success: true}";
        }
        return "{success: false}";
        
    }

}
