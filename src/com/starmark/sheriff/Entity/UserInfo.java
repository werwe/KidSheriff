package com.starmark.sheriff.Entity;

import javax.jdo.annotations.Unique;

import lombok.Data;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Data
@Entity
@Index
public class UserInfo {
	
	@Id
	private String email;
	private String pushId;
	
	
	private UserInfo(){}
	
	public UserInfo(String email, String pushId)
	{
		this.email = email;
		this.pushId = pushId;
	}

}
