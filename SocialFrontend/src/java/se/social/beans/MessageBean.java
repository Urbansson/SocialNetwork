/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.social.beans;

import com.google.gson.Gson;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import se.social.data.UserData;
import se.social.data.UserMessage;
import se.social.rest.MessageRestClient;
import se.social.rest.UserRestClient;

/**
 *
 * @author Teddy
 */
@ManagedBean
@SessionScoped
public class MessageBean {

    /**
     * Creates a new instance of MessageBean
     */
    
    private MessageRestClient rest;
    public MessageBean() {
        
                rest = new MessageRestClient();

    }
    
    public void sendMessage(){
    
        UserMessage message = new UserMessage(0, "Urbansson", "dolphan", new Date(), "Hejsan dett är ett messafe", false);
            Gson gson = new Gson();

        String jsonData = gson.toJson(message);
        System.out.println(jsonData);
        System.out.println("Message from server: " + rest.sendMessage(jsonData));
        
    }
    
    
}
