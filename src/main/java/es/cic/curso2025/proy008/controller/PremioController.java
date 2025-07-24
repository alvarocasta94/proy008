package es.cic.curso2025.proy008.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.cic.curso2025.proy008.model.Premio;
import es.cic.curso2025.proy008.service.PremioService;

@RestController
@RequestMapping("/Premio")
public class PremioController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PremioController.class);

    @Autowired
    PremioService premioService;
}
