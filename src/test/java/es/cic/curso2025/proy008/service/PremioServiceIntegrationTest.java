package es.cic.curso2025.proy008.service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import es.cic.curso2025.proy008.model.Premio;
import es.cic.curso2025.proy008.repository.PremioRepository;

@SpringBootTest
public class PremioServiceIntegrationTest {
    
    @Autowired
    PremioService premioService;

    @Autowired
    PremioRepository premioRepository;

    @AfterEach
    void limpiarBaseDeDatos() {
        premioRepository.deleteAll();
    }

   @Test
    void testCreatePremio() {

        Premio premio = new Premio();
        premio.setAnio(2018);
        premio.setCategoria("Sci-fi");
        premio.setLibroPremiado("¿Sueñan los androides con ovjeas eléctricas?");
        premio.setNombrePremio("Nébula");   
         Premio premioGuardado = premioService.create(premio);

        assertNotNull(premioGuardado.getId(), "El ID no debe ser null tras guardar");
        assertEquals(2018, premioGuardado.getAnio());
        assertEquals("Nébula", premioGuardado.getNombrePremio());
        assertEquals("¿Sueñan los androides con ovjeas eléctricas?", premioGuardado.getLibroPremiado());
        assertEquals("Sci-fi", premioGuardado.getCategoria());

        Optional<Premio> desdeBD = premioRepository.findById(premioGuardado.getId());
        assertTrue(desdeBD.isPresent(), "El premio debe existir en la BBDD");
    }

    @Test
    void testGetPremio() {
        Premio premio = new Premio();
        premio.setAnio(2018);
        premio.setCategoria("Sci-fi");
        premio.setLibroPremiado("¿Sueñan los androides con ovjeas eléctricas?");
        premio.setNombrePremio("Nébula");   
        Premio premioGuardado = premioService.create(premio);

        Optional<Premio> premioObtenido = premioService.get(premioGuardado.getId());

        assertTrue(premioObtenido.isPresent(), "El premio debe estar presente");
        assertEquals(2018, premioObtenido.get().getAnio());
        assertEquals("Nébula", premioObtenido.get().getNombrePremio());
        assertEquals("¿Sueñan los androides con ovjeas eléctricas?", premioObtenido.get().getLibroPremiado());
        assertEquals("Sci-fi", premioObtenido.get().getCategoria());
    }

    @Test
    void testGetAllPremios() {
        Premio premio1 = new Premio( 2020, "Premio Nadal", "Reina Roja", "Suspense");
        Premio premio2 = new Premio( 2021, "Premio Alfaguara", "La hija del relojero", "Histórica");

        premioService.create(premio1);
        premioService.create(premio2);

        List<Premio> listaPremios = premioService.getAll();

        assertEquals(2, listaPremios.size(), "Debe haber 2 premios en la lista");
        assertTrue(listaPremios.stream().anyMatch(p -> p.getNombrePremio().equals("Premio Nadal")));
        assertTrue(listaPremios.stream().anyMatch(p -> p.getNombrePremio().equals("Premio Alfaguara")));
    }

    @Test
    void testUpdatePremio() {
        Premio premioOriginal = new Premio( 2022, "Premio Nacional", "Patria", "Narrativa Social");
        Premio premioGuardado = premioService.create(premioOriginal);

        Long idPremio = premioGuardado.getId();

        Premio premioActualizado = new Premio(idPremio, 2023, "Premio Nacional Actualizado", "Todo Vuelve", "Ensayo");

        Premio resultado = premioService.update(premioActualizado);

        assertNotNull(resultado.getId(), "El ID no debe ser null tras actualizar");
        assertEquals(2023, resultado.getAnio());
        assertEquals("Premio Nacional Actualizado", resultado.getNombrePremio());
        assertEquals("Todo Vuelve", resultado.getLibroPremiado());
        assertEquals("Ensayo", resultado.getCategoria());
    }

    @Test
    void testDeletePremio() {
        Premio premio = new Premio(null, 2025, "Premio Biblioteca Breve", "Los Asquerosos", "Contemporáneo");
        Premio premioGuardado = premioService.create(premio);

        premioService.delete(premioGuardado.getId());

        Optional<Premio> premioEliminado = premioService.get(premioGuardado.getId());
        assertTrue(premioEliminado.isEmpty(), "El premio debería haber sido eliminado");
    }
}
