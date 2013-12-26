package com.pairliu.learning.cxf.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Consumes("application/json")
@Produces("application/json")
public interface UserManager {

    @GET
    @Path("/fetchAllUsers/")
    public Response fetchAllUsers();

}