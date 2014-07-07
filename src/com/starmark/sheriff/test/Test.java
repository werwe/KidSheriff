package com.starmark.sheriff.test;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;

import com.starmark.sheriff.pojo.LinkRequestData;
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
		URI uri = getLocalBaseURI();
		
		//remote
		//URI uri = getBaseURI();
		
		WebResource service = client.resource(uri);

//		System.out.println(service.path("apis").path("hello/name=kkkk").accept(
//				MediaType.TEXT_PLAIN).get(String.class));
		
		//postRequest(client);
		linkRequest();
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
		queryParams.add("email", "werwe@starmark.com");
		queryParams.add("pushid", "this_is_push_id");

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
			list.add("werwe.test@starmark.co.kr");
			LinkRequestData data = new LinkRequestData("werwe.me@gamil.com","this is push id",list);
			ClientConfig clientConfig = new DefaultClientConfig();
			clientConfig.getFeatures().put(
					JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
			Client client = Client.create(clientConfig);

			WebResource webResource = client
					.resource("http://localhost:8881/apis/link");
			

//			ClientResponse response = webResource.accept("application/json")
//					.type("application/json").post(ClientResponse.class, data);
//
//			if (response.getStatus() != 200) {
//				throw new RuntimeException("Failed : HTTP error code : "
//						+ response.getStatus());
//			}
//
//			String output = response.getEntity(String.class);
//
//			System.out.println("Server response .... \n");
//			System.out.println(output);
			
			LinkRequestData response = webResource.accept("application/json")
					.type("application/json").post(LinkRequestData.class, data);


			System.out.println("Server response .... \n");
			System.out.println(response.toString());

		} catch (Exception e) {

			e.printStackTrace();

		}
	}
}