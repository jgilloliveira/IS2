package proyecto.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import proyecto.entities.Proyecto;
import proyecto.entities.UserHistories;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-18T12:35:38")
@StaticMetamodel(Sprint.class)
public class Sprint_ { 

    public static volatile SingularAttribute<Sprint, Date> fechaInicio;
    public static volatile SingularAttribute<Sprint, Integer> idSprint;
    public static volatile SingularAttribute<Sprint, Proyecto> proyecto;
    public static volatile CollectionAttribute<Sprint, UserHistories> userHistoriesCollection;
    public static volatile SingularAttribute<Sprint, String> nombre;
    public static volatile SingularAttribute<Sprint, Date> fechaFin;

}