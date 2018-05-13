/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto.entities.service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author jacqueline
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(proyecto.entities.service.EstadoKanbanFacadeREST.class);
        resources.add(proyecto.entities.service.EstadoProyectoFacadeREST.class);
        resources.add(proyecto.entities.service.GrupoDeTrabajoFacadeREST.class);
        resources.add(proyecto.entities.service.ProyectoFacadeREST.class);
        resources.add(proyecto.entities.service.RolSistemaFacadeREST.class);
        resources.add(proyecto.entities.service.SprintFacadeREST.class);
        resources.add(proyecto.entities.service.UserHistoriesFacadeREST.class);
        resources.add(proyecto.entities.service.UsuarioFacadeREST.class);
    }
    
}
