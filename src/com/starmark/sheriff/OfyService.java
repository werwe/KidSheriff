package com.starmark.sheriff;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.starmark.sheriff.Entity.LinkRequest;
import com.starmark.sheriff.Entity.Location;
import com.starmark.sheriff.Entity.UserSetting;

public class OfyService {
    static {
        ofy().factory().register(LinkRequest.class);
        ofy().factory().register(Location.class);
        ofy().factory().register(UserSetting.class);
        System.out.print("registerd");
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}