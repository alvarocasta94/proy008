package es.cic.curso2025.proy008.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.cic.curso2025.proy008.model.Escritor;
import es.cic.curso2025.proy008.repository.EscritorRepository;
import es.cic.curso2025.proy008.service.EscritorServices;

@RestController
@RequestMapping("/Escritor")
public class EscritorController {

    @Autowired
    private EscritorServices escritorServices;

@PostMapping
public Escritor crear(@RequestBody Escritor escritor){
    
    return escritorServices.create(escritor);
}

// @GetMapping("/{id}")



}
