package com.starmark.sheriff.test;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.starmark.sheriff.entity.UserInfo;
import com.starmark.sheriff.pojo.LinkRequestData;
import com.starmark.sheriff.pojo.Location;
import com.starmark.sheriff.pojo.LocationInfo;
import com.starmark.sheriff.pojo.LocationList;
import com.starmark.sheriff.pojo.HistoryRequest;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class Test {
	public static void main(String[] args) {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		
		//local
		//URI uri = getLocalBaseURI();
		
		//remote
		URI uri = getBaseURI();
		
		WebResource service = client.resource(uri);

		//		System.out.println(service.path("apis").path("hello/name=kk7777").accept(
		//				MediaType.TEXT_PLAIN).get(String.class));
		
		//postRequest(client);
		linkRequest();
		updateLocRequest();
		getUserLocation();
	}

	private static URI getLocalBaseURI() {
		return UriBuilder.fromUri(
				"http://localhost:8881/").build();
	}
	
	private static URI getBaseURI() {
		return UriBuilder.fromUri(
				"http://kid-sheriff-001.appspot.com/").build();
	}
	
	public static void postRequest(Client client)
	{
		WebResource service = client.resource(getBaseURI());
		MultivaluedMap<String,String> queryParams = new MultivaluedMapImpl(); 
		queryParams.add("email", "werwe.test@starmark.com");
		queryParams.add("pushid", "this_is_push_id 22");

		String result = service.path("apis/regist")
		.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
		.post(String.class,queryParams);
		
		System.out.println(result);
	}
	
	public static void linkRequest()
	{
		try {
			List<String> list = new ArrayList<String>();
			list.add("werwe@starmark.co.kr");
			list.add("werwe.test@gmail.com");
			LinkRequestData data = 
				new LinkRequestData("werwe.me@gmail.com","this is push id",list, UserInfo.CHILD);
			ClientConfig clientConfig = new DefaultClientConfig();
			clientConfig.getFeatures().put(
					JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
			Client client = Client.create(clientConfig);

//			WebResource webResource = client
//					.resource("http://localhost:8881/apis/link");
			
			WebResource webResource = client
					.resource("http://kid-sheriff-001.appspot.com/apis/link");
			
			

			ClientResponse response = webResource.accept("application/json")
					.type("application/json").post(ClientResponse.class, data);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			String output = response.getEntity(String.class);

			System.out.println("Server response .... \n");
			System.out.println(output);
			
//			LinkRequestData response = webResource.accept("application/json")
//					.type("application/json").post(LinkRequestData.class, data);
//
//
//			System.out.println("Server response .... \n");
//			System.out.println(response.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public static void updateLocRequest()
	{
		try {
			LocationInfo info = new LocationInfo();
			Location loc = new Location();
			DateTime time = new DateTime();
			DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
			String currentTime = time.toString(fmt);
			loc.setDate(currentTime);
			//37.504537, 127.049027 //선릉역
			loc.setLat(37.504537);
			loc.setLng(127.049027);
			
			System.out.println("currentTime:"+currentTime);
			info.setLoc(loc);
			info.setUserId("werwe.me@gmail.com");
			
			ClientConfig clientConfig = new DefaultClientConfig();
			clientConfig.getFeatures().put(
					JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
			Client client = Client.create(clientConfig);
			
			WebResource webResource = client
					.resource("http://kid-sheriff-001.appspot.com/apis/updateLoc");

			ClientResponse response = webResource.accept("application/json")
					.type("application/json").post(ClientResponse.class, info);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}
			String output = response.getEntity(String.class);
			System.out.println("Server response .... \n");
			System.out.println(output);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void getUserLocation()
	{
		try {
			HistoryRequest reqData = new HistoryRequest();
			reqData.setRequestorId("werwe.test@gmail.com");
			reqData.setTargetUserId("werwe.me@gmail.com");
			reqData.setLimit(3);
			
			ClientConfig clientConfig = new DefaultClientConfig();
			clientConfig.getFeatures().put(
					JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
			Client client = Client.create(clientConfig);
			
			WebResource webResource = client
					.resource("http://kid-sheriff-001.appspot.com/apis/getLoc");

			LocationList locations = webResource.accept("application/json")
					.type("application/json").post(LocationList.class, reqData);

			if(locations != null)
			{
				System.out.println("Server response .... \n");
				System.out.println("result:\n"+locations.getResult());
				for(Location l : locations.getList())
					System.out.println("loc:"+l.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

