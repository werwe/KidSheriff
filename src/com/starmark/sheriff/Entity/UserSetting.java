package com.starmark.sheriff.Entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class UserSetting {
	@Id Long id;
	String email;
	String pushId;
	
	
	private UserSetting(){}
	
	public UserSetting(String email, String pushId)
	{
		this.email = email;
		this.pushId = pushId;
	}
	

}
