/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.social.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import se.social.persist.data.UserMessage;
import se.social.persist.data.UserPost;
import se.social.rest.MessageRest;

/**
 *
 * @author Teddy
 */
@ManagedBean
@ViewScoped
public class MessageBean {

    /**
     * Creates a new instance of MessageBean
     */
    private List<UserMessage> messages;

    @ManagedProperty(value = "#{userBean}")
    private userBean user;

    private String partner = " ";
    
    private String message = "";

    public MessageBean() {

    }

    public List<UserMessage> getMessages() {
        messages = this.fetchConvo(this.partner);

        return messages;
    }

    public void sendMessage() {
        MessageRest rest = new MessageRest();

        System.out.println("Sending a message to: " + this.partner + ": !!");
        
        
         UserMessage message = new UserMessage(0, user.getUserName(), this.partner, new Date(), this.message, false);
         Gson gson = new Gson();

         String jsonData = gson.toJson(message);
         System.out.println(jsonData);
         System.out.println("Message from server: " + rest.sendMessage(jsonData));
         
    }

    public List<UserMessage> fetchConvo(String partner) {

        MessageRest rest = new MessageRest();
        Gson gson = new Gson();
        List<UserMessage> posts = new ArrayList<>();

        System.out.println("Getting all messages with: " + partner);

        try {
            posts = gson.fromJson(rest.getConvo(user.getUserName(), partner), new TypeToken<List<UserMessage>>() {
            }.getType());

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        //List<UserMessage> posts = gson.fromJson(rest.getConvo(user.getUserName(), partner), new TypeToken<List<UserMessage>>(){}.getType());
        return posts;
    }

    public List<UserMessage> getFriends() {
        return null;
    }

    public void fetchMessages() {
        System.out.println("Fetch convo!");

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
     * @return the partner
     */
    public String getPartner() {
        return partner;
    }

    public void changePartner(String partner) {

        System.err.println("Setting partner to: " + partner);
        this.message = "";
        this.partner = partner;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
