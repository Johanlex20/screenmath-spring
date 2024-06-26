package com.aluraGenesysSpring.screenmatch.dto;

import com.aluraGenesysSpring.screenmatch.models.Genero;

public record SerieDTO(
        Long id,
        String titulo,
        Integer totalDeTemporadas,
        Double evaluacion,
        String poster,
        Genero genero,
        String sinopsis,
        String actores
) {
}
