package es.cic.curso2025.proy008.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Escritor")
public class Escritor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;

    private int edad;

    private long cantidadLibros;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "premio_id")
    private Premio premio;
    
    public Escritor(){

    }

  
    public Escritor(Long id, String nombre, int edad, long cantidadLibros){
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.cantidadLibros = cantidadLibros;
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }


        public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public long getCantidadLibros() {
        return cantidadLibros;
    }

    public void setCantidadLibros(long cantidadLibros) {
        this.cantidadLibros = cantidadLibros;
    }

    public Premio getPremio() {
        return premio;
    }

    public void setPremio(Premio premio) {
        this.premio = premio;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + edad;
        result = prime * result + (int) (cantidadLibros ^ (cantidadLibros >>> 32));
        result = prime * result + ((premio == null) ? 0 : premio.hashCode());
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
        Escritor other = (Escritor) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (edad != other.edad)
            return false;
        if (cantidadLibros != other.cantidadLibros)
            return false;
        if (premio == null) {
            if (other.premio != null)
                return false;
        } else if (!premio.equals(other.premio))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "Escritor [id=" + id + ", nombre=" + nombre + ", edad=" + edad + ", cantidadLibros=" + cantidadLibros
                + ", premio=" + premio + "]";
    }




}
