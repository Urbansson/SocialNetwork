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
@Table(name = "T_MESSAGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TMessage.findAll", query = "SELECT t FROM TMessage t"),
    @NamedQuery(name = "TMessage.findByMessageId", query = "SELECT t FROM TMessage t WHERE t.messageId = :messageId"),
    @NamedQuery(name = "TMessage.findByContent", query = "SELECT t FROM TMessage t WHERE t.content = :content"),
    @NamedQuery(name = "TMessage.findByReadmessage", query = "SELECT t FROM TMessage t WHERE t.readmessage = :readmessage"),
    @NamedQuery(name = "TMessage.getConvo", query = "Select t from TMessage t WHERE (t.sender = :sender and t.reciever = :reciever) or (t.sender = :reciever and t.reciever = :sender) ORDER BY t.sendtime")})
public class TMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "MESSAGE_ID")
    private Integer messageId;
    @Column(name = "CONTENT")
    private String content;
    @Basic(optional = false)
    @Column(name = "SENDTIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendtime;
    @Column(name = "READMESSAGE")
    private Boolean readmessage;
    @JoinColumn(name = "SENDER", referencedColumnName = "USER_ID")
    @ManyToOne
    private TUser sender;
    @JoinColumn(name = "RECIEVER", referencedColumnName = "USER_ID")
    @ManyToOne
    private TUser reciever;

    public TMessage() {
    }

    public TMessage(Integer messageId) {
        this.messageId = messageId;
    }

    public TMessage(Integer messageId, Date sendtime) {
        this.messageId = messageId;
        this.sendtime = sendtime;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendtime() {
        return sendtime;
    }

    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    public Boolean getReadmessage() {
        return readmessage;
    }

    public void setReadmessage(Boolean readmessage) {
        this.readmessage = readmessage;
    }

    public TUser getSender() {
        return sender;
    }

    public void setSender(TUser sender) {
        this.sender = sender;
    }

    public TUser getReciever() {
        return reciever;
    }

    public void setReciever(TUser reciever) {
        this.reciever = reciever;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (messageId != null ? messageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TMessage)) {
            return false;
        }
        TMessage other = (TMessage) object;
        if ((this.messageId == null && other.messageId != null) || (this.messageId != null && !this.messageId.equals(other.messageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.social.persist.TMessage[ messageId=" + messageId + " ]";
    }
    
}
