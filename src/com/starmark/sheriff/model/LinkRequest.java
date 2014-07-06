package com.starmark.sheriff.model;

import com.googlecode.objectify.annotation.Id;

import com.googlecode.objectify.annotation.Entity;

@Entity
public class LinkRequest{
	@Id 
	Long id;
	
	String emailSender;
	String emailReciever;
	boolean linked = false;
}
