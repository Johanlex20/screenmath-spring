package com.aluraGenesysSpring.screenmatch.repository;
import com.aluraGenesysSpring.screenmatch.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface iSerieRepository extends JpaRepository<Serie,Long> {

    Optional<Serie>findByTituloContainsIgnoreCase(String nombreSerie);



}
