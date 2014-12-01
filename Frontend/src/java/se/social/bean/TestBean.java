/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.social.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import se.social.rest.Hello;
import se.social.rest.MessageRest;

/**
 *
 * @author Teddy
 */
@ManagedBean
@SessionScoped
public class TestBean {

    
    private String message = "Answe = ";
    /**
     * Creates a new instance of TestBean
     */
    public TestBean() {
    }
    
    public void testRes(){
    
        //Hello rest = new Hello();
        MessageRest rest = new MessageRest();
        
        message = "Answe = "+ rest.getConvo("Urbansson", "dolphan");
        
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
