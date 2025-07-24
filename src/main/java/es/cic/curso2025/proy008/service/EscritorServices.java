
package es.cic.curso2025.proy008.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.cic.curso2025.proy008.model.Escritor;
import es.cic.curso2025.proy008.repository.EscritorRepository;

@Service
public class EscritorServices {


    @Autowired
    private EscritorRepository escritorRepository;

    // Métodos
    public Escritor create(Escritor escritor){
        return escritorRepository.save(escritor);
    }

    public Escritor get(Long id){

        Optional<Escritor> resultado = escritorRepository.findById(id);

        return resultado.orElse(null);
    }

    public List<Escritor> getAll()
    {
        return escritorRepository.findAll();
        
    }

    public void  delete(Long id){
        escritorRepository.deleteById(id);
    }

    public Escritor update(Escritor escritor){
        if(escritor.getId() == null){
            throw new EscritorServicesIdNotNullException("No tienes ningun id en la BD");
        }
        return escritorRepository.save(escritor);
    }
    
}
