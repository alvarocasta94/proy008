package es.cic.curso2025.proy008.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.time.LocalDate;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic.curso2025.proy008.model.Libro;
import es.cic.curso2025.proy008.repository.LibroRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class LibroControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LibroRepository libroRepository;

    @AfterEach
    void limpiarBaseDeDatos() {
        libroRepository.deleteAll();
    }

    @Test
    void testCreate() throws Exception {

        Libro libro = new Libro();
        libro.setAutor("Cervantes");
        libro.setNombre("El Quijote");
        libro.setDescripcion("Narra las aventuras de cierto hidalgo manchego");
        libro.setFechaPublicacion(LocalDate.parse("2023-07-22"));

        String libroJson = objectMapper.writeValueAsString(libro);

        mockMvc.perform(post("/libro")
                .contentType("application/json")
                .content(libroJson))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String respuesta = result.getResponse().getContentAsString();
                    Libro registroCreado = objectMapper.readValue(respuesta, Libro.class);
                    assertTrue(registroCreado.getId() > 0, "El valor debe ser mayor que 0");

                    Optional<Libro> registroRealmenteCreado = libroRepository
                            .findById(registroCreado.getId());
                    assertTrue(registroRealmenteCreado.isPresent());

                });

    }

    @Test
    void testCreateIntentandoModificacion() throws Exception {

        Libro libro = new Libro();
        libro.setId((long) 3);
        libro.setAutor("J.K. Rowling");
        libro.setNombre("Harry Potter y la piedra filosofal");
        libro.setDescripcion("Narra las aventuras de un joven mago");
        libro.setFechaPublicacion(LocalDate.parse("2023-07-22"));

        String libroJson = objectMapper.writeValueAsString(libro);

        mockMvc.perform(post("/libro")
                .contentType("application/json")
                .content(libroJson))
                .andExpect(status().isBadRequest());

    }

    @Test
    void testGet() throws Exception {
        // 1. Crear el libro
        Libro libro = new Libro();
        libro.setAutor("Anónimo");
        libro.setNombre("El Lazarillo de Tormes");
        libro.setDescripcion("Narra las aventuras de cierto pícaro español");
        libro.setFechaPublicacion(LocalDate.parse("2023-07-22"));

        // 2. Guardar el libro en la BD
        libro = libroRepository.save(libro);

        // 3. Simular la solicitud get
        // 3.1. Realizar la solicitud HTTP GET
        mockMvc.perform(get("/libro/" + libro.getId())
                .contentType("application/json"))
                // Validar el estado HTTP
                .andExpect(status().isOk())
                // Validar el contenido del JSON
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    Libro recibido = objectMapper.readValue(json, Libro.class);
                    assertEquals("Anónimo", recibido.getAutor());
                    assertEquals("El Lazarillo de Tormes", recibido.getNombre());
                    assertEquals("Narra las aventuras de cierto pícaro español", recibido.getDescripcion());
                    assertEquals(LocalDate.parse("2023-07-22"), recibido.getFechaPublicacion());
                });
    }

    @Test
    void testGetAll() throws Exception {
        // Prepara datos
        Libro libro1 = new Libro();
        libro1.setAutor("Anónimo");
        libro1.setNombre("El Lazarillo de Tormes");
        libro1.setDescripcion("Narra las aventuras de cierto pícaro español");
        libro1.setFechaPublicacion(LocalDate.parse("2023-07-22"));

        Libro libro2 = new Libro();
        libro2.setAutor("Isabel Allende");
        libro2.setNombre("La ciudad de las bestias");
        libro2.setDescripcion("Narra las aventuras de un par de chavales y la tía de uno de ellos");
        libro2.setFechaPublicacion(LocalDate.parse("2024-07-22"));

        libroRepository.save(libro1);
        libroRepository.save(libro2);

        // Petición GET
        mockMvc.perform(get("/libro"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    Libro[] libros = objectMapper.readValue(json, Libro[].class);
                    assertEquals(2, libros.length);
                });
    }

    @Test
    void testUpdate() throws Exception {
        // 1. Guardar libro inicial
        Libro libro = new Libro();
        libro.setAutor("Anónimo");
        libro.setNombre("El Lazarillo de Tormes");
        libro.setDescripcion("Narra las aventuras de cierto pícaro español");
        libro.setFechaPublicacion(LocalDate.parse("2023-07-22"));
        libro = libroRepository.save(libro);

        // 2. Crear libro actualizado
        Libro libroActualizado = new Libro();
        libroActualizado.setAutor("Isabel Allende");
        libroActualizado.setNombre("La ciudad de las bestias");
        libroActualizado.setDescripcion("Narra las aventuras de un par de chavales y la tía de uno de ellos");
        libroActualizado.setFechaPublicacion(LocalDate.parse("2024-07-22"));
        String jsonActualizado = objectMapper.writeValueAsString(libroActualizado);

        // 3. Hacer PUT
        mockMvc.perform(put("/libro/" + libro.getId())
                .contentType("application/json")
                .content(jsonActualizado))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    Libro recibido = objectMapper.readValue(json, Libro.class);
                    assertEquals("Isabel Allende", recibido.getAutor());
                });
    }




    @Test
    void testDelete() throws Exception {
        // 1. Crear y guardar un libro
        Libro libro = new Libro();
        libro.setAutor("Isabel Allende");
        libro.setNombre("La ciudad de las bestias");
        libro.setDescripcion("Narra las aventuras de un par de chavales y la tía de uno de ellos");
        libro.setFechaPublicacion(LocalDate.parse("2024-07-22"));
        

        libro = libroRepository.save(libro);

        Long id = libro.getId();

        // 2. Realizar la solicitud DELETE
        mockMvc.perform(delete("/libro/" + id))
                .andExpect(status().isOk());

        // 3. Verificar que ya no existe en la base de datos
        Optional<Libro> eliminado = libroRepository.findById(id);
        assertTrue(eliminado.isEmpty()); // Ya no debería estar presente
    }

}
