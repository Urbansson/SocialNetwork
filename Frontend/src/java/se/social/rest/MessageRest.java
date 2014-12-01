/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.social.rest;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * Jersey REST client generated for REST resource:MessageRest
 * [MessageResources]<br>
 * USAGE:
 * <pre>
 *        MessageRest client = new MessageRest();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Teddy
 */
public class MessageRest {
    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/Backend_1/rest";
    //private static final String BASE_URI = "http://193.10.39.111:8080/Backend/rest";
    
    public MessageRest() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("MessageResources");
    }

    public String getConvo(String sender, String recivier) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("getconvo/{0}/{1}", new Object[]{sender, recivier}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
    }

    public String sendMessage(Object requestEntity) throws ClientErrorException {
        return webTarget.path("sendMessage").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), String.class);
    }

    public String removeMessage(Object requestEntity) throws ClientErrorException {
        return webTarget.path("removeMessage").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), String.class);
    }

    public void close() {
        client.close();
    }

    public String getConvo(String urbansson) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
