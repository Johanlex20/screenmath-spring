package com.aluraGenesysSpring.screenmatch.models;

public enum Genero {
    ACCION ("Action", "Acción"),
    ROMANCE ("Romance", "Romance"),
    CRIMEN ("Crime", "Crimen"),
    COMEDIA ("Comedy", "Comedia"),
    DRAMA ("Drama", "Drama"),
    AVENTURA ("Adventure", "Aventura");

    private String generoOmdb;
    private String generoEspaniol;
    Genero (String generoOmdb, String generoEspaniol){
        this.generoOmdb = generoOmdb;
        this.generoEspaniol = generoEspaniol;
    }

    public static Genero fromString(String text) {
        for (Genero genero : Genero.values()) {
            if (genero.generoOmdb.equalsIgnoreCase(text)) {
                return genero;
            }
        }
        throw new IllegalArgumentException("Ningula categoria encontrada: "+ text);
    }

    public static Genero fromEspaniol(String text) {
        for (Genero genero : Genero.values()) {
            if (genero.generoEspaniol.equalsIgnoreCase(text)) {
                return genero;
            }
        }
        throw new IllegalArgumentException("Ningun Genero encontrado: "+ text);
    }



}
