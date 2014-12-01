/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.social.persist.data;

import java.util.Date;

/**
 *
 * @author Teddy
 */
public class PostComment {
    
    private String sender;
    private String content;
    private Date publishtime; 
    private int commentId;
    
    public PostComment(int commentId, String sender, String content, Date publishtime){
        this.content = content;
        this.sender = sender;
        this.publishtime = publishtime;
        this.commentId = commentId;
    }

    /**
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * @return the comment
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
     * @return the commentId
     */
    public int getCommentId() {
        return commentId;
    }
    
}
