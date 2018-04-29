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
        javax.persistence.Query q1 = getEntityManager().createNativeQuery("select count(u.nombre_usuario) "
        + "from usuario u where u.nombre_usuario like '"+usuario.getNombreUsuario()+"' and u.contrasenha"
        + " like '"+usuario.getContrasenha()+"';");
        long resultado = (Long)q1.getSingleResult();
        JSONObject json = new JSONObject();
        if(resultado>0){
            javax.persistence.Query q2 = getEntityManager().createNativeQuery("select id_usuario || ',' ||"
            + " rol from usuario where nombre_usuario like '"+usuario.getNombreUsuario()+"' and contrasenha"
            + " like '"+usuario.getContrasenha()+"';");
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
    
    public void addUser(Usuario usuario) {
        javax.persistence.Query q = getEntityManager().createNativeQuery("insert into usuario "
        + "(id_usuario, nombre, apellido, nombre_usuario, contrasenha, rol, grupo) "
        + "values ("+usuario.getIdUsuario()+", '"+usuario.getNombre()+"', '"+usuario.getApellido()+"', "
        + "'"+usuario.getNombreUsuario()+"', '"+usuario.getContrasenha()+"', "+usuario.getRol().getIdRol()+", "
        + usuario.getGrupo().getIdGrupo()+");");
        q.executeUpdate();
    }
    
    public String ultimoUser() {
        javax.persistence.Query q = getEntityManager().createNativeQuery("select max(id_usuario) from usuario;");
        int resultado = (int)q.getSingleResult();
        JSONObject json = new JSONObject();
        json.put("ultimo", resultado);
        return json.toString();
    }
    
    public String listarUser() {
        javax.persistence.Query q = getEntityManager().createNativeQuery("select id_usuario || ',' || "
        + "nombre || ',' || apellido from usuario where rol <> 1 order by id_usuario;");
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
    
    public String listarUnUser(int id) {
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
    
    public void editUser(Usuario usuario) {
        javax.persistence.Query q = getEntityManager().createNativeQuery("update usuario set "
        + "nombre = '"+ usuario.getNombre()+"', apellido = '"+usuario.getApellido()+"', "
        + "nombre_usuario = '"+usuario.getNombreUsuario()+"', contrasenha = '"+ usuario.getContrasenha()+"', "
        + "rol = "+usuario.getRol().getIdRol()+" where id_usuario = "+usuario.getIdUsuario()+";");
        q.executeUpdate();
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

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
