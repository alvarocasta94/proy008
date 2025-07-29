package es.cic.curso2025.proy008.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic.curso2025.proy008.model.Editorial;
import es.cic.curso2025.proy008.model.Libro;
import es.cic.curso2025.proy008.repository.EditorialRepository;
import es.cic.curso2025.proy008.repository.LibroRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class EditorialControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EditorialRepository editorialRepository;

    @AfterEach
    void limpiarBaseDeDatos() {
        editorialRepository.deleteAll();
    }

    @Test
    void testCreate() throws Exception {

        Editorial editorial = new Editorial();
        editorial.setNombre("Espasa");
        editorial.setAnio_fundacion(1978);
        editorial.setDireccion("Calle Charo Charez, 2");

        String editorialJson = objectMapper.writeValueAsString(editorial);

        mockMvc.perform(post("/editorial")
                .contentType("application/json")
                .content(editorialJson))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String respuesta = result.getResponse().getContentAsString();
                    Editorial registroCreado = objectMapper.readValue(respuesta, Editorial.class);
                    assertTrue(registroCreado.getId() > 0, "El valor debe ser mayor que 0");

                    Optional<Editorial> registroRealmenteCreado = editorialRepository
                            .findById(registroCreado.getId());
                    assertTrue(registroRealmenteCreado.isPresent());

                });

    }

    @Test
    void testCreateIntentandoModificacion() throws Exception {

        Editorial editorial = new Editorial();
        editorial.setId(1L);
        editorial.setNombre("VOX");
        editorial.setAnio_fundacion(1982);
        editorial.setDireccion("Calle Charca Charquez, 3");;

        String editorialJson = objectMapper.writeValueAsString(editorial);

        mockMvc.perform(post("/editorial")
                .contentType("application/json")
                .content(editorialJson))
                .andExpect(status().isBadRequest());

    }

    @Test
    void testGet() throws Exception {
        // 1. Crear la editorial
        Editorial editorial = new Editorial();
        editorial.setNombre("Salamandra");
        editorial.setAnio_fundacion(1994);
        editorial.setDireccion("Calle Pepe Pepez, 5");

        // 2. Guardar la editorial en la BD
        editorial = editorialRepository.save(editorial);

        // 3. Simular la solicitud get
        // 3.1. Realizar la solicitud HTTP GET
        mockMvc.perform(get("/editorial/" + editorial.getId())
                .contentType("application/json"))
                // Validar el estado HTTP
                .andExpect(status().isOk())
                // Validar el contenido del JSON
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    Editorial recibido = objectMapper.readValue(json, Editorial.class);
                    assertEquals("Salamandra", recibido.getNombre());
                    assertEquals(1994, recibido.getAnio_fundacion());
                    assertEquals("Calle Pepe Pepez, 5", recibido.getDireccion());
                });
    }

    @Test
    void testGetAll() throws Exception {
        // Prepara datos
        Editorial editorial1 = new Editorial();
        editorial1.setNombre("Salamandra");
        editorial1.setAnio_fundacion(1994);
        editorial1.setDireccion("Calle Pepe Pepez, 5");

        Editorial editorial2 = new Editorial();
        editorial2.setNombre("VOX");
        editorial2.setAnio_fundacion(1982);
        editorial2.setDireccion("Calle Charca Charquez, 3");

        editorialRepository.save(editorial1);
        editorialRepository.save(editorial2);

        // Petición GET
        mockMvc.perform(get("/editorial"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    Editorial[] editoriales = objectMapper.readValue(json, Editorial[].class);
                    assertEquals(2, editoriales.length);
                });
    }

    @Test
    void testUpdate() throws Exception {
        // 1. Guardar editorial inicial
        Editorial editorial = new Editorial();
        editorial.setNombre("Salamandra");
        editorial.setAnio_fundacion(1994);
        editorial.setDireccion("Calle Pepe Pepez, 5");
        editorial = editorialRepository.save(editorial);

        // 2. Crear editorial actualizado
        Editorial editorialActualizado = new Editorial();
        editorialActualizado.setNombre("Máquina de vapor");
        editorialActualizado.setAnio_fundacion(1998);
        editorialActualizado.setDireccion("Calle Pin y Pon, 7");
        String jsonActualizado = objectMapper.writeValueAsString(editorialActualizado);

        // 3. Hacer PUT
        mockMvc.perform(put("/editorial/" + editorial.getId())
                .contentType("application/json")
                .content(jsonActualizado))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    Editorial recibido = objectMapper.readValue(json, Editorial.class);
                    assertEquals("Máquina de vapor", recibido.getNombre());
                });
    }




    @Test
    void testDelete() throws Exception {
        // 1. Crear y guardar una editorial
        Editorial editorial = new Editorial();
        editorial.setNombre("Máquina de vapor");
        editorial.setAnio_fundacion(1998);
        editorial.setDireccion("Calle Pin y Pon, 7");
        

        editorial = editorialRepository.save(editorial);

        Long id = editorial.getId();

        // 2. Realizar la solicitud DELETE
        mockMvc.perform(delete("/editorial/" + id))
                .andExpect(status().isOk());

        // 3. Verificar que ya no existe en la base de datos
        Optional<Editorial> eliminado = editorialRepository.findById(id);
        assertTrue(eliminado.isEmpty()); // Ya no debería estar presente
    }

}
