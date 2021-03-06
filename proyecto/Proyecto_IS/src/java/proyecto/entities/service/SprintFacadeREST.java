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
import proyecto.entities.Sprint;

/**
 *
 * @author jacqueline
 */
@Stateless
@Path("proyecto.entities.sprint")
public class SprintFacadeREST extends AbstractFacade<Sprint> {

    @PersistenceContext(unitName = "Proyecto_ISPU")
    private EntityManager em;

    public SprintFacadeREST() {
        super(Sprint.class);
    }

    @POST
    @Override
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(Sprint entity) {
        super.create(entity);
    }
    
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addSprint(Sprint sprint) {
        return super.addSprint(sprint);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void edit(@PathParam("id") Integer id, Sprint entity) {
        super.edit(entity);
    }
    
    @PUT
    @Path("/edit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String editSprint(Sprint sprint) {
        return super.editSprint(sprint);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Sprint find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sprint> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sprint> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }
    
    @GET
    @Path("/ultimo")
    @Produces(MediaType.APPLICATION_JSON)
    public String ultimoSprint() {
        return super.ultimoSprint();
    }
    
    @GET
    @Path("/proyecto/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String obtenerProyecto(@PathParam("id") Integer id) {
        return super.obtenerProyecto(id);
    }
    
    @GET
    @Path("/listar/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String listarSprint(@PathParam("id") Integer id) {
        return super.listarSprint(id);
    }
    
    @GET
    @Path("/listar2/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String listarSprint2(@PathParam("id") Integer id) {
        return super.listarSprint2(id);
    }
    
    @GET
    @Path("/listar/miembro/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String listarSprintMiembro(@PathParam("id") Integer id) {
        return super.listarSprintMiembro(id);
    }
    
    @GET
    @Path("/listar/fecha/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String listarSprintFecha(@PathParam("id") Integer id) {
        return super.listarSprintFecha(id);
    }
    
    @GET
    @Path("/obtener/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String obtenerSprint(@PathParam("id") Integer id) {
        return super.obtenerSprint(id);
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
