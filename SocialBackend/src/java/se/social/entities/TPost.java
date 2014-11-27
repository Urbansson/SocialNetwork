/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.social.entities;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Teddy
 */
@Entity
@Table(name = "T_POST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TPost.findAll", query = "SELECT t FROM TPost t"),
    @NamedQuery(name = "TPost.findByPostId", query = "SELECT t FROM TPost t WHERE t.postId = :postId"),
    @NamedQuery(name = "TPost.findByPublishtime", query = "SELECT t FROM TPost t WHERE t.publishtime = :publishtime"),
    @NamedQuery(name = "TPost.findByContent", query = "SELECT t FROM TPost t WHERE t.content = :content"),
    @NamedQuery(name = "TPost.findByrecievingwall", query = "SELECT t FROM TPost t WHERE t.recievingwall = :user")})
public class TPost implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "POST_ID")
    private Integer postId;
    @Column(name = "PUBLISHTIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishtime;
    @Size(max = 1024)
    @Column(name = "CONTENT")
    private String content;
    @OneToMany(mappedBy = "atpost")
    private Collection<TComments> tCommentsCollection;
    @JoinColumn(name = "AUTHOR", referencedColumnName = "USER_ID")
    @ManyToOne
    private TUser author;
    @JoinColumn(name = "RECIEVINGWALL", referencedColumnName = "USER_ID")
    @ManyToOne
    private TUser recievingwall;

    public TPost() {
    }

    public TPost(Integer postId) {
        this.postId = postId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
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

    @XmlTransient
    public Collection<TComments> getTCommentsCollection() {
        return tCommentsCollection;
    }

    public void setTCommentsCollection(Collection<TComments> tCommentsCollection) {
        this.tCommentsCollection = tCommentsCollection;
    }

    public TUser getAuthor() {
        return author;
    }

    public void setAuthor(TUser author) {
        this.author = author;
    }

    public TUser getRecievingwall() {
        return recievingwall;
    }

    public void setRecievingwall(TUser recievingwall) {
        this.recievingwall = recievingwall;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (postId != null ? postId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TPost)) {
            return false;
        }
        TPost other = (TPost) object;
        if ((this.postId == null && other.postId != null) || (this.postId != null && !this.postId.equals(other.postId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.social.entities.TPost[ postId=" + postId + " ]";
    }
    
}
