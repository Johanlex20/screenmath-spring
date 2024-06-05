package com.aluraGenesysSpring.screenmatch.models;

public enum Genero {
    ACCION ("Action"),
    ROMANCE ("Romance"),
    CRIMEN ("Crime"),
    COMEDIA ("Comedy"),
    DRAMA ("Drama"),
    AVENTURA ("Adventure");

    private String generoOmdb;
    Genero (String generoOmdb){
        this.generoOmdb = generoOmdb;
    }

    public static Genero fromString(String text) {
        for (Genero genero : Genero.values()) {
            if (genero.generoOmdb.equalsIgnoreCase(text)) {
                return genero;
            }
        }
        throw new IllegalArgumentException("Ningula categoria encontrada: "+ text);
    }



}
