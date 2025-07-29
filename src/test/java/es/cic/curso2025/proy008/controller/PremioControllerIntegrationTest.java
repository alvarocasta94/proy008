package es.cic.curso2025.proy008.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


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


import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic.curso2025.proy008.model.Premio;
import es.cic.curso2025.proy008.repository.PremioRepository;

@AutoConfigureMockMvc
@SpringBootTest
public class PremioControllerIntegrationTest {
    
    

    @Autowired
    PremioRepository premioRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
     
     @AfterEach
    void limpiarBaseDeDatos() {
        premioRepository.deleteAll();
    }


    @Test
    void testCreatePremio() throws Exception {
        Premio premio = new Premio(2025, "Premio Planeta", "El infinito en un junco", "Narrativa");

        String premioJson = objectMapper.writeValueAsString(premio);

        MvcResult result = mockMvc.perform(post("/Premio")
                .contentType("application/json")
                .content(premioJson))
                .andExpect(status().isOk())
                .andReturn();

        Premio premioCreado = objectMapper.readValue(result.getResponse().getContentAsString(), Premio.class);
        assertTrue(premioCreado.getId() > 0, "El id no puede ser cero");
    }



   @Test
    void testListarTodosPremios() throws Exception {
        Premio premio1 = new Premio( 2020, "Premio Nadal", "Reina Roja", "Suspense");
        Premio premio2 = new Premio( 2021, "Premio Alfaguara", "La hija del relojero", "Histórica");

        premioRepository.save(premio1);
        premioRepository.save(premio2);

        mockMvc.perform(get("/Premio"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    Premio[] premios = objectMapper.readValue(json, Premio[].class);
                    assertEquals(2, premios.length);
                });
    }




     @Test
    void testBuscarPremioPorId() throws Exception {
        Premio premio = new Premio( 2024, "Premio Cervantes", "Don Quijote", "Literatura Clásica");

        String premioJson = objectMapper.writeValueAsString(premio);

        MvcResult result = mockMvc.perform(post("/Premio")
                .contentType("application/json")
                .content(premioJson))
                .andExpect(status().isOk())
                .andReturn();

        Long idObtenido = objectMapper.readValue(result.getResponse().getContentAsString(), Premio.class).getId();

        MvcResult result2 = mockMvc.perform(get("/Premio/" + idObtenido))
                .andExpect(status().isOk())
                .andReturn();

        Premio premioEncontrado = objectMapper.readValue(result2.getResponse().getContentAsString(), Premio.class);
        assertEquals(premio.getNombrePremio(), premioEncontrado.getNombrePremio());
    }


    @Test
    void testEliminarPremio() throws Exception {
        Premio premio = new Premio(2025, "Premio Biblioteca Breve", "Los Asquerosos", "Contemporáneo");

        String premioJson = objectMapper.writeValueAsString(premio);

        MvcResult result = mockMvc.perform(post("/Premio")
                .contentType("application/json")
                .content(premioJson))
                .andExpect(status().isOk())
                .andReturn();

        Long idObtenido = objectMapper.readValue(result.getResponse().getContentAsString(), Premio.class).getId();

        // Borramos
        mockMvc.perform(delete("/Premio/" + idObtenido))
                .andExpect(status().isOk())
                .andReturn();

        // Verificamos que el ID original ya no se corresponde con el objeto actual
        assertEquals(premio.getId(), null, "El ID del objeto en memoria no debe estar asignado después del delete");
    }



}
