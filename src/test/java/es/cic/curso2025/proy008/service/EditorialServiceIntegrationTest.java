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

import es.cic.curso2025.proy008.model.Libro;
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

        Libro libro = new Libro();
        libro.setNombre("El Principito");
        libro.setAutor("Antoine de Saint-Exupéry");
        libro.setDescripcion("Un cuento filosófico para niños y adultos");
        libro.setFechaPublicacion(LocalDate.parse("2024-07-22"));

        Libro libroGuardado = libroService.create(libro);

        assertNotNull(libroGuardado.getId(), "El ID no debe ser null tras guardar");
        assertEquals("El Principito", libroGuardado.getNombre());
        assertEquals("Antoine de Saint-Exupéry", libroGuardado.getAutor());
        assertEquals("Un cuento filosófico para niños y adultos", libroGuardado.getDescripcion());
        assertEquals(LocalDate.parse("2024-07-22"), libroGuardado.getFechaPublicacion());

        Optional<Libro> desdeBD = libroRepository.findById(libroGuardado.getId());
        assertTrue(desdeBD.isPresent(), "El libro debe existir en la BBDD");
    }

    @Test
    void testGet() {

        Libro libro = new Libro();
        libro.setNombre("Don Quijote de la Mancha");
        libro.setAutor("Miguel de Cervantes");
        libro.setDescripcion("Las aventuras del ingenioso hidalgo");
        libro.setFechaPublicacion(LocalDate.parse("2024-07-22"));

        Libro libroGuardado = libroService.create(libro);

        Optional<Libro> libro_Obtenido = libroService.get(libroGuardado.getId());

        assertTrue(libro_Obtenido.isPresent(), "El libro debería estar presente");
        assertEquals("Don Quijote de la Mancha", libro_Obtenido.get().getNombre());
        assertEquals("Miguel de Cervantes", libro_Obtenido.get().getAutor());
        assertEquals("Las aventuras del ingenioso hidalgo", libro_Obtenido.get().getDescripcion());
        assertEquals(LocalDate.parse("2024-07-22"), libroGuardado.getFechaPublicacion());
    }

    @Test
    void testGetAll() {
        Libro libro1 = new Libro();
        libro1.setNombre("El Quijote");
        Libro libro2 = new Libro();
        libro2.setNombre("El Lazarillo de Tormes");

        libroService.create(libro1);
        libroService.create(libro2);

        List<Libro> lista = libroService.getAll();

        assertEquals(2, lista.size());
    }

    @Test
    void testUpdate() {
        Libro libro = new Libro();
        libro.setNombre("fahrenheit 451");
        libro.setAutor("Ray Bradbury");
        libro.setDescripcion("Movidas varias");
        libro.setFechaPublicacion(LocalDate.parse("2024-07-22"));
        Libro libroGuardado = libroService.create(libro);

        Libro libroActualizado = new Libro();
        libroActualizado.setNombre("La Santa Biblia");
        libroActualizado.setAutor("Dios");
        libroActualizado.setDescripcion("Más movidas");
        libroActualizado.setFechaPublicacion(LocalDate.parse("2025-07-22"));

        Libro resultado = libroService.update(libroGuardado.getId(), libroActualizado);

        assertEquals("La Santa Biblia", resultado.getNombre());
        assertEquals("Dios", resultado.getAutor());
        assertEquals("Más movidas", resultado.getDescripcion());
        assertEquals(LocalDate.parse("2025-07-22"), resultado.getFechaPublicacion());
    }

    @Test
    void testUpdateNoExiste() {
        Libro libroActualizado = new Libro();
        libroActualizado.setNombre("No existe");

        assertThrows(EntidadNoEncontradaException.class, () -> {
            libroService.update(999L, libroActualizado);
        });
    }

    @Test
    void testDelete() {
        Libro libro = new Libro();
        libro.setNombre("Harry Potter y la piedra filosofal");
        Libro libroGuardado = libroService.create(libro);

        libroService.delete(libroGuardado.getId());

        Optional<Libro> eliminado = libroService.get(libroGuardado.getId());

        assertFalse(eliminado.isPresent());
    }

    @Test
    void testDeleteNoExiste() {
        assertThrows(EntidadNoEncontradaException.class, () -> {
            libroService.delete(999L);
        });
    }

}
