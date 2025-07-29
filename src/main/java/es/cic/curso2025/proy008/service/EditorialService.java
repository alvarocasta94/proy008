package es.cic.curso2025.proy008.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso2025.proy008.model.Editorial;
import es.cic.curso2025.proy008.repository.EditorialRepository;

@Service
@Transactional
public class EditorialService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EditorialService.class);

    @Autowired
    private EditorialRepository editorialRepository;

    public Optional<Editorial> get(Long id) {
        LOGGER.info(String.format("Buscando la editorial con id %d", id));
        return editorialRepository.findById(id);
    }

    public List<Editorial> get() {
        LOGGER.info("Buscando todas las editoriales");
        return editorialRepository.findAll();
    }

    public Editorial create(Editorial editorial) {
        LOGGER.info("Creando una editorial");
        return editorialRepository.save(editorial);
    }

    public Editorial update(Long id, Editorial editorialActualizada) {
        LOGGER.info(String.format("Actualizando la editorial con id %d", id));

        Editorial existente = editorialRepository.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("Editorial no encontrada con ID: " + id));

        // Actualizamos solo los campos necesarios (ignoramos el id de
        // editorialActualizada)
        existente.setNombre(editorialActualizada.getNombre());
        existente.setAnio_fundacion(editorialActualizada.getAnio_fundacion());
        existente.setDireccion(editorialActualizada.getDireccion());
        existente.setLibro(editorialActualizada.getLibro());

        return editorialRepository.save(existente);
    }

    public void delete(Long id) {
        LOGGER.info(String.format("Eliminación de la editorial con id %s", id));

        if (!editorialRepository.existsById(id)) {
            throw new EntidadNoEncontradaException("No se puede eliminar: editorial no encontrada con ID: " + id);
        }

        editorialRepository.deleteById(id);
    }
}
