package es.cic.curso2025.proy008.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Premio {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column
    int anio;

    @Column
    String nombrePremio;

    @Column
    String libroPremiado;

    @Column
    String categoria;

    public Premio(){

    }

    public Premio(Long id, int anio, String nombrePremio, String libroPremiado, String categoria){
        
        this.id = id;
        this.anio  = anio;
        this.nombrePremio = nombrePremio;
        this.libroPremiado = libroPremiado;
        this.categoria = categoria;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getNombrePremio() {
        return nombrePremio;
    }

    public void setNombrePremio(String nombrePremio) {
        this.nombrePremio = nombrePremio;
    }

    public String getLibroPremiado() {
        return libroPremiado;
    }

    public void setLibroPremiado(String libroPremiado) {
        this.libroPremiado = libroPremiado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + anio;
        result = prime * result + ((nombrePremio == null) ? 0 : nombrePremio.hashCode());
        result = prime * result + ((libroPremiado == null) ? 0 : libroPremiado.hashCode());
        result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Premio other = (Premio) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (anio != other.anio)
            return false;
        if (nombrePremio == null) {
            if (other.nombrePremio != null)
                return false;
        } else if (!nombrePremio.equals(other.nombrePremio))
            return false;
        if (libroPremiado == null) {
            if (other.libroPremiado != null)
                return false;
        } else if (!libroPremiado.equals(other.libroPremiado))
            return false;
        if (categoria == null) {
            if (other.categoria != null)
                return false;
        } else if (!categoria.equals(other.categoria))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Premios [id=" + id + ", anio=" + anio + ", nombrePremio=" + nombrePremio + ", libroPremiado="
                + libroPremiado + ", categoria=" + categoria + "]";
    }



}
