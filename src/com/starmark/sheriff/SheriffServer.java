package com.starmark.sheriff;

//import static com.starmark.sheriff.OfyService.ofy;
//import static com.starmark.sheriff.OfyService.factory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.googlecode.objectify.ObjectifyService;
import com.starmark.sheriff.Entity.UserSetting;



@Path("/apis")
public class SheriffServer {

    static {
        ObjectifyService.register(UserSetting.class);
        System.out.print("registered");
    }
	
	@GET
	@Path("/hello/name={name}")
	@Produces(MediaType.TEXT_PLAIN)
	public String hello(@PathParam("name") final String name)
	{
		ObjectifyService.ofy().save().entity(new UserSetting("werwe@starmark.co.kr","testPushId"));
		return "Hello, "+name;
	}
}
