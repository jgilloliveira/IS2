/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jacqueline
 */
@Entity
@Table(name = "user_histories")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserHistories.findAll", query = "SELECT u FROM UserHistories u")
    , @NamedQuery(name = "UserHistories.findByIdUs", query = "SELECT u FROM UserHistories u WHERE u.idUs = :idUs")
    , @NamedQuery(name = "UserHistories.findByDescripcion", query = "SELECT u FROM UserHistories u WHERE u.descripcion = :descripcion")})
public class UserHistories implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_us")
    private Integer idUs;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "estado_kanban", referencedColumnName = "id_estado_kanban")
    @ManyToOne(optional = false)
    private EstadoKanban estadoKanban;
    @JoinColumn(name = "us_sprint", referencedColumnName = "id_sprint")
    @ManyToOne(optional = false)
    private Sprint usSprint;

    public UserHistories() {
    }

    public UserHistories(Integer idUs) {
        this.idUs = idUs;
    }

    public UserHistories(Integer idUs, String descripcion) {
        this.idUs = idUs;
        this.descripcion = descripcion;
    }

    public Integer getIdUs() {
        return idUs;
    }

    public void setIdUs(Integer idUs) {
        this.idUs = idUs;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoKanban getEstadoKanban() {
        return estadoKanban;
    }

    public void setEstadoKanban(EstadoKanban estadoKanban) {
        this.estadoKanban = estadoKanban;
    }

    public Sprint getUsSprint() {
        return usSprint;
    }

    public void setUsSprint(Sprint usSprint) {
        this.usSprint = usSprint;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUs != null ? idUs.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserHistories)) {
            return false;
        }
        UserHistories other = (UserHistories) object;
        if ((this.idUs == null && other.idUs != null) || (this.idUs != null && !this.idUs.equals(other.idUs))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto.entities.UserHistories[ idUs=" + idUs + " ]";
    }
    
}
