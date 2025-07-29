package es.cic.curso2025.proy008.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import es.cic.curso2025.proy008.model.Editorial;
import es.cic.curso2025.proy008.repository.EditorialRepository;
import es.cic.curso2025.proy008.repository.LibroRepository;

@SpringBootTest
public class EditorialServiceIntegrationTest {
    @Autowired
    private EditorialService editorialService;

    @Autowired
    private EditorialRepository editorialRepository;

    @AfterEach
    void limpiarBaseDeDatos() {
        editorialRepository.deleteAll();
    }

    @Test
    void testCreate() {

        Editorial editorial = new Editorial();
        editorial.setNombre("Espasa");
        editorial.setAnio_fundacion(1978);
        editorial.setDireccion("Calle Charo Charez, 2");

        Editorial editorialGuardada = editorialService.create(editorial);

        assertNotNull(editorialGuardada.getId(), "El ID no debe ser null tras guardar");
        assertEquals("Espasa", editorialGuardada.getNombre());
        assertEquals(1978, editorialGuardada.getAnio_fundacion());
        assertEquals("Calle Charo Charez, 2", editorialGuardada.getDireccion());

        Optional<Editorial> desdeBD = editorialRepository.findById(editorialGuardada.getId());
        assertTrue(desdeBD.isPresent(), "La editorial debe existir en la BBDD");
    }

    @Test
    void testGet() {

        Editorial editorial = new Editorial();
        editorial.setNombre("VOX");
        editorial.setAnio_fundacion(1964);
        editorial.setDireccion("Calle Charca Charquez, 3");

        Editorial editorialGuardada = editorialService.create(editorial);

        Optional<Editorial> editorial_Obtenida = editorialService.get(editorialGuardada.getId());

        assertTrue(editorial_Obtenida.isPresent(), "La editorial debería estar presente");
        assertEquals("VOX", editorial_Obtenida.get().getNombre());
        assertEquals(1964, editorial_Obtenida.get().getAnio_fundacion());
        assertEquals("Calle Charca Charquez, 3", editorial_Obtenida.get().getDireccion());
    }

    @Test
    void testGetAll() {
        Editorial editorial1 = new Editorial();
        editorial1.setNombre("Espasa");
        Editorial editorial2 = new Editorial();
        editorial2.setNombre("VOX");

        editorialService.create(editorial1);
        editorialService.create(editorial2);

        List<Editorial> lista = editorialService.get();

        assertEquals(2, lista.size());
    }

    @Test
    void testUpdate() {
        Editorial editorial = new Editorial();
        editorial.setNombre("Salamandra");
        editorial.setAnio_fundacion(1994);
        editorial.setDireccion("Calle Gloria Fuertes, 8");
        Editorial editorialGuardada = editorialService.create(editorial);

        Editorial editorialActualizada = new Editorial();
        editorialActualizada.setNombre("Círculo de Lectores");
        editorialActualizada.setAnio_fundacion(1982);
        editorialActualizada.setDireccion("Calle Pepe Pepez, 11");

        Editorial resultado = editorialService.update(editorialGuardada.getId(), editorialActualizada);

        assertEquals("Círculo de Lectores", resultado.getNombre());
        assertEquals(1982, resultado.getAnio_fundacion());
        assertEquals("Calle Pepe Pepez, 11", resultado.getDireccion());
    }

    @Test
    void testUpdateNoExiste() {
        Editorial editorialActualizada = new Editorial();
        editorialActualizada.setNombre("No existe");

        assertThrows(EntidadNoEncontradaException.class, () -> {
            editorialService.update(999L, editorialActualizada);
        });
    }

    @Test
    void testDelete() {
        Editorial editorial = new Editorial();
        editorial.setNombre("Anaya");
        Editorial editorialGuardada = editorialService.create(editorial);

        editorialService.delete(editorialGuardada.getId());

        Optional<Editorial> eliminado = editorialService.get(editorialGuardada.getId());

        assertFalse(eliminado.isPresent());
    }

    @Test
    void testDeleteNoExiste() {
        assertThrows(EntidadNoEncontradaException.class, () -> {
            editorialService.delete(999L);
        });
    }

}
