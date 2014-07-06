package com.starmark.sheriff.Entity;

import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Entity;

@Entity
public class LinkRequest{
	@Id 
	Long id;
	
	String emailSender;
	String emailReciever;
	boolean linked = false;
	private LinkRequest(){}
	public LinkRequest(String sender,String reciever,boolean linked)
	{
		this.emailReciever = reciever;
		this.emailSender = sender;
		this.linked = linked;
	}
}
