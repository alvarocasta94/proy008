package es.cic.curso2025.proy008.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.cic.curso2025.proy008.model.Libro;
import es.cic.curso2025.proy008.service.LibroService;

@RestController
@RequestMapping("/libro")
public class LibroController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LibroController.class);

    @Autowired
    private LibroService libroService;

    @GetMapping("/{id}")
    public Optional<Libro> get(@PathVariable Long id) {

        LOGGER.info("Enpoint GET /libro/id obtener libro por id");

        Optional<Libro> libro = libroService.get(id);

        return libro;

    }

    @GetMapping
    public List<Libro> getAll() {

        LOGGER.info("Enpoint GET /libro obtener todos los libros");
        List<Libro> libros = libroService.getAll();

        return libros;

    }

    @PostMapping
    public Libro create(@RequestBody Libro libro) {
        if (libro.getId() != null) {
            throw new ModificacionSecurityException("Has tratado de modificar mediante creación");
        }

        LOGGER.info("Enpoint POST /libro subir libro a BBDD");
        libro = libroService.create(libro);

        return libro;
    }

    @PutMapping("/{id}")
    public Libro update(@PathVariable Long id, @RequestBody Libro libroActualizado) {
        LOGGER.info("Endpoint PUT /libro/{} actualizar libro en BBDD", id);
        return libroService.update(id, libroActualizado);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

        LOGGER.info("Enpoint DELETE /libro/id eliminar libro por id");

        libroService.delete(id);
    }

}
