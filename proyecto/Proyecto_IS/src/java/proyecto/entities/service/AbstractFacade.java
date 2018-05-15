/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto.entities.service;

import java.util.List;
import javax.persistence.EntityManager;
import org.json.JSONArray;
import org.json.JSONObject;
import proyecto.entities.GrupoDeTrabajo;
import proyecto.entities.Proyecto;
import proyecto.entities.Sprint;
import proyecto.entities.UserHistories;
import proyecto.entities.Usuario;

/**
 *
 * @author jacqueline
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
    
    public String findUser(Usuario usuario) {
        javax.persistence.Query q1 = getEntityManager().createNativeQuery("select count(id_usuario) "
        + "from usuario where nombre_usuario = '"+usuario.getNombreUsuario()+"' and contrasenha = "
        + "'"+usuario.getContrasenha()+"' and grupo = "+usuario.getGrupo().getIdGrupo()+";");
        long resultado = (Long)q1.getSingleResult();
        JSONObject json = new JSONObject();
        if(resultado>0){
            javax.persistence.Query q2 = getEntityManager().createNativeQuery("select id_usuario || ',' ||"
            + " rol from usuario where nombre_usuario = '"+usuario.getNombreUsuario()+"' and contrasenha = "
            + "'"+usuario.getContrasenha()+"' and grupo = "+usuario.getGrupo().getIdGrupo()+";");
            String respuesta = (String)q2.getSingleResult();
            String[] partes = respuesta.split(",");
            json.put("idUsuario", partes[0]);
            json.put("rol", partes[1]);
            return json.toString();
        }else{
            json.put("idUsuario", 0);
            json.put("rol", 0);
            return json.toString();
        }
    }
    
    public String obtenerGrupo(int id) {
        javax.persistence.Query q = getEntityManager().createNativeQuery("select id_grupo "
        + "from grupo_de_trabajo where proyecto_grupo = "+id+";");
        int resultado = (int)q.getSingleResult();
        JSONObject json = new JSONObject();
        json.put("idGrupo", resultado);
        return json.toString();
    }
    
    public String addUser(Usuario usuario) {
        javax.persistence.Query q1 = getEntityManager().createNativeQuery("insert into usuario "
        + "(id_usuario, nombre, apellido, nombre_usuario, contrasenha, rol, grupo) "
        + "values ("+usuario.getIdUsuario()+", '"+usuario.getNombre()+"', '"+usuario.getApellido()+"', "
        + "'"+usuario.getNombreUsuario()+"', '"+usuario.getContrasenha()+"', "+usuario.getRol().getIdRol()+", "
        + usuario.getGrupo().getIdGrupo()+");");
        q1.executeUpdate();
        javax.persistence.Query q2 = getEntityManager().createNativeQuery("select count(id_usuario) "
        + "from usuario where id_usuario = "+usuario.getIdUsuario()+";");
        long resultado = (Long)q2.getSingleResult();
        JSONObject json = new JSONObject();
        if(resultado>0){
            json.put("respuesta", true);
        }else{
            json.put("respuesta", false);
        }
        return json.toString();
    }
    
    public String ultimoUser() {
        javax.persistence.Query q = getEntityManager().createNativeQuery("select coalesce(max(id_usuario),'0') "
        + "from usuario;");
        int resultado = (int)q.getSingleResult();
        JSONObject json = new JSONObject();
        json.put("ultimo", resultado);
        return json.toString();
    }
    
    public String listarUser(int id) {
        javax.persistence.Query q = getEntityManager().createNativeQuery("select id_usuario || ',' || "
        + "nombre || ',' || apellido from usuario where rol <> 1 and grupo = "+id+" order by id_usuario;");
        List resultado = q.getResultList();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < resultado.size(); i++) {
            JSONObject json = new JSONObject();
            String[] partes = resultado.get(i).toString().split(",");
            json.put("idUsuario", partes[0]);
            json.put("nombre", partes[1]);
            json.put("apellido", partes[2]);
            jsonArray.put(json);
        }
        return jsonArray.toString();
    }
    
    public String listarTodosUser(int id) {
        javax.persistence.Query q = getEntityManager().createNativeQuery("select u.id_usuario "
        + "||','|| u.nombre ||','|| u.apellido from usuario u join grupo_de_trabajo g "
        + "on u.grupo = g.id_grupo join proyecto p on g.proyecto_grupo = p.id_proyecto "
        + "join sprint s on s.proyecto = p.id_proyecto where s.id_sprint = "+id+" "
        + "order by u.id_usuario;");
        List resultado = q.getResultList();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < resultado.size(); i++) {
            JSONObject json = new JSONObject();
            String[] partes = resultado.get(i).toString().split(",");
            json.put("idUsuario", partes[0]);
            json.put("nombre", partes[1]);
            json.put("apellido", partes[2]);
            jsonArray.put(json);
        }
        return jsonArray.toString();
    }
    
    public String obtenerUser(int id) {
        javax.persistence.Query q = getEntityManager().createNativeQuery("select nombre || ',' || apellido "
        + "|| ',' || nombre_usuario || ',' || contrasenha || ',' || rol from usuario where id_usuario = "+id+";");
        String resultado = (String)q.getSingleResult();
        JSONObject json = new JSONObject();
        String[] partes = resultado.split(",");
        json.put("nombre", partes[0]);
        json.put("apellido", partes[1]);
        json.put("nombreUsuario", partes[2]);
        json.put("contrasenha", partes[3]);
        json.put("rol", partes[4]);
        return json.toString();
    }
    
    public String editUser(Usuario usuario) {
        javax.persistence.Query q1 = getEntityManager().createNativeQuery("update usuario set "
        + "nombre = '"+ usuario.getNombre()+"', apellido = '"+usuario.getApellido()+"', "
        + "nombre_usuario = '"+usuario.getNombreUsuario()+"', contrasenha = '"+ usuario.getContrasenha()+"', "
        + "rol = "+usuario.getRol().getIdRol()+" where id_usuario = "+usuario.getIdUsuario()+";");
        q1.executeUpdate();
        javax.persistence.Query q2 = getEntityManager().createNativeQuery("select count(id_usuario) "
        + "from usuario where id_usuario = "+usuario.getIdUsuario()+" and nombre = '"+ usuario.getNombre()+"' "
        + "and apellido = '"+usuario.getApellido()+"' and nombre_usuario = '"+usuario.getNombreUsuario()+"' "
        + "and contrasenha = '"+ usuario.getContrasenha()+"' and rol = "+usuario.getRol().getIdRol()+";");
        long resultado = (Long)q2.getSingleResult();
        JSONObject json = new JSONObject();
        if(resultado>0){
            json.put("respuesta", true);
        }else{
            json.put("respuesta", false);
        }
        return json.toString();
    }
    
    public String listarRol() {
        javax.persistence.Query q = getEntityManager().createNativeQuery("select id_rol || ',' || "
        + "descripcion from rol_sistema where id_rol > 1 order by id_rol;");
        List resultado = q.getResultList();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < resultado.size(); i++) {
            JSONObject json = new JSONObject();
            String[] partes = resultado.get(i).toString().split(",");
            json.put("idRol", partes[0]);
            json.put("descripcion", partes[1]);
            jsonArray.put(json);
        }
        return jsonArray.toString();
    }

// Estado Proyecto
    
    public String listarEstadoPro() {
        javax.persistence.Query q = getEntityManager().createNativeQuery("select id_estado || ',' || "
        + "descripcion from estado_proyecto order by id_estado;");
        List resultado = q.getResultList();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < resultado.size(); i++) {
            JSONObject json = new JSONObject();
            String[] partes = resultado.get(i).toString().split(",");
            json.put("idEstado", partes[0]);
            json.put("descripcion", partes[1]);
            jsonArray.put(json);
        }
        return jsonArray.toString();
    }

// Proyecto
    
    public String ultimoProyecto() {
        javax.persistence.Query q = getEntityManager().createNativeQuery("select coalesce(max(id_proyecto),'0') "
        + "from proyecto;");
        int resultado = (int)q.getSingleResult();
        JSONObject json = new JSONObject();
        json.put("ultimo", resultado);
        return json.toString();
    }
    
    public String addProyecto(Proyecto proyecto) {
        javax.persistence.Query q1 = getEntityManager().createNativeQuery("insert into proyecto "
        + "(id_proyecto, nombre, descripcion, fecha_inicio, fecha_fin, estado) "
        + "values ("+proyecto.getIdProyecto()+", '"+proyecto.getNombre()+"', '"+proyecto.getDescripcion()+"', "
        + "'"+proyecto.getFechaInicio()+"', '"+proyecto.getFechaFin()+"', "+proyecto.getEstado().getIdEstado()+");");
        q1.executeUpdate();
        javax.persistence.Query q2 = getEntityManager().createNativeQuery("select count(id_proyecto) "
        + "from proyecto where id_proyecto = "+proyecto.getIdProyecto()+";");
        long resultado = (Long)q2.getSingleResult();
        JSONObject json = new JSONObject();
        if(resultado>0){
            json.put("respuesta", true);
        }else{
            json.put("respuesta", false);
        }
        return json.toString();
    }
    
    public String listarProyecto() {
        javax.persistence.Query q = getEntityManager().createNativeQuery("select id_proyecto || ',' || "
        + "nombre from proyecto order by id_proyecto;");
        List resultado = q.getResultList();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < resultado.size(); i++) {
            JSONObject json = new JSONObject();
            String[] partes = resultado.get(i).toString().split(",");
            json.put("idProyecto", partes[0]);
            json.put("nombre", partes[1]);
            jsonArray.put(json);
        }
        return jsonArray.toString();
    }
    
    public String obtenerNombreEstadoProyecto(int id) {
        javax.persistence.Query q = getEntityManager().createNativeQuery("select p.id_proyecto || ',' || "
        + "p.nombre ||','|| e.descripcion from proyecto p join estado_proyecto e on p.estado = e.id_estado "
        + "join grupo_de_trabajo g on g.proyecto_grupo = p.id_proyecto where g.id_grupo = "+id+";");
        String resultado = (String) q.getSingleResult();
        JSONObject json = new JSONObject();
        String[] partes = resultado.split(",");
        json.put("idProyecto", partes[0]);
        json.put("nombre", partes[1]);
        json.put("estado", partes[2]);
        return json.toString();
    }

// Grupo Proyecto
    
    public String ultimoGrupo() {
        javax.persistence.Query q = getEntityManager().createNativeQuery("select coalesce(max(id_grupo),'0') "
        + "from grupo_de_trabajo;");
        int resultado = (int)q.getSingleResult();
        JSONObject json = new JSONObject();
        json.put("ultimo", resultado);
        return json.toString();
    }
    
    public String addGrupo(GrupoDeTrabajo grupo) {
        javax.persistence.Query q1 = getEntityManager().createNativeQuery("insert into grupo_de_trabajo "
        + "(id_grupo, cantidad, nombre, proyecto_grupo) values ("+grupo.getIdGrupo()+", "+grupo.getCantidad()+", "
        + "'"+grupo.getNombre()+"', "+grupo.getProyectoGrupo().getIdProyecto()+");");
        q1.executeUpdate();
        javax.persistence.Query q2 = getEntityManager().createNativeQuery("select count(id_grupo) "
        + "from grupo_de_trabajo where id_grupo = "+grupo.getIdGrupo()+";");
        long resultado = (Long)q2.getSingleResult();
        JSONObject json = new JSONObject();
        if(resultado>0){
            json.put("respuesta", true);
        }else{
            json.put("respuesta", false);
        }
        return json.toString();
    }

// Sprint
    
    public String listarSprint(int id) {
        javax.persistence.Query q = getEntityManager().createNativeQuery("select s.id_sprint ||','|| s.nombre "
        + "from sprint s join proyecto p on s.proyecto = p.id_proyecto join grupo_de_trabajo g "
        + "on p.id_proyecto = g.proyecto_grupo join usuario u on g.id_grupo = u.grupo "
        + "where u.id_usuario="+id+" order by s.id_sprint;");
        List resultado = q.getResultList();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < resultado.size(); i++) {
            JSONObject json = new JSONObject();
            String[] partes = resultado.get(i).toString().split(",");
            json.put("idSprint", partes[0]);
            json.put("nombre", partes[1]);
            jsonArray.put(json);
        }
        return jsonArray.toString();
    }
    
    public String listarSprint2(int id) {
        javax.persistence.Query q = getEntityManager().createNativeQuery("select id_sprint ||','|| nombre "
        + "from sprint where proyecto="+id+" order by id_sprint;");
        List resultado = q.getResultList();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < resultado.size(); i++) {
            JSONObject json = new JSONObject();
            String[] partes = resultado.get(i).toString().split(",");
            json.put("idSprint", partes[0]);
            json.put("nombre", partes[1]);
            jsonArray.put(json);
        }
        return jsonArray.toString();
    }
    
        public String listarSprintMiembro(int id) {
        javax.persistence.Query q = getEntityManager().createNativeQuery("select distinct s.id_sprint ||','|| "
        + "s.nombre from sprint s join user_histories u on u.us_sprint = s.id_sprint where "
        + "u.usuario = "+id+";");
        List resultado = q.getResultList();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < resultado.size(); i++) {
            JSONObject json = new JSONObject();
            String[] partes = resultado.get(i).toString().split(",");
            json.put("idSprint", partes[0]);
            json.put("nombre", partes[1]);
            jsonArray.put(json);
        }
        return jsonArray.toString();
    }
        
    public String listarSprintFecha(int id) {
        javax.persistence.Query q = getEntityManager().createNativeQuery("select distinct s.id_sprint ||','|| "
        + "s.nombre ||','|| s.fecha_fin from sprint s join user_histories u on u.us_sprint = s.id_sprint "
        + "join estado_kanban e on e.id_estado_kanban = u.estado_kanban where e.descripcion <> 'Hecho' "
        + "and u.usuario = "+id+";");
        List resultado = q.getResultList();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < resultado.size(); i++) {
            JSONObject json = new JSONObject();
            String[] partes = resultado.get(i).toString().split(",");
            json.put("idSprint", partes[0]);
            json.put("nombre", partes[1]);
            json.put("fechaFin", partes[2]);
            jsonArray.put(json);
        }
        return jsonArray.toString();
    }
    
    public String obtenerSprint(int id) {
        javax.persistence.Query q = getEntityManager().createNativeQuery("select nombre || ',' || fecha_inicio "
        + "|| ',' || fecha_fin from sprint where id_sprint = "+id+";");
        String resultado = (String)q.getSingleResult();
        JSONObject json = new JSONObject();
        String[] partes = resultado.split(",");
        json.put("nombre", partes[0]);
        json.put("fechaInicio", partes[1]);
        json.put("fechaFin", partes[2]);
        return json.toString();
    }
    
    public String ultimoSprint() {
        javax.persistence.Query q = getEntityManager().createNativeQuery("select coalesce(max(id_sprint),'0') "
        + "from sprint; ");
        int resultado = (int)q.getSingleResult();
        JSONObject json = new JSONObject();
        json.put("ultimo", resultado);
        return json.toString();
    }
    
    public String obtenerProyecto(int id) {
        javax.persistence.Query q = getEntityManager().createNativeQuery("select g.proyecto_grupo "
        + "from grupo_de_trabajo g join usuario u on g.id_grupo = u.grupo where u.id_usuario = "+id+";");
        int resultado = (int)q.getSingleResult();
        JSONObject json = new JSONObject();
        json.put("idProyecto", resultado);
        return json.toString();
    }
    
    public String addSprint(Sprint sprint) {
        javax.persistence.Query q1 = getEntityManager().createNativeQuery("insert into sprint "
        + "(id_sprint, nombre, fecha_inicio, fecha_fin, proyecto) values ("+sprint.getIdSprint()+", "
        + "'"+sprint.getNombre()+"', '"+sprint.getFechaInicio()+"', '"+sprint.getFechaFin()+"', "
        + ""+sprint.getProyecto().getIdProyecto()+");");
        q1.executeUpdate();
        javax.persistence.Query q2 = getEntityManager().createNativeQuery("select count(id_sprint) "
        + "from sprint where id_sprint = "+sprint.getIdSprint()+";");
        long resultado = (Long)q2.getSingleResult();
        JSONObject json = new JSONObject();
        if(resultado>0){
            json.put("respuesta", true);
        }else{
            json.put("respuesta", false);
        }
        return json.toString();
    }
    
    public String editSprint(Sprint sprint) {
        System.out.print("FECHA FIN: "+sprint.getFechaFin());
        javax.persistence.Query q1 = getEntityManager().createNativeQuery("update sprint set "
        + "nombre = '"+ sprint.getNombre()+"', fecha_inicio = '"+sprint.getFechaInicio()+"', "
        + "fecha_fin = '"+sprint.getFechaFin()+"' where id_sprint = "+sprint.getIdSprint()+";");
        q1.executeUpdate();
        javax.persistence.Query q2 = getEntityManager().createNativeQuery("select count(id_sprint) "
        + "from sprint where id_sprint = "+sprint.getIdSprint()+" and nombre = '"+ sprint.getNombre()+"' "
        + "and fecha_inicio = '"+sprint.getFechaInicio()+"' and fecha_fin = '"+sprint.getFechaFin()+"';");
        long resultado = (Long)q2.getSingleResult();
        JSONObject json = new JSONObject();
        if(resultado>0){
            json.put("respuesta", true);
        }else{
            json.put("respuesta", false);
        }
        return json.toString();
    }
    
//User Story
    
    public String listarUS(int id) {
        javax.persistence.Query q = getEntityManager().createNativeQuery("select id_us ||','|| descripcion "
        + "from user_histories where us_sprint = "+id+" order by id_us;");
        List resultado = q.getResultList();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < resultado.size(); i++) {
            JSONObject json = new JSONObject();
            String[] partes = resultado.get(i).toString().split(",");
            json.put("idUs", partes[0]);
            json.put("descripcion", partes[1]);
            jsonArray.put(json);
        }
        return jsonArray.toString();
    }
    
    public String listarUSEstadoUser(int idSprint, int idUser, int idEstado) {
        javax.persistence.Query q = getEntityManager().createNativeQuery("select id_us ||','|| descripcion "
        + "from user_histories where us_sprint = "+idSprint+" and usuario = "+idUser+" and "
        + "estado_kanban = "+idEstado+" order by id_us;");
        List resultado = q.getResultList();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < resultado.size(); i++) {
            JSONObject json = new JSONObject();
            String[] partes = resultado.get(i).toString().split(",");
            json.put("idUs", partes[0]);
            json.put("descripcion", partes[1]);
            jsonArray.put(json);
        }
        return jsonArray.toString();
    }
    
    public String listarUSEstado(int idSprint, int idEstado) {
        javax.persistence.Query q = getEntityManager().createNativeQuery("select descripcion "
        + "from user_histories where us_sprint = "+idSprint+" and estado_kanban = "+idEstado+" "
        + "order by id_us;");
        List resultado = q.getResultList();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < resultado.size(); i++) {
            JSONObject json = new JSONObject();
            json.put("descripcion", resultado.get(i).toString());
            jsonArray.put(json);
        }
        return jsonArray.toString();
    }
    
    public String obtenerUS(int id) {
        javax.persistence.Query q = getEntityManager().createNativeQuery("select descripcion || ',' || estado_kanban "
        + "||','|| usuario from user_histories where id_us = "+id+";");
        String resultado = (String)q.getSingleResult();
        JSONObject json = new JSONObject();
        String[] partes = resultado.split(",");
        json.put("descripcion", partes[0]);
        json.put("estadoKanban", partes[1]);
        json.put("usuario", partes[2]);
        return json.toString();
    }
    
    public String ultimoUS() {
        javax.persistence.Query q = getEntityManager().createNativeQuery("select coalesce(max(id_us),'0') "
        + "from user_histories; ");
        int resultado = (int)q.getSingleResult();
        JSONObject json = new JSONObject();
        json.put("ultimo", resultado);
        return json.toString();
    }
    
    public String addUS(UserHistories us) {
        javax.persistence.Query q1 = getEntityManager().createNativeQuery("insert into user_histories "
        + "(id_us, descripcion, estado_kanban, us_sprint, usuario) values ("+us.getIdUs()+", "
        + "'"+us.getDescripcion()+"', "+us.getEstadoKanban().getIdEstadoKanban()+", "
        + ""+us.getUsSprint().getIdSprint()+", "+us.getUsuario().getIdUsuario()+");");
        q1.executeUpdate();
        javax.persistence.Query q2 = getEntityManager().createNativeQuery("select count(id_us) "
        + "from user_histories where id_us = "+us.getIdUs()+";");
        long resultado = (Long)q2.getSingleResult();
        JSONObject json = new JSONObject();
        if(resultado>0){
            json.put("respuesta", true);
        }else{
            json.put("respuesta", false);
        }
        return json.toString();
    }
    
    public String editUS(UserHistories us) {
        javax.persistence.Query q1 = getEntityManager().createNativeQuery("update user_histories set "
        + "descripcion = '"+us.getDescripcion()+"', estado_kanban = "+us.getEstadoKanban().getIdEstadoKanban()+", "
        + "usuario = "+us.getUsuario().getIdUsuario()+" where id_us = "+us.getIdUs()+";");
        q1.executeUpdate();
        javax.persistence.Query q2 = getEntityManager().createNativeQuery("select count(id_us) "
        + "from user_histories where id_us = "+us.getIdUs()+" and descripcion = '"+us.getDescripcion()+"' "
        + "and estado_kanban = "+us.getEstadoKanban().getIdEstadoKanban()+" and usuario = "+us.getUsuario().getIdUsuario()+";");
        long resultado = (Long)q2.getSingleResult();
        JSONObject json = new JSONObject();
        if(resultado>0){
            json.put("respuesta", true);
        }else{
            json.put("respuesta", false);
        }
        return json.toString();
    }
    
// Estado Kanban
    
    public String listarEstadoKan() {
        javax.persistence.Query q = getEntityManager().createNativeQuery("select id_estado_kanban || ',' || "
        + "descripcion from estado_kanban order by id_estado_kanban;");
        List resultado = q.getResultList();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < resultado.size(); i++) {
            JSONObject json = new JSONObject();
            String[] partes = resultado.get(i).toString().split(",");
            json.put("idEstadoKanban", partes[0]);
            json.put("descripcion", partes[1]);
            jsonArray.put(json);
        }
        return jsonArray.toString();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
