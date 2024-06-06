package com.aluraGenesysSpring.screenmatch.services;
import com.aluraGenesysSpring.screenmatch.dto.SerieDTO;
import com.aluraGenesysSpring.screenmatch.repository.iSerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SerieService {

    @Autowired
    private iSerieRepository serieRpository;

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
