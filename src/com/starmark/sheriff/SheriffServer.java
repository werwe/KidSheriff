package com.starmark.sheriff;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/apis")
public class SheriffServer {

	@GET
	@Path("/hello/name={name}")
	@Produces(MediaType.TEXT_PLAIN)
	public String hello(@PathParam("name") final String name)
	{
		return "Hello, "+name;
	}
}
