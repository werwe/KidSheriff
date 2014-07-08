package com.starmark.sheriff.Entity;

import lombok.Data;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Data
@Entity
public class LinkInfo{
	@Id 
	Long id;
	
	@Index
	Key<UserInfo> key;
	@Index
	String emailReciever;

	private LinkInfo(){}
	public LinkInfo(Key<UserInfo> key,String reciever)
	{
		this.key = key;
		this.emailReciever = reciever;
	}
}
