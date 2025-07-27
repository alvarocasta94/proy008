package es.cic.curso2025.proy008.uc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import es.cic.curso2025.proy008.model.Escritor;
import es.cic.curso2025.proy008.model.Premio;


@SpringBootTest
@AutoConfigureMockMvc
public class RelacionEscritorPremioTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void relacionEscritorPremioTest() throws Exception {

        // Creamos el premio con toda su información

        Escritor escritorTest = new Escritor();
        escritorTest.setCantidadLibros(2);
        escritorTest.setEdad(22);
        escritorTest.setNombre("Héctor Solana Díez");

        Premio premio = new Premio();
        premio.setAnio(2018);
        premio.setCategoria("Sci-fi");
        premio.setLibroPremiado("¿Sueñan los androides con ovjeas eléctricas?");
        premio.setNombrePremio("Nébula");

        escritorTest.setPremio(premio);


        String escritorjson = objectMapper.writeValueAsString(escritorTest);


        MvcResult result = mockMvc.perform(post("/Escritor")
                            .contentType("application/json")
                             .content(escritorjson))                           
                            .andExpect(status().isOk())
                            .andExpect(resultPremio -> {
                            assertNotNull(objectMapper.readValue(resultPremio.getResponse().getContentAsString(), Premio.class));                                
                            })
                            .andReturn();


        Escritor escritorCreado = objectMapper.readValue(result.getResponse().getContentAsString(), Escritor.class);
        Long idCreado = escritorCreado.getId();


        mockMvc.perform(get("/Escritor/" + idCreado))
                            .andDo(print())
                            .andExpect(status().isOk())
                       .andExpect(resultado -> {
                        Escritor escritorLeido = objectMapper.readValue(resultado.getResponse().getContentAsString(), Escritor.class);
                        assertEquals(escritorLeido.getId(), idCreado);
                    });
                    
                        

        escritorCreado.getPremio().setAnio(0);

        String escritorJson = objectMapper.writeValueAsString(escritorCreado);

        MvcResult putResult = mockMvc.perform(put("/Escritor/" + idCreado)
                .contentType("application/json")
                .content(escritorJson))
                .andExpect(status().isOk())
                .andReturn();

        Escritor actualizado = objectMapper.readValue(putResult.getResponse().getContentAsString(), Escritor.class);
        assertEquals(0, actualizado.getPremio().getAnio());
        
        mockMvc.perform(delete("/Escritor/" + idCreado))
                            .andDo(print())
                            .andExpect(status().isOk());

                    



        }




}
