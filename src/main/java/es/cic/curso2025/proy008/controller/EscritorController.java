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

import es.cic.curso2025.proy008.model.Escritor;
import es.cic.curso2025.proy008.service.EscritorServices;

@RestController
@RequestMapping("/Escritor")
public class EscritorController {


    @Autowired
    private EscritorServices escritorServices;

    @PostMapping
    public Escritor create(@RequestBody Escritor escritor){
        if(escritor.getId() != null){
            
            throw new EscritorIdNotNullException("No puedes pasarme un id antes de crear un escritor");
        }
        return escritorServices.create(escritor);
    }

    
    @GetMapping("/{id}")
    public Escritor buscarPorId(@PathVariable Long id){
  
        return escritorServices.get(id);
    }

    @DeleteMapping("/{id}")
    void borrarUna(@PathVariable Long id){
         escritorServices.delete(id);
    }


    @PutMapping("/{id}")
    public Escritor actualizarUna(@PathVariable Long id ,@RequestBody Escritor escritor){

        if (escritor.getId() == null || !escritor.getId().equals(id)){
            throw new EscritorIdNotNullException("El id del cuerpo no coincide con el de la URL");
        }
        // Obtengo la id por la URL para que se establezca en el objeto
        escritor.setId(id);
        return escritorServices.update(escritor);
    }


}
