package com.aluraGenesysSpring.screenmatch.models;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosSerie(
        @JsonAlias("Title") String titulo,
        @JsonAlias("totalSeasons") Integer totalDeTemporadas,
        @JsonAlias("imdbRating") String evaluacion,
        @JsonAlias("Genre") String genero,
        @JsonAlias("Plot") String sipnocis,
        @JsonAlias("Poster") String poster
) {
}
