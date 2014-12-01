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
 * Jersey REST client generated for REST resource:Hello [/hello]<br>
 * USAGE:
 * <pre>
 *        Hello client = new Hello();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Teddy
 */
public class Hello {
    private WebTarget webTarget;
    private Client client;
    //private static final String BASE_URI = "http://localhost:8080/Backend/rest";
    private static final String BASE_URI = "http://193.10.39.111:8080/Backend/rest";
    
    public Hello() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("hello");
    }

    public String sayXMLHello() throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.TEXT_XML).get(String.class);
    }

    public String sayHtmlHello() throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.TEXT_HTML).get(String.class);
    }

    public String sayPlainTextHello() throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    public void close() {
        client.close();
    }
    
}
