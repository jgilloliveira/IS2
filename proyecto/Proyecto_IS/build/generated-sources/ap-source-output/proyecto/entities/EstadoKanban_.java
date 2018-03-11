package proyecto.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import proyecto.entities.UserHistories;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-04T17:33:59")
@StaticMetamodel(EstadoKanban.class)
public class EstadoKanban_ { 

    public static volatile SingularAttribute<EstadoKanban, Integer> idEstadoKanban;
    public static volatile SingularAttribute<EstadoKanban, String> descripcion;
    public static volatile CollectionAttribute<EstadoKanban, UserHistories> userHistoriesCollection;

}