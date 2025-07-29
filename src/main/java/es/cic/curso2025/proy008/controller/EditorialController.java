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

import es.cic.curso2025.proy008.model.Editorial;
import es.cic.curso2025.proy008.model.Libro;
import es.cic.curso2025.proy008.service.EditorialService;
import es.cic.curso2025.proy008.service.LibroService;

@RestController
@RequestMapping("/editorial")
public class EditorialController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EditorialController.class);

    @Autowired
    private LibroService libroService;

    @Autowired
    private EditorialService editorialService;

    @GetMapping("/{id}")
    public Optional<Editorial> get(@PathVariable Long id) {
        LOGGER.info(String.format("Buscando la editorial con id %d", id));
        return editorialService.get(id);
    }

    @GetMapping
    public List<Editorial> get() {
        LOGGER.info("Buscando todas las editoriales");
        return editorialService.get();
    }

    @PostMapping
    public Editorial create(@RequestBody Editorial editorial) {
        if (editorial.getId() != null) {
            throw new ModificacionSecurityException("Intento de modificación en el create");
        }
        LOGGER.info("Creando una editorial");
        return editorialService.create(editorial);
    }

    @PostMapping("/libro")
    public Libro create(@RequestBody Libro libro) {
        Libro libroCreado = libroService.create(libro);

        return libroCreado;
    }

    @PutMapping("/{id}")
    public Editorial update(@PathVariable Long id, @RequestBody Editorial editorialActualizada) {
        LOGGER.info("Endpoint PUT /editorial/{} actualizar editorial en BBDD", id);
        return editorialService.update(id, editorialActualizada);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        LOGGER.info("Enpoint DELETE /editorial/id eliminar editorial por id");
        editorialService.delete(id);
    }
}
