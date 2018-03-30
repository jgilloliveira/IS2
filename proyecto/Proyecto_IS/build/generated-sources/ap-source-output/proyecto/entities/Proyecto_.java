package proyecto.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import proyecto.entities.EstadoProyecto;
import proyecto.entities.GrupoDeTrabajo;
import proyecto.entities.Sprint;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-11T13:16:02")
@StaticMetamodel(Proyecto.class)
public class Proyecto_ { 

    public static volatile SingularAttribute<Proyecto, String> descripcion;
    public static volatile SingularAttribute<Proyecto, Integer> idProyecto;
    public static volatile SingularAttribute<Proyecto, EstadoProyecto> estado;
    public static volatile SingularAttribute<Proyecto, Date> fechaInicio;
    public static volatile CollectionAttribute<Proyecto, GrupoDeTrabajo> grupoDeTrabajoCollection;
    public static volatile CollectionAttribute<Proyecto, Sprint> sprintCollection;
    public static volatile SingularAttribute<Proyecto, String> nombre;
    public static volatile SingularAttribute<Proyecto, Date> fechaFin;

}