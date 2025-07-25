package es.cic.curso2025.proy008.controller;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.jayway.jsonpath.JsonPath;

import es.cic.curso2025.proy008.controller.EscritorController;

import es.cic.curso2025.proy008.model.Escritor;
import es.cic.curso2025.proy008.repository.EscritorRepository;

// Imports MockMvc
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class EscritorControllerIntegrationTest {


    @Autowired
    EscritorRepository escritorRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper ObjectMapper;
     
     @AfterEach
    void limpiarBaseDeDatos() {
        escritorRepository.deleteAll();
    }


    @Test
    void testCreate() throws Exception{
        Escritor escritor = new Escritor();
        escritor.setCantidadLibros(70);
        escritor.setEdad(50);
        escritor.setNombre("Kafka");

        String escritorJson = ObjectMapper.writeValueAsString(escritor);

        MvcResult result = mockMvc.perform(post("/Escritor")
                        .contentType("application/json")
                        .content(escritorJson))
                .andExpect(status().isOk())
                .andReturn();
 
                Escritor escritorCreado = ObjectMapper.readValue(result.getResponse().getContentAsString(), Escritor.class);
                assertTrue(escritorCreado.getId() > 0, "El id no puede ser cero");
    }


    @Test
    void testListarTodos() throws Exception{
        Escritor escritor = new Escritor();
        escritor.setCantidadLibros(70);
        escritor.setEdad(50);
        escritor.setNombre("Kafka");


        Escritor escritor2 = new Escritor();
        escritor2.setCantidadLibros(70);
        escritor2.setEdad(50);
        escritor2.setNombre("Kafka");

        // Creo los dos objetos
        escritorRepository.save(escritor);
        escritorRepository.save(escritor2);

         mockMvc.perform(get("/Escritor"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                        String json = result.getResponse().getContentAsString();
                        Escritor escritores[] = ObjectMapper.readValue(json,Escritor[].class );
                        assertEquals(2, escritores.length);

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
        escritor.setId(idObtenido);
        escritor.setCantidadLibros(27);
        escritor.setEdad(22);
        escritor.setNombre("Héctor Solana Díez");

        escritorJson = ObjectMapper.writeValueAsString(escritor); //  Generar nuevo JSON actualizado

        //  Enviar PUT para actualizar
        MvcResult result2 = mockMvc.perform(put("/Escritor/" + idObtenido)
                            .contentType("application/json")
                            .content(escritorJson))
                            .andExpect(status().isOk())
                            .andExpect(jsonPath("$.id").value(idObtenido))
                            .andExpect(jsonPath("$.cantidadLibros").value(27))
                            .andExpect(jsonPath("$.edad").value(22))
                            .andExpect(jsonPath("$.nombre").value("Héctor Solana Díez"))
                            .andReturn();

        Escritor escritorActualizado = ObjectMapper.readValue(
                result2.getResponse().getContentAsString(), Escritor.class);

        // Verificamos
        assertEquals(idObtenido, escritorActualizado.getId(), "El id no coincide");
        assertEquals(27, escritorActualizado.getCantidadLibros(), "La cantidad de libros no coincide");
        assertEquals(22, escritorActualizado.getEdad(), "La edad no coincide");
        assertEquals("Héctor Solana Díez", escritorActualizado.getNombre(), "El nombre no coincide");

    }



}
