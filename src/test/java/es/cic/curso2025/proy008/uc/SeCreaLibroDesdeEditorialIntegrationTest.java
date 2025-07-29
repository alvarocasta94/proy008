import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic.curso2025.proy008.model.Editorial;
import es.cic.curso2025.proy008.model.Libro;

@SpringBootTest
@AutoConfigureMockMvc
public class SeCreaLibroDesdeEditorialIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testEstablecerRelacionLibroEditorial() throws Exception {
        /**
         * Creo primero una editorial
         */
       
        Editorial editorial = new Editorial();
        editorial.setNombre("Salamandra");
        editorial.setAnio_fundacion(1994);
        editorial.setDireccion("Calle Pepe Pepez, 5");

        Libro libroTest = new Libro();
        libroTest.setAutor("Cervantes");
        libroTest.setNombre("El Quijote");
        libroTest.setDescripcion("Narra las aventuras de cierto hidalgo manchego");
        libroTest.setFechaPublicacion(LocalDate.parse("2023-07-22"));

        libroTest.setEditorial(editorial);
        // persona.setPerro(perroTest);


        //convertimos el objeto de tipo Libro en json con ObjectMapper
        String libroACrearJson = objectMapper.writeValueAsString(libroTest);
        
        
        //con MockMvc simulamos la peticion HTTP para crear una editorial
        MvcResult mvcResult = mockMvc.perform(post("/editorial/libro")
        .contentType("application/json")
        .content(libroACrearJson))
        .andExpect(status().isOk())
        .andExpect( editorialResult ->{
            assertNotNull(
                objectMapper.readValue(
                    editorialResult.getResponse().getContentAsString(), Libro.class), 
                "Se ha creado la relación entre libro y editorial");
            })
        .andReturn();


        Libro libroCreado = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Libro.class);
        Long id = libroCreado.getId();

        mockMvc.perform(get("/libro/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> {
                    assertEquals(objectMapper.readValue(result.getResponse().getContentAsString(), Libro.class).getId(),
                            id);
                });   
         
                
        libroCreado.getEditorial().setDireccion("Calle sin número");


        String libroJson = objectMapper.writeValueAsString(libroCreado);

        mockMvc.perform(put("/libro")
                .contentType("application/json")
                .content(libroJson))
                .andDo(print())                
                .andExpect(status().isOk());





        mockMvc.perform(delete("/libro/" + id))
                .andDo(print())        
                .andExpect(status().isOk());                
    }
}
