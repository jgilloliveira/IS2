package proyecto.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import proyecto.entities.Proyecto;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-11T13:16:02")
@StaticMetamodel(EstadoProyecto.class)
public class EstadoProyecto_ { 

    public static volatile SingularAttribute<EstadoProyecto, String> descripcion;
    public static volatile SingularAttribute<EstadoProyecto, Integer> idEstado;
    public static volatile CollectionAttribute<EstadoProyecto, Proyecto> proyectoCollection;

}