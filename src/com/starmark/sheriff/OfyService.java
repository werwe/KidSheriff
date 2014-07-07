package com.starmark.sheriff;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.starmark.sheriff.Entity.LinkInfo;
import com.starmark.sheriff.Entity.Location;
import com.starmark.sheriff.Entity.UserInfo;

public class OfyService {
    static {
        ofy().factory().register(LinkInfo.class);
        ofy().factory().register(Location.class);
        ofy().factory().register(UserInfo.class);
        System.out.print("registerd");
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}