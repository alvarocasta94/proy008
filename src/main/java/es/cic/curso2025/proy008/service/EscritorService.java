
package es.cic.curso2025.proy008.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.cic.curso2025.proy008.model.Escritor;
import es.cic.curso2025.proy008.repository.EscritorRepository;
import jakarta.transaction.Transactional;

@Service
public class EscritorService {


    @Autowired
    private EscritorRepository escritorRepository;

    // Métodos
    public Escritor create(Escritor escritor){
        return escritorRepository.save(escritor);
    }
    @Transactional
    public Escritor get(Long id){

        Optional<Escritor> resultado = escritorRepository.findById(id);

        return resultado.orElse(null);
    }
    @Transactional
    public List<Escritor> getAll()
    {
        return escritorRepository.findAll();
        
    }
    @Transactional
    public void  delete(Long id){
        escritorRepository.deleteById(id);
    }
    @Transactional
    public Escritor update(Escritor escritor){
        if(escritor.getId() == null){
            throw new EscritorServiceIdNotNullException("No tienes ningun id en la BD");
        }
        return escritorRepository.save(escritor);
    }
    
}
