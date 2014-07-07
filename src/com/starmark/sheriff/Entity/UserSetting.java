package com.starmark.sheriff.Entity;

import lombok.Data;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Data
@Entity
public class UserSetting {
	@Id Long id;
	
	@Index
	private String email;
	private String pushId;
	
	
	private UserSetting(){}
	
	public UserSetting(String email, String pushId)
	{
		this.email = email;
		this.pushId = pushId;
	}

}
