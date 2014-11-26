/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.social.beans;

import com.google.gson.Gson;
import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import se.social.data.UserData;
import se.social.rest.UserRestClient;

/**
 *
 * @author Teddy
 */
@ManagedBean
@SessionScoped
public class userBean {

    private UserRestClient rest;

    private String userName;
    private String firstName;
    private String LastName;
    private String email;
    private String password;
    private Boolean loggedin;

    /**
     * Creates a new instance of userBean
     */
    public userBean() {
        System.out.println("Made a user bean");
        this.userName = "";
        this.firstName = "";
        this.LastName = "";
        this.email = "";
        this.password = "";
        this.loggedin = false;

        rest = new UserRestClient();
    }

    public boolean login() {

        String response = rest.login(userName, password);

        RequestContext context = RequestContext.getCurrentInstance();
        Gson gson = new Gson();

        UserData data = gson.fromJson(response, UserData.class);

        if (data.getUserName() != null) {

            this.userName = data.getUserName();
            this.firstName = data.getFirstName();
            this.LastName = data.getLastName();
            this.email = data.getEmail();
            this.password = data.getPassword();

            context.addCallbackParam("success", true);
            loggedin = true;
            return true;
        }
        context.addCallbackParam("success", false);
        loggedin = false;
        return false;
    }

    public boolean logout() throws IOException {

        System.out.println("Logging out");
        this.loggedin = false;
        FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        return true;
    }

    public boolean register() {

        Gson gson = new Gson();
        UserData ud = new UserData(getUserName(), getFirstName(), getLastName(), getEmail(), getPassword());
        String json = gson.toJson(ud);

        System.out.println(json);
        String response = rest.register(json);

        UserData data = gson.fromJson(response, UserData.class);
        if (data.getUserName() != null) {

            this.userName = data.getUserName();
            this.firstName = data.getFirstName();
            this.LastName = data.getLastName();
            this.email = data.getEmail();
            this.password = data.getPassword();

            loggedin = true;
            return true;

        }

        return false;

        //return false;
    }

    public void checkUsername() {

        System.out.println("Checking uname");
        RequestContext context = RequestContext.getCurrentInstance();

        if (userName.equals("Teddy")) {
            context.addCallbackParam("free", false);
        } else {
            context.addCallbackParam("free", true);
        }
    }

    public void checkEmail() {

        System.out.println("Checking email");
        RequestContext context = RequestContext.getCurrentInstance();

        if (email.equals("teddy@brandts.se")) {
            context.addCallbackParam("free", false);
        } else {
            context.addCallbackParam("free", true);
        }
    }

    public boolean redirectIfLoggedin() {
        return this.getLoggedin();
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the LastName
     */
    public String getLastName() {
        return LastName;
    }

    /**
     * @param LastName the LastName to set
     */
    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the logedin
     */
    public Boolean getLoggedin() {
        return loggedin;
    }

}
