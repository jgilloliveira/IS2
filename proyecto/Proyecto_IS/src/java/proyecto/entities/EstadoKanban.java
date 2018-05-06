/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jacqueline
 */
@Entity
@Table(name = "estado_kanban")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoKanban.findAll", query = "SELECT e FROM EstadoKanban e")
    , @NamedQuery(name = "EstadoKanban.findByIdEstadoKanban", query = "SELECT e FROM EstadoKanban e WHERE e.idEstadoKanban = :idEstadoKanban")
    , @NamedQuery(name = "EstadoKanban.findByDescripcion", query = "SELECT e FROM EstadoKanban e WHERE e.descripcion = :descripcion")})
public class EstadoKanban implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_estado_kanban")
    private Integer idEstadoKanban;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadoKanban")
    private Collection<UserHistories> userHistoriesCollection;

    public EstadoKanban() {
    }

    public EstadoKanban(Integer idEstadoKanban) {
        this.idEstadoKanban = idEstadoKanban;
    }

    public EstadoKanban(Integer idEstadoKanban, String descripcion) {
        this.idEstadoKanban = idEstadoKanban;
        this.descripcion = descripcion;
    }

    public Integer getIdEstadoKanban() {
        return idEstadoKanban;
    }

    public void setIdEstadoKanban(Integer idEstadoKanban) {
        this.idEstadoKanban = idEstadoKanban;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public Collection<UserHistories> getUserHistoriesCollection() {
        return userHistoriesCollection;
    }

    public void setUserHistoriesCollection(Collection<UserHistories> userHistoriesCollection) {
        this.userHistoriesCollection = userHistoriesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstadoKanban != null ? idEstadoKanban.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoKanban)) {
            return false;
        }
        EstadoKanban other = (EstadoKanban) object;
        if ((this.idEstadoKanban == null && other.idEstadoKanban != null) || (this.idEstadoKanban != null && !this.idEstadoKanban.equals(other.idEstadoKanban))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto.entities.EstadoKanban[ idEstadoKanban=" + idEstadoKanban + " ]";
    }
    
}
