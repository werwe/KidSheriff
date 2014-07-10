package com.starmark.sheriff;

//import static com.starmark.sheriff.OfyService.ofy;
//import static com.starmark.sheriff.OfyService.factory;

import java.util.List;
import java.util.logging.Level;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import lombok.extern.java.Log;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.Result;
import com.googlecode.objectify.cmd.LoadType;
import com.starmark.sheriff.entity.LinkInfo;
import com.starmark.sheriff.entity.LocationHistory;
import com.starmark.sheriff.entity.UserInfo;
import com.starmark.sheriff.pojo.LinkRequestData;
import com.starmark.sheriff.pojo.Location;
import com.starmark.sheriff.pojo.LocationInfo;
import com.starmark.sheriff.pojo.LocationList;
import com.starmark.sheriff.pojo.HistoryRequest;

@Log
@Path("/apis")
public class SheriffServer {

	static
	{
		log.setLevel(Level.WARNING);
	}
	
	//test method
	@GET
	@Path("/hello/name={name}")
	@Produces(MediaType.TEXT_PLAIN)
	public String hello(@PathParam("name") final String name) {
		Result<Key<UserInfo>> result = OfyService.ofy().save()
				.entity(new UserInfo("werwe222@starmark.co.kr", "testPushId",UserInfo.CHILD));
		if(result == null) log.fine("not file");
		else log.fine("fine ^^");
		//else if(result == null) log.log(Level.WARNING, "result is :"+result.now().getId());
		return "Hello, " + name;
	}

	//test method
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
	public Response requestRegist(LinkRequestData param) {
			String userEmail = param.getEmail();
			String pushId = param.getPushid();
			List<String> parent = param.getLinkedAccounts();
			int whichSide = param.getWhichSide();
			Objectify ofy = OfyService.ofy();
			UserInfo info = new UserInfo(userEmail,pushId,whichSide);
			Key<UserInfo> userKey = Key.create(info);
			ofy.save().entities(info);
			
			LoadType<LinkInfo> linkList = ofy.load().type(LinkInfo.class);
			int pLength = parent.size();
			
			StringBuilder builder = new StringBuilder();
			for(int i = 0 ; i < pLength ; i++)
			{
				List<LinkInfo> list = 
						linkList.filter("linkedAccount", parent.get(i))
						.filter("key", userKey)
						.list();

				if (list == null || list.size() == 0)
				{
					ofy.save().entities(new LinkInfo(userKey,parent.get(i)));
				}
//				else
//				{
//					LinkInfo query =list.get(0);
//					ofy.save().entities(query);
//				}
//				{
//				    ofy.transact(new VoidWork() { 
//				      @Override public void vrun() {
//				        ofy.save.entity(newPerson).now();
//				      }
//				}
			}
			
		return Response.status(200).entity("success").build();
	}
	
	@POST
	@Path("/updateLoc")
	@Consumes(MediaType.APPLICATION_JSON)
	// @Produces(MediaType.APPLICATION_JSON)
	public Response updateLocation(LocationInfo param) {
			StringBuilder builder = new StringBuilder();
			String userId = param.getUserId();
			Location loc = param.getLoc();
			Objectify ofy = OfyService.ofy();
			Key<UserInfo> userKey = Key.create(UserInfo.class, userId);
			
			builder.append(userKey.toString()).append(" / ");
			List<UserInfo> userList = ofy.load().type(UserInfo.class).filterKey(userKey).list();
			
			if(userList != null && userList.size() > 0)
			{
				Ref<UserInfo> refUser = Ref.create(userList.get(0));
			
				LocationHistory history = new LocationHistory(refUser,loc);
				ofy.save().entities(history);
				
				builder.append(loc.toString());
			}
			else
			{
				//유저 정보가 없다.
				return Response.status(200).entity("no user infomation : " + builder.toString()).build();
			}	
		return Response.status(200).entity("success : " + builder.toString()).build();
	}
	
	@POST
	@Path("/getLoc")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public LocationList getLocation(HistoryRequest request) {
			StringBuilder builder = new StringBuilder();
			LocationList locations = new LocationList();
			
			String requestorUserId = request.getRequestorId();
			String targetUserId = request.getTargetUserId();
			
			builder.append("request user:"+requestorUserId+"\n");
			builder.append("target user:"+targetUserId+"\n");
			
			Objectify ofy = OfyService.ofy();
			
			Key<UserInfo> targetKey = Key.create(UserInfo.class,targetUserId);
			List<LinkInfo> list = 
				ofy.load().type(LinkInfo.class).filter("linkedAccount", requestorUserId)
				.filter("key", targetKey)
				.list();

			if (list == null || list.size() == 0)
			{
				builder.append("연결된 사용자가 아닙니다.");
				locations.setResult(builder.toString());
				return locations;
			}
			builder.append("연결된 사용자 입니다.\n");

			UserInfo user = new UserInfo();
			user.setEmail(targetUserId);
			List<LocationHistory> locList = ofy.load()
					.type(LocationHistory.class)
					.filter("userRef",user)
					.limit(request.getLimit())
					.list();
			if(locList != null && locList.size() > 0)
			{
				int size = locList.size();
				for(int i = 0 ; i < size ; i++)
				{
					locations.addLocation(locList.get(i).getLoc());
				}
			}
			locations.setResult(builder.toString());
		return locations;
	}
}
