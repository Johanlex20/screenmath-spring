package com.aluraGenesysSpring.screenmatch.services;
import com.aluraGenesysSpring.screenmatch.dto.EpisodioDTO;
import com.aluraGenesysSpring.screenmatch.dto.SerieDTO;
import com.aluraGenesysSpring.screenmatch.models.Episodio;
import com.aluraGenesysSpring.screenmatch.models.Genero;
import com.aluraGenesysSpring.screenmatch.models.Serie;
import com.aluraGenesysSpring.screenmatch.repository.iSerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {

    @Autowired
    private iSerieRepository serieRpository;

    public List<SerieDTO> obtenerTodasLasSeries(){
        return converierteDatos(serieRpository.findAll());
    }

    public List<SerieDTO> obtenerTop5() {
        return converierteDatos(serieRpository.findTop5ByOrderByEvaluacionDesc());
    }

    public List<SerieDTO> obtenerLanzamientosMasRecientes(){
        return converierteDatos(serieRpository.lanzamientosMaRecientes());
    }

    public SerieDTO obtenerPorId(Long id) {
        Optional<Serie> serie = serieRpository.findById(id);
        if (serie.isPresent()){
            Serie s = serie.get();
            return new SerieDTO(
                    s.getId(),
                    s.getTitulo(),
                    s.getTotalDeTemporadas(),
                    s.getEvaluacion(),
                    s.getPoster(),
                    s.getGenero(),
                    s.getActores(),
                    s.getSinopsis());
        }
        return null;
    }


    public List<SerieDTO> converierteDatos(List<Serie> serie){
        return serie.stream()
                .map(s -> new SerieDTO(
                        s.getId(),
                        s.getTitulo(),
                        s.getTotalDeTemporadas(),
                        s.getEvaluacion(),
                        s.getPoster(),
                        s.getGenero(),
                        s.getActores(),
                        s.getSinopsis()))
                .collect(Collectors.toList());
    }
    public List<EpisodioDTO> obtenerTodasLasTemporadas(Long id){
        Optional<Serie> serie = serieRpository.findById(id);
        if (serie.isPresent()){
            Serie s = serie.get();
            return s.getEpisodios().stream().map(e -> new EpisodioDTO(
                    e.getTemporada(),
                    e.getTitulo(),
                    e.getNumeroEpisodio()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<EpisodioDTO> obtenerTemporadasPorNumero(Long id, Long numeroTemporada) {
        return serieRpository.obtenerTemporadasPorNumero(id,numeroTemporada).stream()
                .map(e-> new EpisodioDTO(
                        e.getTemporada(),
                        e.getTitulo(),
                        e.getNumeroEpisodio()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obtnerSeriesPorGenero(String nombreGenero) {
        Genero genero = Genero.fromEspaniol(nombreGenero);
        return converierteDatos(serieRpository.findByGenero(genero));
    }
}
