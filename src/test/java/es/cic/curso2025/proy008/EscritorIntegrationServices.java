package es.cic.curso2025.proy008;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.cic.curso2025.proy008.controller.EscritorController;
import es.cic.curso2025.proy008.model.Escritor;

// Imports MockMvc
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class EscritorIntegrationServices {


    @Autowired
    private EscritorController escritorController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper ObjectMapper;
     

    @Test
    void testCreate() throws Exception{
        Escritor escritor = new Escritor();
        escritor.setCantidadLibros(70);
        escritor.setEdad(50);
        escritor.setNombre("Kafka");

        String escritorJson = ObjectMapper.writeValueAsString(escritor);

        mockMvc.perform(post("/Escritor")
                        .contentType("application/json")
                        .content(escritorJson))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    assertTrue(     ObjectMapper.readValue(result.getResponse().getContentAsString(), Escritor.class).getId()
                        > 0, "El id no puede ser cero"); 
                   
                });
 
    }


    @Test
    void testBuscarPorId() throws Exception {
        Escritor escritor = new Escritor();
        escritor.setCantidadLibros(250);
        escritor.setEdad(125);
        escritor.setNombre("Isaac Asimov");

        String escritorJson = ObjectMapper.writeValueAsString(escritor);

        // Primero creamos un resultado para poder buscarlo después
        MvcResult result =  mockMvc.perform(post("/Escritor")
                                    .contentType("application/json")
                                    .content(escritorJson))
                            .andExpect(status().isOk())
                            .andReturn();  
        // Obtenemos el id con ObjectMapper. El get response recoge el cuerpo del mock y lo convierte a string, luego lo pasamos de valor JSON a  clase y obtenemos el id
        Long idObtenido = ObjectMapper.readValue(result.getResponse().getContentAsString(), Escritor.class).getId();
        
        // Con el método get devolvemos un segundo resultado con el idObtenido
        MvcResult result2 = mockMvc.perform(get("/Escritor/" + idObtenido))
                            .andExpect(status().isOk())
                            .andReturn();
        // Lo pasamos a valor Objeto
        Escritor escritorEncontrado = ObjectMapper.readValue(result2.getResponse().getContentAsString(),Escritor.class);
        // Verificamos
        assertEquals(escritorEncontrado.getNombre(), escritor.getNombre());

    }

    @Test
    void testEliminar() throws Exception {
        
        Escritor escritor = new Escritor();
        escritor.setCantidadLibros(25);
        escritor.setEdad(20);
        escritor.setNombre("Dostoievski");

        String escritorJson = ObjectMapper.writeValueAsString(escritor);

        MvcResult result = mockMvc.perform(post("/Escritor")
                        .contentType("application/json")
                        .content(escritorJson))
                        .andExpect(status().isOk())
                        .andReturn();

        Long idObtenido = ObjectMapper.readValue(result.getResponse().getContentAsString(), Escritor.class).getId();

        // Borramos
        mockMvc.perform(delete("/Escritor/" + idObtenido))
                            .andExpect(status().isOk())
                            .andReturn();

        // Verificamos
        assertEquals(escritor.getId(), null);
        
    }


    @Test
    void testActualizar() throws Exception {
               
        Escritor escritor = new Escritor();
        escritor.setCantidadLibros(25);
        escritor.setEdad(20);
        escritor.setNombre("Dostoievski");

        String escritorJson = ObjectMapper.writeValueAsString(escritor);

        MvcResult result = mockMvc.perform(post("/Escritor")
                            .contentType("application/json")
                            .content(escritorJson))   
                            .andExpect(status().isOk())
                            .andReturn();

        Long idObtenido = ObjectMapper.readValue(result.getResponse().getContentAsString(), Escritor.class).getId();

        // Creo otro objeto que va a ser el que obtiene el id del primero para actualizar los cambios
        Escritor escritor2 = new Escritor();
        escritor2.setId(idObtenido);
        escritor2.setCantidadLibros(27);
        escritor2.setEdad(22);
        escritor2.setNombre("Héctor Solana Díez");

        String escritor2Json = ObjectMapper.writeValueAsString(escritor2);

        // Utilizo el jsonPath para verificar los resultados actualizados
        MvcResult result2 = mockMvc.perform(put("/Escritor/" + idObtenido)
                            .contentType("application/json")
                            .content(escritor2Json))
                            .andExpect(status().isOk())
                            .andExpect(jsonPath("$.id").value(idObtenido)) 
                            .andExpect(jsonPath("$.cantidadLibros").value(27)) 
                            .andExpect(jsonPath("$.edad").value(22)) 
                            .andExpect(jsonPath("$.nombre").value("Héctor Solana Díez"))
                            .andReturn();

        Escritor escritorActualizado = ObjectMapper.readValue(result2.getResponse().getContentAsString(),Escritor.class);

        // Verificación
        assertEquals(idObtenido, escritorActualizado.getId(),"El id no coincide");
        assertEquals(27, escritorActualizado.getCantidadLibros(),"La cantidad de libros no coincide");
        assertEquals(22, escritorActualizado.getEdad(),"La edad no coincide");
        assertEquals("Héctor Solana Díez", escritorActualizado.getNombre(),"El nombre no coincide");
    }



}
