package com.starmark.sheriff.model;

import org.joda.time.DateTime;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.stringifier.Stringifier;

@Entity
public class Location {
	@Id Long id;
	String email;
	StringTime time;
	long lat;
	long lng;
	
	private Location(){}
	
	public Location(String email,StringTime time,long lat, long lng)
	{
		this.email = email;
		this.time = time;
		this.lat = lat;
		this.lng = lng;
	}
}

class StringTime implements Stringifier<DateTime>
{
	@Override
	public DateTime fromString(String arg0) {
		return new DateTime(arg0);
	}

	@Override
	public String toString(DateTime arg0) {
		return arg0.toString();
	}
}