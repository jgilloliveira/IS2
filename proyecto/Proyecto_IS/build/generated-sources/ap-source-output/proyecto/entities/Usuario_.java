package proyecto.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import proyecto.entities.GrupoDeTrabajo;
import proyecto.entities.RolSistema;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-04T17:33:59")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile SingularAttribute<Usuario, Integer> idUsuario;
    public static volatile SingularAttribute<Usuario, String> apellido;
    public static volatile SingularAttribute<Usuario, GrupoDeTrabajo> grupo;
    public static volatile SingularAttribute<Usuario, String> nombreUsuario;
    public static volatile SingularAttribute<Usuario, String> nombre;
    public static volatile SingularAttribute<Usuario, String> contrasenha;
    public static volatile SingularAttribute<Usuario, RolSistema> rol;

}