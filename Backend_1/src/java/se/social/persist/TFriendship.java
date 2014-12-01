/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.social.persist;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author Teddy
 */
@Entity
@Table(name = "T_FRIENDSHIP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TFriendship.findAll", query = "SELECT t FROM TFriendship t"),
    @NamedQuery(name = "TFriendship.findByFriender", query = "SELECT t FROM TFriendship t WHERE t.tFriendshipPK.friender = :friender"),
    @NamedQuery(name = "TFriendship.findByFriendee", query = "SELECT t FROM TFriendship t WHERE t.tFriendshipPK.friendee = :friendee"),
    @NamedQuery(name = "TFriendship.findByAccepted", query = "SELECT t FROM TFriendship t WHERE t.accepted = :accepted")})
public class TFriendship implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TFriendshipPK tFriendshipPK;
    @Column(name = "accepted")
    private Boolean accepted;
    @JoinColumn(name = "FRIENDER", referencedColumnName = "USER_ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TUser tUser;
    @JoinColumn(name = "FRIENDEE", referencedColumnName = "USER_ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TUser tUser1;

    public TFriendship() {
    }

    public TFriendship(TFriendshipPK tFriendshipPK) {
        this.tFriendshipPK = tFriendshipPK;
    }

    public TFriendship(int friender, int friendee) {
        this.tFriendshipPK = new TFriendshipPK(friender, friendee);
    }

    public TFriendshipPK getTFriendshipPK() {
        return tFriendshipPK;
    }

    public void setTFriendshipPK(TFriendshipPK tFriendshipPK) {
        this.tFriendshipPK = tFriendshipPK;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public TUser getTUser() {
        return tUser;
    }

    public void setTUser(TUser tUser) {
        this.tUser = tUser;
    }

    public TUser getTUser1() {
        return tUser1;
    }

    public void setTUser1(TUser tUser1) {
        this.tUser1 = tUser1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tFriendshipPK != null ? tFriendshipPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TFriendship)) {
            return false;
        }
        TFriendship other = (TFriendship) object;
        if ((this.tFriendshipPK == null && other.tFriendshipPK != null) || (this.tFriendshipPK != null && !this.tFriendshipPK.equals(other.tFriendshipPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.social.persist.TFriendship[ tFriendshipPK=" + tFriendshipPK + " ]";
    }
    
}
