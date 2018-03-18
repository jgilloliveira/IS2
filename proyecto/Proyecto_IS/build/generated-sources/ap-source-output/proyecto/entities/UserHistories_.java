package proyecto.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import proyecto.entities.EstadoKanban;
import proyecto.entities.Sprint;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-18T12:35:39")
@StaticMetamodel(UserHistories.class)
public class UserHistories_ { 

    public static volatile SingularAttribute<UserHistories, String> descripcion;
    public static volatile SingularAttribute<UserHistories, Sprint> usSprint;
    public static volatile SingularAttribute<UserHistories, Integer> idUs;
    public static volatile SingularAttribute<UserHistories, EstadoKanban> estadoKanban;

}