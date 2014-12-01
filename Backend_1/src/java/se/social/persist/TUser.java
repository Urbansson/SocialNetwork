/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.social.persist;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 *
 * @author Teddy
 */
@Entity
@Table(name = "T_USER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TUser.findAll", query = "SELECT t FROM TUser t"),
    @NamedQuery(name = "TUser.findByUserId", query = "SELECT t FROM TUser t WHERE t.userId = :userId"),
    @NamedQuery(name = "TUser.findByUsername", query = "SELECT t FROM TUser t WHERE t.username = :username"),
    @NamedQuery(name = "TUser.findByFirstname", query = "SELECT t FROM TUser t WHERE t.firstname = :firstname"),
    @NamedQuery(name = "TUser.findByLastname", query = "SELECT t FROM TUser t WHERE t.lastname = :lastname"),
    @NamedQuery(name = "TUser.findByEmail", query = "SELECT t FROM TUser t WHERE t.email = :email"),
    @NamedQuery(name = "TUser.findByPasswrd", query = "SELECT t FROM TUser t WHERE t.passwrd = :passwrd"),
    @NamedQuery(name = "TUser.findUserLike", query = "SELECT t FROM TUser t WHERE t.username like :username")})

public class TUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "USER_ID")
    private Integer userId;
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "FIRSTNAME")
    private String firstname;
    @Column(name = "LASTNAME")
    private String lastname;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PASSWRD")
    private String passwrd;
    @OneToMany(mappedBy = "author")
    private Collection<TComments> tCommentsCollection;
    @OneToMany(mappedBy = "sender")
    private Collection<TMessage> tMessageCollection;
    @OneToMany(mappedBy = "reciever")
    private Collection<TMessage> tMessageCollection1;
    @OneToMany(mappedBy = "author")
    private Collection<TPost> tPostCollection;
    @OneToMany(mappedBy = "recievingwall")
    private Collection<TPost> tPostCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tUser")
    private Collection<TFriendship> tFriendshipCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tUser1")
    private Collection<TFriendship> tFriendshipCollection1;

    public TUser() {
    }

    public TUser(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswrd() {
        return passwrd;
    }

    public void setPasswrd(String passwrd) {
        this.passwrd = passwrd;
    }

    @XmlTransient
    public Collection<TComments> getTCommentsCollection() {
        return tCommentsCollection;
    }

    public void setTCommentsCollection(Collection<TComments> tCommentsCollection) {
        this.tCommentsCollection = tCommentsCollection;
    }

    @XmlTransient
    public Collection<TMessage> getTMessageCollection() {
        return tMessageCollection;
    }

    public void setTMessageCollection(Collection<TMessage> tMessageCollection) {
        this.tMessageCollection = tMessageCollection;
    }

    @XmlTransient
    public Collection<TMessage> getTMessageCollection1() {
        return tMessageCollection1;
    }

    public void setTMessageCollection1(Collection<TMessage> tMessageCollection1) {
        this.tMessageCollection1 = tMessageCollection1;
    }

    @XmlTransient
    public Collection<TPost> getTPostCollection() {
        return tPostCollection;
    }

    public void setTPostCollection(Collection<TPost> tPostCollection) {
        this.tPostCollection = tPostCollection;
    }

    @XmlTransient
    public Collection<TPost> getTPostCollection1() {
        return tPostCollection1;
    }

    public void setTPostCollection1(Collection<TPost> tPostCollection1) {
        this.tPostCollection1 = tPostCollection1;
    }

    @XmlTransient
    public Collection<TFriendship> getTFriendshipCollection() {
        return tFriendshipCollection;
    }

    public void setTFriendshipCollection(Collection<TFriendship> tFriendshipCollection) {
        this.tFriendshipCollection = tFriendshipCollection;
    }

    @XmlTransient
    public Collection<TFriendship> getTFriendshipCollection1() {
        return tFriendshipCollection1;
    }

    public void setTFriendshipCollection1(Collection<TFriendship> tFriendshipCollection1) {
        this.tFriendshipCollection1 = tFriendshipCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TUser)) {
            return false;
        }
        TUser other = (TUser) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.social.persist.TUser[ userId=" + userId + " ]";
    }
    
}
