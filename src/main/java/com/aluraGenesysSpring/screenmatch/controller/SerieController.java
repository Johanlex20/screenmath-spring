package com.aluraGenesysSpring.screenmatch.controller;
import com.aluraGenesysSpring.screenmatch.dto.SerieDTO;
import com.aluraGenesysSpring.screenmatch.services.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
public class SerieController {

    @Autowired
    private SerieService serieService;

    @GetMapping(value = "/series")
    public List<SerieDTO> obtenerTodasLasSeries(){
        return serieService.obtenerTodasLasSeries();
    }

    @GetMapping(value = "/series/top5")
    public List<SerieDTO> obtenerTop5(){
        return serieService.obtenerTop5();
    }


}
