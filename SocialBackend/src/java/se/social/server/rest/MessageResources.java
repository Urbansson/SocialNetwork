/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.social.server.rest;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collection;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import se.social.dao.MessageDao;
import se.social.data.UserMessage;
import se.social.entities.TMessage;

/**
 * REST Web Service
 *
 * @author Teddy
 */
@Path("message")
public class MessageResources {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of MessageResources
     */
    public MessageResources() {
    }

    /**
     * Retrieves representation of an instance of se.social.server.rest.MessageResources
     * @param sender
     * @param recivier
     * @return an instance of java.lang.String
     */
    @GET @Path("/getconvo/{sender}/{recivier}")
    @Produces("application/json")
    public String getConvo(@PathParam("sender") String sender, @PathParam("recivier") String recivier) {
        //TODO return proper representation object
        //throw new UnsupportedOperationException();
        //MessageDao doa = new MessageDao();
        Gson gson = new Gson();
        
        ArrayList<UserMessage> messages = (ArrayList<UserMessage>) MessageDao.getConvo(sender, recivier); 
        
        for(UserMessage item: messages){
            System.out.println(item.getContent());
        }
       
        return gson.toJson(messages, ArrayList.class); 
    }

    /**
     * PUT method for updating or creating an instance of MessageResources
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    
    @PUT @Path("/sendMessage")
    @Consumes("application/json")
    @Produces("application/json")
    public String sendMessage(String content) {
        
        System.out.println("Recived: " + content);

        //MessageDao doa = new MessageDao();
        Gson gson = new Gson();

        UserMessage message = gson.fromJson(content, UserMessage.class);
        
        boolean success =  MessageDao.sendMessage(message);
        
        return "{success: "+success+"}";
    }
    
    @DELETE @Path("/removeMessage")
    @Consumes("application/json")
    @Produces("application/json")
    public String removeMessage(String content) {
        
        System.out.println("Recived: " + content);

        //MessageDao doa = new MessageDao();
        Gson gson = new Gson();

        UserMessage message = gson.fromJson(content, UserMessage.class);
        
        boolean success =  MessageDao.sendMessage(message);
        
        return "{success: "+success+"}";
    }
    
}
