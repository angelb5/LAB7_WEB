package edu.pucp.gtics.lab5_gtics_20221.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JuegosUserDto {

    private Integer idjuego;
    private String imageURL;
    private String nombre;
    private String genero;
    private String descripcion;
}
