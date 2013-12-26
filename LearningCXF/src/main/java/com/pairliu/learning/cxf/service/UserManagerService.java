package com.pairliu.learning.cxf.service;

import javax.ws.rs.core.Response;

public class UserManagerService implements UserManager {

    public Response fetchAllUsers() {
        
        return Response.ok().build();
    }

}
