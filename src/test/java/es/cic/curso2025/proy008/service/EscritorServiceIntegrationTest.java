package es.cic.curso2025.proy008.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import es.cic.curso2025.proy008.model.Escritor;
import es.cic.curso2025.proy008.model.Libro;
import es.cic.curso2025.proy008.repository.EscritorRepository;
import es.cic.curso2025.proy008.repository.EscritorRepository;

@SpringBootTest
public class EscritorServiceIntegrationTest {

    @Autowired
    EscritorServices escritorServices;

    @Autowired
    EscritorRepository escritorRepository;

    @AfterEach
    void limpiarBaseDeDatos() {
        escritorRepository.deleteAll();
    }

    @Test
    void testCreate() {

        Escritor escritor = new Escritor();
        escritor.setCantidadLibros(2);
        escritor.setEdad(20);
        escritor.setNombre("Lucas");
        Escritor escritorGuardado = escritorServices.create(escritor);

        assertNotNull(escritorGuardado.getId(), "El ID no debe ser null tras guardar");
        assertEquals(2, escritorGuardado.getCantidadLibros());
        assertEquals(20, escritorGuardado.getEdad());
        assertEquals("Lucas", escritorGuardado.getNombre());


        Optional<Escritor> desdeBD = escritorRepository.findById(escritorGuardado.getId());
        assertTrue(desdeBD.isPresent(), "El libro debe existir en la BBDD");
    }

    @Test
    void testGet() {

        Escritor escritor = new Escritor();
        escritor.setCantidadLibros(2);
        escritor.setEdad(20);
        escritor.setNombre("Lucas");

        Escritor escritorGuardado = escritorServices.create(escritor);

        Escritor escritor_Obtenido = escritorServices.get(escritorGuardado.getId());

        assertNotNull(escritor_Obtenido.getId(), "El ID no debe ser null tras guardar");
        assertEquals(2, escritor_Obtenido.getCantidadLibros());
        assertEquals(20, escritor_Obtenido.getEdad());
        assertEquals("Lucas", escritor_Obtenido.getNombre());
    }


    
    @Test
    void testUpdate() {

        Escritor escritor = new Escritor();
        escritor.setCantidadLibros(2);
        escritor.setEdad(20);
        escritor.setNombre("Lucas");

        Escritor escritorGuardado = escritorServices.create(escritor);

        Long idObtenido = escritorGuardado.getId();
        
        Escritor escritorNuevo = new Escritor();
        escritorNuevo.setId(idObtenido);
        escritorNuevo.setCantidadLibros(1);
        escritorNuevo.setEdad(22);
        escritorNuevo.setNombre("Alguien");
        
        
        Escritor escritorActualizado = escritorServices.update(escritorNuevo);

        assertNotNull(escritorActualizado.getId(), "El ID no debe ser null tras guardar");
        assertEquals(1, escritorActualizado.getCantidadLibros());
        assertEquals(22, escritorActualizado.getEdad());
        assertEquals("Alguien", escritorActualizado.getNombre());
    }



    @Test
    void testDelete() {

        Escritor escritor = new Escritor();
        escritor.setCantidadLibros(2);
        escritor.setEdad(20);
        escritor.setNombre("Lucas");

        Escritor escritorGuardado = escritorServices.create(escritor);

        escritorServices.delete(escritorGuardado.getId());
        
        Escritor escritor_eliminado = escritorServices.get(escritorGuardado.getId());

        assertNull(escritor_eliminado);
    }
    
}
