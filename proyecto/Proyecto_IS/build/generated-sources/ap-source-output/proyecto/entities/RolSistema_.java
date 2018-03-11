package proyecto.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import proyecto.entities.Usuario;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-11T13:16:02")
@StaticMetamodel(RolSistema.class)
public class RolSistema_ { 

    public static volatile SingularAttribute<RolSistema, String> descripcion;
    public static volatile SingularAttribute<RolSistema, Integer> idRol;
    public static volatile CollectionAttribute<RolSistema, Usuario> usuarioCollection;

}