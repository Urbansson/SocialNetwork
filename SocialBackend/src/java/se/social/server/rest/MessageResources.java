/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.social.server.rest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;

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
     * @return an instance of java.lang.String
     */
    @GET @Path("/getmessagefromuser/{username}")
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        //throw new UnsupportedOperationException();
        
        
        return "Hello world";
        
    }

    /**
     * PUT method for updating or creating an instance of MessageResources
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
