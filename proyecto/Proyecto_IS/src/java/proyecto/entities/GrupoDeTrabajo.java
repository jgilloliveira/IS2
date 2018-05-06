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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "grupo_de_trabajo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrupoDeTrabajo.findAll", query = "SELECT g FROM GrupoDeTrabajo g")
    , @NamedQuery(name = "GrupoDeTrabajo.findByIdGrupo", query = "SELECT g FROM GrupoDeTrabajo g WHERE g.idGrupo = :idGrupo")
    , @NamedQuery(name = "GrupoDeTrabajo.findByCantidad", query = "SELECT g FROM GrupoDeTrabajo g WHERE g.cantidad = :cantidad")
    , @NamedQuery(name = "GrupoDeTrabajo.findByNombre", query = "SELECT g FROM GrupoDeTrabajo g WHERE g.nombre = :nombre")})
public class GrupoDeTrabajo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_grupo")
    private Integer idGrupo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad")
    private int cantidad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nombre")
    private String nombre;
    @JoinColumn(name = "proyecto_grupo", referencedColumnName = "id_proyecto")
    @ManyToOne(optional = false)
    private Proyecto proyectoGrupo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupo")
    private Collection<Usuario> usuarioCollection;

    public GrupoDeTrabajo() {
    }

    public GrupoDeTrabajo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public GrupoDeTrabajo(Integer idGrupo, int cantidad, String nombre) {
        this.idGrupo = idGrupo;
        this.cantidad = cantidad;
        this.nombre = nombre;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Proyecto getProyectoGrupo() {
        return proyectoGrupo;
    }

    public void setProyectoGrupo(Proyecto proyectoGrupo) {
        this.proyectoGrupo = proyectoGrupo;
    }

    @XmlTransient
    public Collection<Usuario> getUsuarioCollection() {
        return usuarioCollection;
    }

    public void setUsuarioCollection(Collection<Usuario> usuarioCollection) {
        this.usuarioCollection = usuarioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGrupo != null ? idGrupo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrupoDeTrabajo)) {
            return false;
        }
        GrupoDeTrabajo other = (GrupoDeTrabajo) object;
        if ((this.idGrupo == null && other.idGrupo != null) || (this.idGrupo != null && !this.idGrupo.equals(other.idGrupo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto.entities.GrupoDeTrabajo[ idGrupo=" + idGrupo + " ]";
    }
    
}
