package es.cic.curso2025.proy008.controller;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;

import es.cic.curso2025.proy008.model.Premio;
import es.cic.curso2025.proy008.service.PremioService;

@RestController
@RequestMapping("/Premio")
public class PremioController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PremioController.class);

    @Autowired
    PremioService premioService;

    @PostMapping
    public Premio crear(@RequestBody Premio premio){

        if(premio.getId() != null){
            
            throw new PremioIdNotNullException("No puedes pasarme un id antes de crear un escritor");
        }
        return premioService.create(premio);
    }

        
    @PostMapping("/premio")
    public Premio create(@RequestBody Premio premio){
        Premio premioCreado = premioService.create(premio);

        return premioCreado;
    }

    @GetMapping("/{id}")
    public Optional<Premio> get(@PathVariable Long id){

        
        Optional<Premio> premio = premioService.get(id);
        
        return premio;
    }

    @GetMapping("")
    public List<Premio> ListarTodos(){
        List<Premio> premio = premioService.getAll();

        return premio;
    }

    @DeleteMapping("/{id}")
    public void borrarPorId(@PathVariable Long id){
        premioService.delete(id);
    }

    @PutMapping("/{id}")
    public Premio  actualizar(@RequestBody Premio premio, Long id){
        premioService.get(id);

        Premio actualizado = premioService.update(premio);
        
        return actualizado;


    }
}
