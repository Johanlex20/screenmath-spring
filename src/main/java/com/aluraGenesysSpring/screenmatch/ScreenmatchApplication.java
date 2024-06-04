package com.aluraGenesysSpring.screenmatch;

import com.aluraGenesysSpring.screenmatch.models.DatosEpisodio;
import com.aluraGenesysSpring.screenmatch.models.DatosSerie;
import com.aluraGenesysSpring.screenmatch.models.DatosTemporadas;
import com.aluraGenesysSpring.screenmatch.principal.Principal;
import com.aluraGenesysSpring.screenmatch.services.ConsumoApi;
import com.aluraGenesysSpring.screenmatch.services.ConvierteDatos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.muestraElMenu();
	}
}
