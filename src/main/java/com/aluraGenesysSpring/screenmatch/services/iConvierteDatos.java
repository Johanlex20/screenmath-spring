package com.aluraGenesysSpring.screenmatch.services;

public interface iConvierteDatos {

    <T> T obtenerDatos(String json, Class<T> clase); //metodo generico ya que no se sabe que retornara

}
