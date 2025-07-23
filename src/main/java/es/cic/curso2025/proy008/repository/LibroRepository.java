package es.cic.curso2025.proy008.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.cic.curso2025.proy008.model.Libro;


public interface LibroRepository extends JpaRepository<Libro,Long>{
    
}
