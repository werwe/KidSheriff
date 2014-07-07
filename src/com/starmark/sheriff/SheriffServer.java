package com.starmark.sheriff;

//import static com.starmark.sheriff.OfyService.ofy;
//import static com.starmark.sheriff.OfyService.factory;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.googlecode.objectify.ObjectifyService;
import com.starmark.sheriff.Entity.UserSetting;
import com.starmark.sheriff.pojo.LinkRequestData;

@Path("/apis")
public class SheriffServer {

	@GET
	@Path("/hello/name={name}")
	@Produces(MediaType.TEXT_PLAIN)
	public String hello(@PathParam("name") final String name) {
		ObjectifyService.ofy().save()
				.entity(new UserSetting("werwe@starmark.co.kr", "testPushId"));
		return "Hello, " + name;
	}

	@POST
	@Path("/regist")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String regist(MultivaluedMap<String, String> formParams) {
		String email = formParams.getFirst("email");
		String pushid = formParams.getFirst("pushid");
		return email + "/" + pushid;
	}

	@POST
	@Path("/link")
	@Consumes(MediaType.APPLICATION_JSON)
	// @Produces(MediaType.APPLICATION_JSON)
	public LinkRequestData requestLink(LinkRequestData param) {
		String output = param.toString();

		//return Response.status(200).entity(output).build();
		return param;
		
	}
}
