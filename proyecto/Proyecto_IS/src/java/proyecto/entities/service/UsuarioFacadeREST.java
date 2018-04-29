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
import proyecto.entities.Usuario;

/**
 *
 * @author jacqueline
 */
@Stateless
@Path("proyecto.entities.usuario")
public class UsuarioFacadeREST extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "Proyecto_ISPU")
    private EntityManager em;

    public UsuarioFacadeREST() {
        super(Usuario.class);
    }

    @POST
    @Override
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(Usuario entity) {
        super.create(entity);
    }
    
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String findUser(Usuario usuario) {
        return super.findUser(usuario);
    }
    
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addUser(Usuario usuario) {
        super.addUser(usuario);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void edit(@PathParam("id") Integer id, Usuario entity) {
        super.edit(entity);
    }
    
    @PUT
    @Path("/edit")
    @Consumes(MediaType.APPLICATION_JSON)
    public void editUser(Usuario entity) {
        super.editUser(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Usuario find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @GET
    @Path("/ultimo")
    @Produces(MediaType.APPLICATION_JSON)
    public String ultimoUser() {
        return super.ultimoUser();
    }
    
    @GET
    @Path("/listar")
    @Produces(MediaType.APPLICATION_JSON)
    public String listarUser() {
        return super.listarUser();
    }
    
    @GET
    @Path("/listar/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String listarUnUser(@PathParam("id") Integer id) {
        return super.listarUnUser(id);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
