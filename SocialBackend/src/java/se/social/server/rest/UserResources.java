/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.social.server.rest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;

/**
 * REST Web Service
 *
 * @author Teddy
 */
@Path("UserResources")
public class UserResources {

    @Context
    private UriInfo context;
    /**
     * Creates a new instance of UserResources
     */
    public UserResources() {
    }

    /**
     * Retrieves representation of an instance of se.social.server.rest.UserResources
     * @param userName
     * @param password
     * @return an instance of java.lang.String
     */
    @GET @Path("/login/{username}/{password}")
    @Produces("application/json")
    public String login(@PathParam("username") String userName, @PathParam("password") String password) {
        //TODO return proper representation object
        //throw new UnsupportedOperationException();
        
        if ((userName.equals("teddy") && password.equals("password"))) {
            return "{\"userName\":\"Urbansson\",\"firstName\":\"Teddy\",\"lastName\":\"Brandt\",\"email\":\"teddy@brandts.se\",\"password\":\"password\"}";
        }
        return "{success: false}";
    }
    
    
        @GET @Path("/getuserbyname/{username}")
    @Produces("application/json")
    public String getUserByName(@PathParam("username") String userName) {
        //TODO return proper representation object
        //throw new UnsupportedOperationException();
        
        if ((userName.equals("teddy"))) {
            return "{\"userName\":\"Urbansson\",\"firstName\":\"Teddy\",\"lastName\":\"Brandt\",\"email\":\"teddy@brandts.se\",\"password\":\"password\"}";
        }
        return "{errorMessage: \"not found\"}";
    }
    /**
     *
     * @param content
     * @return
     */
    
    
    @PUT @Path("/register")
    @Consumes("application/json")
    @Produces("application/json")
    public String register(String content) {
        
        System.out.println("register recived recived: " + content);
        
        return "{\"userName\":\"Urbansson\",\"firstName\":\"Teddy\",\"lastName\":\"Brandt\",\"email\":\"teddy@brandts.se\",\"password\":\"password\"}";
    }
}
