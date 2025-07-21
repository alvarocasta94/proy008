package es.cic.curso2025.proy008;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import es.cic.curso2025.proy008.controller.EscritorController;
import es.cic.curso2025.proy008.repository.EscritorRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class EscritorIntegrationServices {


    @Autowired
    private EscritorController escritorController;

    @Autowired
    private MockMvc mockMvc;
    // @Autowired
    // private ObjectMapper ObjectMapper;
     



}
