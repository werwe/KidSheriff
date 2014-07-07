package com.starmark.sheriff.Entity;

import lombok.Data;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Data
@Entity
public class LinkInfo{
	@Id 
	Long id;
	
	Key<UserInfo> key;
	String emailReciever;
	boolean linked = false;
	private LinkInfo(){}
	public LinkInfo(Key<UserInfo> key,String reciever,boolean linked)
	{
		this.key = key;
		this.emailReciever = reciever;
		this.linked = linked;
	}
}
