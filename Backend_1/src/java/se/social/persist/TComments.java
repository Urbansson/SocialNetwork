/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.social.persist;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author Teddy
 */
@Entity
@Table(name = "T_COMMENTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TComments.findAll", query = "SELECT t FROM TComments t"),
    @NamedQuery(name = "TComments.findByCommentId", query = "SELECT t FROM TComments t WHERE t.commentId = :commentId"),
    @NamedQuery(name = "TComments.findByPublishtime", query = "SELECT t FROM TComments t WHERE t.publishtime = :publishtime"),
    @NamedQuery(name = "TComments.findByContent", query = "SELECT t FROM TComments t WHERE t.content = :content")})
public class TComments implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "COMMENT_ID")
    private Integer commentId;
    @Column(name = "PUBLISHTIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishtime;
    @Column(name = "CONTENT")
    private String content;
    @JoinColumn(name = "ATPOST", referencedColumnName = "POST_ID")
    @ManyToOne
    private TPost atpost;
    @JoinColumn(name = "AUTHOR", referencedColumnName = "USER_ID")
    @ManyToOne
    private TUser author;

    public TComments() {
    }

    public TComments(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Date getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(Date publishtime) {
        this.publishtime = publishtime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TPost getAtpost() {
        return atpost;
    }

    public void setAtpost(TPost atpost) {
        this.atpost = atpost;
    }

    public TUser getAuthor() {
        return author;
    }

    public void setAuthor(TUser author) {
        this.author = author;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (commentId != null ? commentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TComments)) {
            return false;
        }
        TComments other = (TComments) object;
        if ((this.commentId == null && other.commentId != null) || (this.commentId != null && !this.commentId.equals(other.commentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.social.persist.TComments[ commentId=" + commentId + " ]";
    }
    
}
