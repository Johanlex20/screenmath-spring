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

}
