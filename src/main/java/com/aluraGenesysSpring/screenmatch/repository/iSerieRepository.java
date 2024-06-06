package com.aluraGenesysSpring.screenmatch.repository;
import com.aluraGenesysSpring.screenmatch.models.Episodio;
import com.aluraGenesysSpring.screenmatch.models.Genero;
import com.aluraGenesysSpring.screenmatch.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface iSerieRepository extends JpaRepository<Serie,Long> {

    Optional<Serie> findByTituloContainsIgnoreCase(String nombreSerie);

    List<Serie> findTop5ByOrderByEvaluacionDesc();

    List<Serie> findByGenero(Genero genero);

//    List<Serie> findByTotalTemporadasLessThanEqualAndEvaluacionGreaterThanEqual(int totalTemporadas, double evaluacion);
//    @Query( value = "SELECT * FROM series WHERE series.total_de_temporadas <= 6 AND series.evaluacion >= 7.5", nativeQuery = true )
//    List<Serie> seriesPorTemporadaYEvaluacion();

    @Query("SELECT s FROM Serie s WHERE s.totalDeTemporadas >= :totalTemporadas AND s.evaluacion >= :evaluacion")
    List<Serie> seriesPorTemporadaYEvaluacion(int totalTemporadas, Double evaluacion);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:nombreEpisodio%")
    List<Episodio> episodiosPorNombre(String nombreEpisodio);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.evaluacion DESC LIMIT 5")
    List<Episodio> top5Episodios(Serie serie);


}
