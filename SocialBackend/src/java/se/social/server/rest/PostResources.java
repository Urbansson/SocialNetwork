/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.social.server.rest;

import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import se.social.dao.WallDao;
import se.social.data.UserPost;

/**
 * REST Web Service
 *
 * @author Teddy
 */
@Path("PostResources")
public class PostResources {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PostResources
     */
    public PostResources() {
    }

    /**
     * Retrieves representation of an instance of se.social.dao.PostResources
     * @return an instance of java.lang.String
     */
    @GET  @Path("/getwall/{username}")
    @Produces("application/json")
    public String getWall(@PathParam("username") String userName) {
        //TODO return proper representation object
        
        WallDao.getWall(userName);
        //throw new UnsupportedOperationException();
        
        return "Hello";
    }

    /**
     * PUT method for updating or creating an instance of PostResources
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
    
    @PUT @Path("/addPost")
    @Consumes("application/json")
    @Produces("application/json")
    public String addPost(String content) {
        
        
        System.out.println("Adding post request recived: " + content);

        Gson gson = new Gson();
        UserPost data = gson.fromJson(content, UserPost.class);

        /*
        System.out.println("register recived recived: " + content);
        
        UserDao doa = new UserDao();
       
        Gson gson = new Gson();
        UserData data = gson.fromJson(content, UserData.class);

        //doa.registerNewUser(data);
        if(doa.registerNewUser(data)){
            return gson.toJson(data, UserData.class);
        }else{
            return "{success: false}";
        } 
                */
        return "{success: false}";
    }
    
    @PUT @Path("/addComment")
    @Consumes("application/json")
    @Produces("application/json")
    public String addComment(String content) {
        
        /*
        System.out.println("register recived recived: " + content);
        
        UserDao doa = new UserDao();
       
        Gson gson = new Gson();
        UserData data = gson.fromJson(content, UserData.class);

        //doa.registerNewUser(data);
        if(doa.registerNewUser(data)){
            return gson.toJson(data, UserData.class);
        }else{
            return "{success: false}";
        } 
                */
        return "{success: false}";
    }
}
