/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.social.persist.data;

import java.util.Collection;
import java.util.Date;

/**
 *
 * @author Teddy
 */
public class UserPost {
    private int PostId;
    private String reciver;
    private String sender;
    private String content;
    private Date publishtime;
    private Collection<PostComment> comments;

    
    public UserPost(int PostId, String reciver,String sender,String content,Date now,Collection<PostComment> comments){
    
        this.PostId = PostId;
        this.reciver = reciver;
        this.sender = sender;
        this.content = content;
        this.publishtime = now;
        this.comments = comments;
    
    }
    
    
    /**
     * @return the author
     */
    public String getReciver() {
        return reciver;
    }

    /**
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @return the publishtime
     */
    public Date getPublishtime() {
        return publishtime;
    }

    /**
     * @return the comments
     */
    public Collection<PostComment> getComments() {
        return comments;
    }

    /**
     * @return the CommentId
     */
    public int getPostId() {
        return PostId;
    }
    
    
}
