package proyecto.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import proyecto.entities.Proyecto;
import proyecto.entities.Usuario;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-11T13:16:02")
@StaticMetamodel(GrupoDeTrabajo.class)
public class GrupoDeTrabajo_ { 

    public static volatile SingularAttribute<GrupoDeTrabajo, Proyecto> proyectoGrupo;
    public static volatile SingularAttribute<GrupoDeTrabajo, Integer> cantidad;
    public static volatile CollectionAttribute<GrupoDeTrabajo, Usuario> usuarioCollection;
    public static volatile SingularAttribute<GrupoDeTrabajo, String> nombre;
    public static volatile SingularAttribute<GrupoDeTrabajo, Integer> idGrupo;

}