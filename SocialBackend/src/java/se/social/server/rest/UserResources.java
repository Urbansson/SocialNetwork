/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.social.server.rest;

import com.google.gson.Gson;
import java.util.Collection;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import se.social.dao.UserDao;
import se.social.data.UserData;

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
        
        //UserDao doa = new UserDao();
        Gson gson = new Gson();
        UserData user = UserDao.getUserByUsername(userName);
        
        if (user != null) {
            if(user.getPassword().equals(password))
                return gson.toJson(user, UserData.class);
        }
        return "{success: false}";
    }
    
    
    @GET @Path("/getuserbyname/{username}")
    @Produces("application/json")
    public String getUserByName(@PathParam("username") String userName) {
        //TODO return proper representation object
        //throw new UnsupportedOperationException();
        //UserDao doa = new UserDao();
        Gson gson = new Gson();
        UserData user = UserDao.getUserByUsername(userName);
        
        if(user != null){
            return gson.toJson(user, UserData.class);
        }else{
            return "{success: false}";
        }
    }
    
    @GET @Path("/getuserbyemail/{email}")
    @Produces("application/json")
    public String getUserByEmail(@PathParam("email") String email) {

        Gson gson = new Gson();
        UserData user = UserDao.getUserByEmail(email);
        
        if(user != null){
            return gson.toJson(user, UserData.class);
        }else{
            return "{success: false}";
        }
    }
    
    @GET @Path("/checkifriends/{user1}/{user2}")
    @Produces("application/json")
    public String checkIfFriends(@PathParam("user1") String user1,@PathParam("user2") String user2) {

        boolean isFriends = false;
                Collection<UserData> friends= UserDao.getFriends(user1);
        for (UserData friend : friends) {
            if(friend.getUserName().equals(user2)){
                isFriends = true;
                break;
            }
        }
        return "{isFriens: "+isFriends+ "}";
    }
    
    
    @GET @Path("/getuserFriends/{username}")
    @Produces("application/json")
    public String getUserFriends(@PathParam("username") String username) {
        //TODO return proper representation object
        //throw new UnsupportedOperationException();
        
        System.out.println("getUserFriends request recived: " + username);
                Gson gson = new Gson();

        //UserDao doa = new UserDao();

        Collection<UserData> friends= UserDao.getFriends(username);
        if(friends.size()>=1){
            return gson.toJson(friends, Collection.class);
        }else{
            return "{success: false}";
        } 
          
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
        
        //UserDao doa = new UserDao();
       
        Gson gson = new Gson();
        UserData data = gson.fromJson(content, UserData.class);

        if(UserDao.registerNewUser(data)){
            return gson.toJson(data, UserData.class);
        }else{
            return "{success: false}";
        } 
    }
    
    @GET @Path("/addFriend/{user1}/{user2}")
    @Produces("application/json")
    public String addFriend(@PathParam("user1") String frendee,@PathParam("user2") String frender) {
        
        
        System.out.println("Frendship request recived: " + frendee+" "+ frender);

        if(UserDao.sendFriendRequest(frendee, frender)){
            return "{success: true}";
        }else{
            return "{success: false}";
        } 

    }
    
    @GET @Path("/removeFriend/{user1}/{user2}")
    @Produces("application/json")
    public String removeFriend(@PathParam("user1") String user,@PathParam("user2") String toRemove) {
        
        System.out.println("Frendship request recived: " + user+" "+ toRemove);

        if(UserDao.removeFriend(user, toRemove)){
            return "{success: true}";
        }else{
            return "{success: false}";
        } 

    }
    
    
}
