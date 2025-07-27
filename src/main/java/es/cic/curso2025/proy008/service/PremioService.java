package es.cic.curso2025.proy008.service;

import java.util.Optional;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.cic.curso2025.proy008.model.Premio;
import es.cic.curso2025.proy008.repository.EscritorRepository;
import es.cic.curso2025.proy008.repository.PremioRepository;
import jakarta.transaction.Transactional;

@Service
public class PremioService {

    @Autowired
    PremioRepository premioRepository;

    @Autowired
    EscritorRepository escritorRepository;


    @Transactional
    public Optional<Premio> get(Long id){

        Optional<Premio> premio = premioRepository.findById(id);
        
        return premio;
    }

    @Transactional
    public List<Premio> getAll(){

        return premioRepository.findAll();
    }
    @Transactional
    public Premio create(Premio premio){

         premioRepository.save(premio);

         return premio;
    }
    @Transactional
    public Premio update(Premio premio){

        premioRepository.save(premio);

        return premio;
    }
    @Transactional
    public void delete(Long id){

        Optional<Premio> premio = premioRepository.findById(id);

        premioRepository.deleteById(id);
    }
}
