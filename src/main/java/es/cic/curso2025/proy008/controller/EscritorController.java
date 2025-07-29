package es.cic.curso2025.proy008.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.cic.curso2025.proy008.model.Escritor;
import es.cic.curso2025.proy008.model.Premio;
import es.cic.curso2025.proy008.repository.EscritorRepository;
import es.cic.curso2025.proy008.service.EscritorService;
import es.cic.curso2025.proy008.service.PremioService;
import jakarta.persistence.PostUpdate;

@RestController
@RequestMapping("/Escritor")
public class EscritorController {

    private final static Logger LOGGER = LoggerFactory.getLogger(EscritorController.class);

    @Autowired
    private EscritorService escritorService;

    
    @Autowired
    private PremioService premioService;

    @PostMapping
    public Escritor create(@RequestBody Escritor escritor){

        LOGGER.info("Solicitud POST recibida para crear escritor:{}", escritor);


        if(escritor.getId() != null){
            
            throw new EscritorIdNotNullException("No puedes pasarme un id antes de crear un escritor");
        }
        return escritorService.create(escritor);
    }


    @PostMapping("/conPremio")
    public Escritor crearEscritorConPremio(@RequestBody Escritor escritor) {
        if (escritor.getId() != null) {
            throw new EscritorIdNotNullException("El escritor no debe tener ID al crearse");
        }

        Premio premio = escritor.getPremio();
        if (premio != null && premio.getId() != null) {
            throw new PremioIdNotNullException("El premio no debe tener ID al crearse");
        }

        Premio premioCreado = premioService.create(premio);
        escritor.setPremio(premioCreado);

        return escritorService.create(escritor);
    }

        
    @GetMapping("/{id}")
    public Escritor buscarPorId(@PathVariable(required = true) Long id){
  
        return escritorService.get(id);
    }

    @GetMapping("")
    public List<Escritor> ListarTodos(){

        List<Escritor> escritores = escritorService.getAll();
        return escritores;
    }

    @DeleteMapping("/{id}")
    void borrarUna(@PathVariable Long id){
        LOGGER.info("Solicitud delete recibida para el escritor con ID: {}", id);
        escritorService.delete(id);
        LOGGER.info("Escritor con id  eliminado correctamente", id);

    }


    @PutMapping("/{id}")
    public Escritor actualizarUna(@PathVariable Long id ,@RequestBody Escritor escritor){

        LOGGER.info("Solicitud PUT recibida para el escritor con ID: {}", id);

        if (escritor.getId() == null || !escritor.getId().equals(id)) {
            LOGGER.error("ID del cuerpo ({}) no coincide con el de la URL ({})", escritor.getId(), id);
            throw new EscritorIdNotNullException("El id del cuerpo no coincide con el de la URL");
        }

        escritor.setId(id);
        Escritor actualizado = escritorService.update(escritor);
        LOGGER.info("Escritor actualizado correctamente: {}", actualizado);
        return actualizado;

    }


}
