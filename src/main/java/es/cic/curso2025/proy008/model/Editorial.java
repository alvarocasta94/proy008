package es.cic.curso2025.proy008.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity(name = "EDITORIAL")
public class Editorial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "anio_fundacion")
    private int anio_fundacion;
    @Column(name = "direccion")
    private String direccion;

    @JsonIgnore
    @OneToOne(mappedBy = "editorial", cascade = CascadeType.REMOVE)
    private Libro libro;


    public Editorial() {
    }

    public Editorial(Long id, String nombre, int anio_fundacion, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.anio_fundacion = anio_fundacion;
        this.direccion = direccion;
    }

    

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getAnio_fundacion() {
        return anio_fundacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setAnio_fundacion(int anio_fundacion) {
        this.anio_fundacion = anio_fundacion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        Editorial other = (Editorial) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Editorial [id=" + id + ", nombre=" + nombre + ", anio_fundacion=" + anio_fundacion + "]";
    }

    
}
