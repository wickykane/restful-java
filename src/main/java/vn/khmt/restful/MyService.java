package vn.khmt.restful;

import javax.ws.rs.PathParam;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author TheNhan
 */
@Path("DoubleH")
public class MyService {

   //Example: http://localhost:8080/restful/DoubleH/User/1
    @GET
    @Path("User/{id}")
     public Response getUser(@PathParam("id") int id) {
        ConnectToSQL a = new ConnectToSQL(ConnectToSQL.POSTGRESQL,"ec2-54-227-253-228.compute-1.amazonaws.com","d8viikojj42e3b", "uzufecmqojhnyx", "WPJGueUbd3npLKslU2BEUOmMHx");
        String out = a.getUser(id);
        return Response.status(200).entity(out).build();
    }
     //Example:http://localhost:8080/restful/DoubleH/Add/15+huy+123+huy@gmail.com+1+huy
     @POST
     @Path("Add/{id}+{username}+{password}+{email}+{status}+{name}")
    public Response Insert(@PathParam("id") int id,@PathParam("username") String username, @PathParam("password") String password,
            @PathParam("email") String email, @PathParam("status") int status, @PathParam("name") String name) {
        ConnectToSQL a = new ConnectToSQL(ConnectToSQL.POSTGRESQL,"ec2-54-227-253-228.compute-1.amazonaws.com","d8viikojj42e3b", "uzufecmqojhnyx", "WPJGueUbd3npLKslU2BEUOmMHx");
        String out = a.insertUser(id,username,password,email,status,name);
        return Response.status(200).entity(out).build();
    }
}

