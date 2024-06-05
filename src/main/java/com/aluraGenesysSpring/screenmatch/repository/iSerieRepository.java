package com.aluraGenesysSpring.screenmatch.repository;
import com.aluraGenesysSpring.screenmatch.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface iSerieRepository extends JpaRepository<Serie,Long> {

    Optional<Serie>findByTituloContainsIgnoreCase(String nombreSerie);

    List<Serie>findTop5ByOrderByEvaluacionDesc();

}
