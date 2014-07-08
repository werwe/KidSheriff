package com.starmark.sheriff;

//import static com.starmark.sheriff.OfyService.ofy;
//import static com.starmark.sheriff.OfyService.factory;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import lombok.extern.java.Log;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Result;
import com.googlecode.objectify.cmd.LoadType;
import com.googlecode.objectify.cmd.Query;
import com.starmark.sheriff.Entity.LinkInfo;
import com.starmark.sheriff.Entity.UserInfo;
import com.starmark.sheriff.pojo.LinkRequestData;

@Log
@Path("/apis")
public class SheriffServer {

	//private static final Logger log = Logger.getLogger(SheriffServer.class.getName());
	static
	{
		log.setLevel(Level.WARNING);
	}
	@GET
	@Path("/hello/name={name}")
	@Produces(MediaType.TEXT_PLAIN)
	public String hello(@PathParam("name") final String name) {
		Result<Key<UserInfo>> result = OfyService.ofy().save()
				.entity(new UserInfo("werwe222@starmark.co.kr", "testPushId"));
		if(result == null) log.fine("not file");
		else log.fine("fine ^^");
		//else if(result == null) log.log(Level.WARNING, "result is :"+result.now().getId());
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
	public Response requestLink(LinkRequestData param) {
			String userEmail = param.getEmail();
			String pushId = param.getPushid();
			List<String> parent = param.getParents();
			
			Objectify ofy = OfyService.ofy();
			UserInfo info = new UserInfo(userEmail,pushId);
			Key<UserInfo> userKey = Key.create(info);
			ofy.save().entities(info);
			
			LoadType<LinkInfo> linkList = ofy.load().type(LinkInfo.class);
			int pLength = parent.size();
			
			StringBuilder builder = new StringBuilder();
			for(int i = 0 ; i < pLength ; i++)
			{
				List<LinkInfo> list = 
						linkList.filter("emailReciever", parent.get(i))
						.filter("key", userKey)
						.list();

				builder.append(list+"\n");
				builder.append("listCount:"+list.size() + "\n");
				if (list == null || list.size() == 0)
				{
					ofy.save().entities(new LinkInfo(userKey,parent.get(i),false));
				}
				else
				{
					LinkInfo query =list.get(0);
					query.setLinked(true);
					ofy.save().entities(query);
				}
//				{
//				    ofy.transact(new VoidWork() { 
//				      @Override public void vrun() {
//				        ofy.save.entity(newPerson).now();
//				      }
//				}
			}
			
		return Response.status(200).entity(builder.toString()).build();
	}
}
