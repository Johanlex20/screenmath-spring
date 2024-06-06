package com.aluraGenesysSpring.screenmatch.controller;
import com.aluraGenesysSpring.screenmatch.dto.SerieDTO;
import com.aluraGenesysSpring.screenmatch.repository.iSerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SerieController {

    @Autowired
    private iSerieRepository serieRpository;

    @GetMapping(value = "/series")
    public List<SerieDTO> obtenerTodasLasSeries(){
        return serieRpository.findAll().stream()
                .map(s -> new SerieDTO(
                        s.getTitulo(),
                        s.getTotalDeTemporadas(),
                        s.getEvaluacion(),
                        s.getPoster(),
                        s.getGenero(),
                        s.getActores(),
                        s.getSinopsis()))
                .collect(Collectors.toList());
    }

}
