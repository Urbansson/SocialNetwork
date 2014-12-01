/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.social.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import se.social.persist.data.UserData;
import se.social.persist.data.UserPost;
import se.social.rest.UserRest;

/**
 *
 * @author Teddy
 */
@ManagedBean
@SessionScoped
public class userBean {

    private UserRest rest;

    private String userName;
    private String firstName;
    private String LastName;
    private String email;
    private String password;
    private Boolean loggedin;
    private List<UserData> friends;
    private List<UserData> friendRequests;

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

        rest = new UserRest();
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

            String respone = rest.getUserFriends(userName);
            if (respone.contains("{status: noFriends}")) {
                friends = new ArrayList<>();
            } else {
                this.friends = ((List<UserData>) gson.fromJson(respone, new TypeToken<List<UserData>>() {
                }.getType()));
            }

            respone = rest.getUserFriendRequests(userName);

            if (respone.contains("{status: noNewFriendRequest}")) {
                friendRequests = new ArrayList<>();

            } else {

                this.friendRequests = ((List<UserData>) gson.fromJson(respone, new TypeToken<List<UserData>>() {
                }.getType()));
            }
            
            context.addCallbackParam("success", true);
            loggedin = true;
            return true;
        }

        context.addCallbackParam("success", false);
        loggedin = false;
        return false;
    }

    public boolean isLoggedIn() {
        return loggedin;
    }

    public boolean logout() throws IOException {

        System.out.println("Logging out");
        this.loggedin = false;
        FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");

        this.userName = "";
        this.firstName = "";
        this.LastName = "";
        this.email = "";
        this.password = "";
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

    public boolean acceptFriendRequest(String frendee) {

        System.out.println(frendee);
        String response = rest.addFriend(this.userName, frendee);

        return response.contains("true");
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

    /**
     * @return the friends
     */
    public List<UserData> getFriends() {
        Gson gson = new Gson();

            String respone = rest.getUserFriends(userName);
            if (respone.contains("{status: noFriends}")) {
                friends = new ArrayList<>();
            } else {
                this.friends = ((List<UserData>) gson.fromJson(respone, new TypeToken<List<UserData>>() {
                }.getType()));
            }

        return friends;
    }

    /**
     * @param friends the friends to set
     */
    public void setFriends(List<UserData> friends) {
        this.friends = friends;
    }

    /**
     * @return the friendRequests
     */
    public List<UserData> getFriendRequests() {
        Gson gson = new Gson();

            String respone = rest.getUserFriendRequests(userName);

            if (respone.contains("{status: noNewFriendRequest}")) {
                friendRequests = new ArrayList<>();

            } else {

                this.friendRequests = ((List<UserData>) gson.fromJson(respone, new TypeToken<List<UserData>>() {
                }.getType()));
            }

        return new ArrayList<UserData>();
    }

    /**
     * @param friendRequests the friendRequests to set
     */
    public void setFriendRequests(List<UserData> friendRequests) {
        this.friendRequests = friendRequests;
    }

}
