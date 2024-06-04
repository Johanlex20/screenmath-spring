package com.aluraGenesysSpring.screenmatch.principal;

import com.aluraGenesysSpring.screenmatch.models.DatosEpisodio;
import com.aluraGenesysSpring.screenmatch.models.DatosSerie;
import com.aluraGenesysSpring.screenmatch.models.DatosTemporadas;
import com.aluraGenesysSpring.screenmatch.models.Episodio;
import com.aluraGenesysSpring.screenmatch.services.ConsumoApi;
import com.aluraGenesysSpring.screenmatch.services.ConvierteDatos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner sc = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private static String URL_BASE ="https://www.omdbapi.com/?t=";
    private static String API_KEY = "&apikey=2de77f45";
    private ConvierteDatos conversor = new ConvierteDatos();

    public void muestraElMenu(){


        System.out.println("Por favor escribe el nombre de la serie que deseas buscar:");
        var nombreSerie= sc.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ","+") + API_KEY);
        var datos = conversor.obtenerDatos(json, DatosSerie.class);
        System.out.println(datos);



        //CREAR LISTA PARA VER LAS TEMPORADASA
        List<DatosTemporadas> temporadas = new ArrayList<>();
        for (int i = 1; i <= datos.totalDeTemporadas(); i++) {
            json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ","+") + "&Season=" +i+ API_KEY);
            var datosTemporadas = conversor.obtenerDatos(json, DatosTemporadas.class);
            temporadas.add(datosTemporadas);
        }
        // imprimir todo lo que tenga ese lista
        //temporadas.forEach(System.out::println);


        //Mostrar el titulo de lps espisodias por temporada
//        for (int i = 0; i < datos.totalDeTemporadas(); i++) {
//            List<DatosEpisodio>episodiosTemporada = temporadas.get(i).episodios();
//            for (int j = 0; j < episodiosTemporada.size(); j++) {
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//        }

//      LAMBAS DE JAVA 8 SIMPLIFICA UNA LISTA DENTRO DE OTRA LISTA
//      Consumiendo los titulos de episodio de cada temporada
        //temporadas.forEach(t->t.episodios().forEach(e-> System.out.println(e.titulo())));



        //Converitr todas las informaciones a una lista del tipo DatosEpisodo
        List<DatosEpisodio> datosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());



        // TOP 5 EPISODIOS
        System.out.println("TOP 5 EPISODIOS");
        datosEpisodios.stream()
                .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
                .limit(5)
                .forEach(System.out::println);

        //CONVIRTIENDO LOS DATOS A LISTA TIPO EPISODIO
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t->t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d)))
                .collect(Collectors.toList());

        episodios.forEach(System.out::println);
    }
}
