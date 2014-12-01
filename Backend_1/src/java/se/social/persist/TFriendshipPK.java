/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.social.persist;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
/**
 *
 * @author Teddy
 */
@Embeddable
public class TFriendshipPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "FRIENDER")
    private int friender;
    @Basic(optional = false)
    @Column(name = "FRIENDEE")
    private int friendee;

    public TFriendshipPK() {
    }

    public TFriendshipPK(int friender, int friendee) {
        this.friender = friender;
        this.friendee = friendee;
    }

    public int getFriender() {
        return friender;
    }

    public void setFriender(int friender) {
        this.friender = friender;
    }

    public int getFriendee() {
        return friendee;
    }

    public void setFriendee(int friendee) {
        this.friendee = friendee;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) friender;
        hash += (int) friendee;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TFriendshipPK)) {
            return false;
        }
        TFriendshipPK other = (TFriendshipPK) object;
        if (this.friender != other.friender) {
            return false;
        }
        if (this.friendee != other.friendee) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.social.persist.TFriendshipPK[ friender=" + friender + ", friendee=" + friendee + " ]";
    }
    
}
