package com.starmark.sheriff;

import com.google.appengine.api.datastore.AsyncDatastoreService;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

public final class DataStore {
	private static final DatastoreService mInstance =
			DatastoreServiceFactory.getDatastoreService();

	private DataStore(){}
	
	public static DatastoreService get(){
		return mInstance;
	}
	
	public static AsyncDatastoreService asyncGet(){
		return DatastoreServiceFactory.getAsyncDatastoreService();
	}

}
