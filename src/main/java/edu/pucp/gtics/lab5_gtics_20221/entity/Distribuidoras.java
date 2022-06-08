package edu.pucp.gtics.lab5_gtics_20221.entity;

import java.io.Serializable;

public class Distribuidoras implements Serializable{

    private int id;
    private String nombre;
    private String descripcion;
    private String web;
    private int fundacion = 1800;
    private Paises sede;

    public int getId() {
        return id;
    }

    public void setId(int iddistribuidora) {
        this.id = iddistribuidora;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }
    public int getFundacion() {
        return fundacion;
    }

    public void setFundacion(int fundacion) {
        this.fundacion = fundacion;
    }

    public Paises getSede() {
        return sede;
    }

    public void setSede(Paises sede) {
        this.sede = sede;
    }
}
