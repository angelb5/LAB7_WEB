package edu.pucp.gtics.lab5_gtics_20221.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;

@Getter
@Setter
public class Juego implements Serializable{
    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private String image;
    private Plataformas plataforma;
    private Distribuidoras distribuidora;
    private Generos genero;

}
