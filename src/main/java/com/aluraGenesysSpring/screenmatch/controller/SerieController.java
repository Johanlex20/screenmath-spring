package com.aluraGenesysSpring.screenmatch.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SerieController {

    @GetMapping(value = "/series")
    public String mostrarMensaje(){
        return "Mensaje";
    }

}
