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
    private Optional<Serie> serieBuscada;

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
                    6 - Buscar Series Por Categoria
                    7 - Buscar Series Temporadas y evaluacion
                    8 - Buscar Episodio por titulo
                    9 - Top 5 Episodios por serie
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
                case 6:
                    buscarSeriesPorGenero();
                    break;
                case 7:
                    filtrarSeriesPorTemporadaYEvaluacion();
                    break;
                case 8:
                    buscarEpisodiosPorTitulo();
                    break;
                case 9:
                    buscarTop5Episodios();
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
        sc.nextLine();

        serieBuscada = serieRepository.findByTituloContainsIgnoreCase(nombreSerie);

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



    private void buscarSeriesPorGenero() {
        System.out.println("Escribe el genero/catgoria de la serie que quiere buscar : ");
        var genero = sc.nextLine();

        var categoria = Genero.fromEspaniol(genero);
        List<Serie> seriesPorGenero = serieRepository.findByGenero(categoria);
        System.out.println("Las series de la categoria "+ genero);
        seriesPorGenero.forEach(System.out::println );

    }

    private void filtrarSeriesPorTemporadaYEvaluacion(){
        System.out.println("Filtrar séries con cuántas temporadas? : ");
        var totalTemporadas = sc.nextInt();
        sc.nextLine();

        System.out.println("Evaluacion apartir de que valor? : ");
        var evaluacion = sc.nextDouble();
        sc.nextLine();

        List<Serie> filtroSeries = serieRepository.seriesPorTemporadaYEvaluacion(totalTemporadas, evaluacion);
        System.out.println("*** Series Filtradas ***");
        filtroSeries.forEach(s->
                System.out.println(s.getTitulo() + " - evalucaión: "+ s.getEvaluacion()));
    }



    private void buscarEpisodiosPorTitulo(){
        System.out.println("Ingrese titulo del episodio?");
        var nombreEpisodio = sc.nextLine();

        List<Episodio> episodiosEncontrados = serieRepository.episodiosPorNombre(nombreEpisodio);
        episodiosEncontrados.forEach(e -> System.out.printf("Serie: %s Temporada: %s Episodio: %s Evaluación: %s\n",
                e.getSerie().getTitulo(), e.getTemporada(), e.getNumeroEpisodio(), e.getEvaluacion()));
    }



    private void  buscarTop5Episodios(){
        buscarSeriePorTitulo();

        if (serieBuscada.isPresent()){
            Serie serie = serieBuscada.get();
            List<Episodio> topEpisodios = serieRepository.top5Episodios(serie);
            topEpisodios.forEach(e->
                    System.out.printf("Serie: %s Temporada Episodio %s %s Evaluación %s\n",
                            e.getSerie(), e.getTemporada(), e.getTitulo(), e.getEvaluacion()));
        }
    }
}
