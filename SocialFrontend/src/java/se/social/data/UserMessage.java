/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.social.data;

import java.util.Date;

public class UserMessage{
	private int message_id;
        private String sender, reciever;
	private String content;
	private Date sendtime;
	private boolean readmessage;
	
	public UserMessage(int message_id, String sender, String reciever, Date sendtime, String content, boolean readmessage){
		this.message_id = message_id;
		this.sender = sender;
		this.reciever = reciever;
		this.sendtime = sendtime;
		this.readmessage = readmessage;
                this.content = content;
                
	}
	
	public int getMessageID(){
		return message_id;
	}
	
	public String getSender(){
		return sender;
	}

	public String getReciever(){
		return reciever;
	}
	
	public Date getSendTime(){
		return sendtime;
	}
	
	public String getContent(){
		return content;
	}
	
	public boolean getReadMessage(){
		return readmessage;
	}
}
