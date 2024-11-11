package edu.upc.dsa.services;

import edu.upc.dsa.ElementManager;
import edu.upc.dsa.ElementManagerImpl;
import edu.upc.dsa.exceptions.ElementNotFoundException;
import edu.upc.dsa.exceptions.UserNotFoundException;
import edu.upc.dsa.models.ElementType;
import edu.upc.dsa.models.MapElement;
import edu.upc.dsa.models.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

@Api(value = "/elements", description = "Elements and users service")
@Path("/elements")
public class ElementsService {
    private ElementManager em = ElementManagerImpl.getInstance();

    public ElementsService() {
        if(em.listUsers().isEmpty()){
            em.addUser("Jan", "Viñas", "jan.vinas@estudiantat.upc.edu", "1/4/2003");
            em.addElement(ElementType.WALL, 1, 5);
            em.addElement(ElementType.SWORD, 1, 6);
            em.addElement(ElementType.POTION, 1, 7);
            em.addElement(ElementType.GRASS, 1, 8);
        }
    }

    @POST
    @ApiOperation(value = "crea un nou User")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= User.class),
            @ApiResponse(code = 500, message = "Internal error")
    })

    @Path("/users")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(User user){
        //ja tenim un objecte User, però volem generar la id i no acceptar llocs ja visitats
        User u = em.addUser(user.getName(), user.getSurname(), user.getEmail(), user.getBirthday());
        GenericEntity<User> entity = new GenericEntity<User>(u) {};
        return Response.status(201).entity(entity).build();
    }


    @GET
    @ApiOperation(value = "llista els User alfabèticament")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response= TreeSet.class),
            @ApiResponse(code = 500, message = "Internal error")
    })
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listUsers() {
        GenericEntity<TreeSet<User>> entity = new GenericEntity<TreeSet<User>>(em.listUsers()) {};
        return Response.status(200).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "Obté informació d'un usuari")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response= User.class),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 500, message = "Internal error")
    })
    @Path("/user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("userId") String userId) {
        try{
            User u = em.getUser(userId);
            GenericEntity<User> entity = new GenericEntity<User>(u) {};
            return Response.status(200).entity(entity).build();
        }catch(UserNotFoundException e){
            return Response.status(404).build();
        }
    }

    @POST
    @ApiOperation(value = "Afegeix un element al mapa")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= User.class),
            @ApiResponse(code = 400, message = "Invalid element type"),
            @ApiResponse(code = 500, message = "Internal error")
    })
    @Path("/elements")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addElement(MapElement element){
        MapElement e = new MapElement(element.getType(), element.getHorizontal(), element.getVertical());
        if(e.getType() == null){
            return Response.status(400).build();
        }
        em.addElement(e);
        return Response.status(201).entity(e).build();
    }

    @PUT
    @ApiOperation(value = "Passa per un element del mapa")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Invalid user"),
            @ApiResponse(code = 404, message = "Element not found"),
            @ApiResponse(code = 500, message = "Internal error")
    })
    @Path("/elements/{userId}/{horizontal}/{vertical}")
    public Response visitElement(@PathParam("userId") String userId, @PathParam("horizontal") int horizontal, @PathParam("vertical") int vertical){
        try{
            em.visitElement(userId, horizontal, vertical);
        }catch(UserNotFoundException e){
            return Response.status(400).build();
        }catch(ElementNotFoundException e){
            return Response.status(404).build();
        }
        return Response.status(200).build();
    }

    @GET
    @ApiOperation(value = "Consultar punts d'interès")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response= TreeMap.class),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 500, message = "Internal error")
    })
    @Path("/user/{userId}/elements")
    public Response getElements(@PathParam("userId") String userId) {
        try{
            User u = em.getUser(userId);
            GenericEntity<TreeMap<LocalDateTime, MapElement>> entity = new GenericEntity<TreeMap<LocalDateTime, MapElement>>(u.getVisitedElements()) {};
            return Response.status(200).entity(entity).build();
        }catch(UserNotFoundException e){
            return Response.status(404).build();
        }
    }


    @GET
    @ApiOperation(value = "Consultar usuaris que han passat per un punt d'interès")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response= TreeMap.class),
            @ApiResponse(code = 404, message = "Element not found"),
            @ApiResponse(code = 500, message = "Internal error")
    })
    @Path("/usersSeen/{horizontal}/{vertical}/")
    public Response getUsers(@PathParam("horizontal") int horizontal, @PathParam("vertical") int vertical) {
        try{
            MapElement e = em.getElement(horizontal, vertical);
            GenericEntity<TreeMap<LocalDateTime, User>> entity = new GenericEntity<TreeMap<LocalDateTime, User>>(e.getUsersSeen()) {};
            return Response.status(200).entity(entity).build();
        }catch(ElementNotFoundException e){
            return Response.status(404).build();
        }
    }

    @GET
    @ApiOperation(value = "Consultar punts d'interès t'un tipus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response= ArrayList.class),
            @ApiResponse(code = 400, message = "Invalid element type"),
            @ApiResponse(code = 500, message = "Internal error")
    })
    @Path("/elements/{type}")
    public Response getMapElements(@PathParam("type") ElementType type) {
        if(type == null) return Response.status(400).build();
        GenericEntity<ArrayList<MapElement>> entity = new GenericEntity<ArrayList<MapElement>>(em.getElements(type)) {};
        return Response.status(200).entity(entity).build();
    }
}
