/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto.entities.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import proyecto.entities.UserHistories;

/**
 *
 * @author jacqueline
 */
@Stateless
@Path("proyecto.entities.userhistories")
public class UserHistoriesFacadeREST extends AbstractFacade<UserHistories> {

    @PersistenceContext(unitName = "Proyecto_ISPU")
    private EntityManager em;

    public UserHistoriesFacadeREST() {
        super(UserHistories.class);
    }

    @POST
    @Override
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(UserHistories entity) {
        super.create(entity);
    }
    
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addUS(UserHistories us) {
        return super.addUS(us);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void edit(@PathParam("id") Integer id, UserHistories entity) {
        super.edit(entity);
    }
    
    @PUT
    @Path("/edit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String editUS(UserHistories us) {
        return super.editUS(us);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserHistories find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserHistories> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserHistories> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }
    
    @GET
    @Path("/listar/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String listarUS(@PathParam("id") Integer id) {
        return super.listarUS(id);
    }
    
    @GET
    @Path("/listar/{idSprint}/{idUser}/{idEstado}")
    @Produces(MediaType.APPLICATION_JSON)
    public String listarUSEstadoUser(@PathParam("idSprint") Integer idSprint, @PathParam("idUser") Integer idUser,
            @PathParam("idEstado") Integer idEstado) {
        return super.listarUSEstadoUser(idSprint, idUser, idEstado);
    }
    
    @GET
    @Path("/listar/{idSprint}/{idEstado}")
    @Produces(MediaType.APPLICATION_JSON)
    public String listarUSEstado(@PathParam("idSprint") Integer idSprint, @PathParam("idEstado") Integer idEstado) {
        return super.listarUSEstado(idSprint, idEstado);
    }
    
    @GET
    @Path("/obtener/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String obtenerUS(@PathParam("id") Integer id) {
        return super.obtenerUS(id);
    }
    
    @GET
    @Path("/ultimo")
    @Produces(MediaType.APPLICATION_JSON)
    public String ultimoUS() {
        return super.ultimoUS();
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
