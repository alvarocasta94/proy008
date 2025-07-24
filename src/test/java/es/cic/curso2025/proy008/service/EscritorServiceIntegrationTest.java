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
    EscritorService escritorService;

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
        Escritor escritorGuardado = escritorService.create(escritor);

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

        Escritor escritorGuardado = escritorService.create(escritor);

        Escritor escritor_Obtenido = escritorService.get(escritorGuardado.getId());

        assertNotNull(escritor_Obtenido.getId(), "El ID no debe ser null tras guardar");
        assertEquals(2, escritor_Obtenido.getCantidadLibros());
        assertEquals(20, escritor_Obtenido.getEdad());
        assertEquals("Lucas", escritor_Obtenido.getNombre());
    }

    @Test
    void testGetAll(){
        Escritor escritor = new Escritor();
        escritor.setCantidadLibros(70);
        escritor.setEdad(50);
        escritor.setNombre("Kafka");


        Escritor escritor2 = new Escritor();
        escritor2.setCantidadLibros(70);
        escritor2.setEdad(50);
        escritor2.setNombre("Kafka");

        escritorService.create(escritor);
        escritorService.create(escritor2);

        List<Escritor> escritores  = escritorService.getAll();
        assertEquals(2, escritores.size());

    }

    
    @Test
    void testUpdate() {

        Escritor escritor = new Escritor();
        escritor.setCantidadLibros(2);
        escritor.setEdad(20);
        escritor.setNombre("Lucas");

        Escritor escritorGuardado = escritorService.create(escritor);

        Long idObtenido = escritorGuardado.getId();
        
        Escritor escritorNuevo = new Escritor();
        escritorNuevo.setId(idObtenido);
        escritorNuevo.setCantidadLibros(1);
        escritorNuevo.setEdad(22);
        escritorNuevo.setNombre("Alguien");
        
        
        Escritor escritorActualizado = escritorService.update(escritorNuevo);

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

        Escritor escritorGuardado = escritorService.create(escritor);

        escritorService.delete(escritorGuardado.getId());
        
        Escritor escritor_eliminado = escritorService.get(escritorGuardado.getId());

        assertNull(escritor_eliminado);
    }
    
}
