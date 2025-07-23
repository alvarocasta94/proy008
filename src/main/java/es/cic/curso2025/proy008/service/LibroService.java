package es.cic.curso2025.proy008.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.cic.curso2025.proy008.model.Libro;
import es.cic.curso2025.proy008.repository.LibroRepository;

@Service
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(LibroService.class);

    public Optional<Libro> get(Long id) {

        LOGGER.info(String.format("Leído el libro con id %s", id));

        Optional<Libro> libro = libroRepository.findById(id);

        return libro;
    }

    public List<Libro> getAll() {

        LOGGER.info("Obteniendo todos los libros desde la base de datos");
        return libroRepository.findAll();
    }

    public Libro create(Libro libro) {

        LOGGER.info(String.format("Creación del libro con nombre %s", libro.getNombre()));

        libroRepository.save(libro);

        return libro;

    }

    public void delete(Long id) {

        LOGGER.info(String.format("Eliminación del libro con id %s", id));

        if (!libroRepository.existsById(id)) {
            throw new EntidadNoEncontradaException("No se puede eliminar: libro no encontrado con ID: " + id);
        }

        libroRepository.deleteById(id);

    }

    public Libro update(Long id, Libro libroActualizado) {
        Libro existente = libroRepository.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("Libro no encontrado con ID: " + id));

        // Aquí ignoras el libroActualizado.getId() y actualizas solo los campos
        // necesarios
        existente.setNombre(libroActualizado.getNombre());
        existente.setAutor(libroActualizado.getAutor());
        existente.setDescripcion(libroActualizado.getDescripcion());
        existente.setFechaPublicacion(libroActualizado.getFechaPublicacion());

        return libroRepository.save(existente);
    }

}
