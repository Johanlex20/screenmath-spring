package com.aluraGenesysSpring.screenmatch.controller;
import com.aluraGenesysSpring.screenmatch.dto.EpisodioDTO;
import com.aluraGenesysSpring.screenmatch.dto.SerieDTO;
import com.aluraGenesysSpring.screenmatch.services.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping(value = "/series")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @GetMapping
    public List<SerieDTO> obtenerTodasLasSeries(){
        return serieService.obtenerTodasLasSeries();
    }

    @GetMapping(value = "/top5")
    public List<SerieDTO> obtenerTop5(){
        return serieService.obtenerTop5();
    }

    @GetMapping(value = "/lanzamientos")
    public List<SerieDTO> obtenerLanzamientosMasRecientes(){
        return serieService.obtenerLanzamientosMasRecientes();
    }

    @GetMapping(value = "/{id}")
    public SerieDTO obtenerPorId(@PathVariable(value = "id") Long id){
        return serieService.obtenerPorId(id);
    }

    @GetMapping(value = "/{id}/temporadas/todas")
    public List<EpisodioDTO> obtenerTodasLasTemporadas(@PathVariable(value = "id")Long id){
        return serieService.obtenerTodasLasTemporadas(id);
    }
}
