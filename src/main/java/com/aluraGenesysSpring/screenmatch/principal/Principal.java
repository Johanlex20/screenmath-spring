package com.aluraGenesysSpring.screenmatch.principal;
import com.aluraGenesysSpring.screenmatch.models.*;
import com.aluraGenesysSpring.screenmatch.repository.iSerieRepository;
import com.aluraGenesysSpring.screenmatch.services.ConsumoApi;
import com.aluraGenesysSpring.screenmatch.services.ConvierteDatos;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner sc = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private static String URL_BASE ="https://www.omdbapi.com/?t=";
    private static String API_KEY = "&apikey=2de77f45";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosSerie> datosSeries = new ArrayList<>();
    private  iSerieRepository serieRepository;
    private List<Serie> series;

    public Principal(iSerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }


    public void muestraElMenu(){
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar Series
                    2 - Buscar episodios
                    3 - Mostrar Series Buscadas
                    4 - Buscar Series Por Titulo
                    5 - Buscar Top 5
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    mostrarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarTop5Series();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                default:
                    System.out.println("Opción inválida");
            }
        }
    }


    private DatosSerie getDatosSerie() {
        System.out.println("Escribe el nombre de la serie que deseas buscar");
        var nombreSerie = sc.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
        System.out.println(json);
        DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);
        return datos;
    }


    private void buscarEpisodioPorSerie() {
        //DatosSerie datosSerie = getDatosSerie();
        mostrarSeriesBuscadas();
        System.out.println("Escribe el nombre de la serie que quieres ver los episodios: ");
        var nombreSerie = sc.nextLine();

        Optional<Serie> serie = series.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase()))
                .findFirst();

        if (serie.isPresent()){
            var serieEncontrada = serie.get();

            List<DatosTemporadas> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalDeTemporadas(); i++) {
                var json = consumoApi.obtenerDatos(URL_BASE + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DatosTemporadas datosTemporada = conversor.obtenerDatos(json, DatosTemporadas.class);
                temporadas.add(datosTemporada);
            }
            temporadas.forEach(System.out::println);

            //CONVERTIR LA LISTA DE TEMPORADA A UNA LISTA DE EPISODIOS

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e-> new Episodio(d.numero(),e)))
                    .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            serieRepository.save(serieEncontrada);
        }
    }


    private void buscarSerieWeb() {
        DatosSerie datos = getDatosSerie();
        //datosSeries.add(datos);
        Serie serie = new Serie(datos);
        serieRepository.save(serie);
        System.out.println(datos);
    }



    private void mostrarSeriesBuscadas() {
//        List<Serie> series = new ArrayList<>();
//        series = datosSeries.stream()
//                .map(d-> new Serie(d))
//                .collect(Collectors.toList());

        series = serieRepository.findAll();

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }



    private void buscarSeriePorTitulo() {
        System.out.println("Escribe el nombre de la serie que deseas buscar : ");
        var nombreSerie = sc.nextLine();

        Optional<Serie> serieBuscada = serieRepository.findByTituloContainsIgnoreCase(nombreSerie);

        if (serieBuscada.isPresent()){
            System.out.println("La serie buscada es: "+serieBuscada.get());
        }else {
            System.out.println("Serie no encontrada");
        }
    }


    private void buscarTop5Series() {
        List<Serie> topSeries = serieRepository.findTop5ByOrderByEvaluacionDesc();
        topSeries.forEach(s-> System.out.println("Serie: "+s.getTitulo()+ " Evaluación: "+ s.getEvaluacion()));
    }






}
