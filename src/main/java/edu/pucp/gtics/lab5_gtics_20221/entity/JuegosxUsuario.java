package edu.pucp.gtics.lab5_gtics_20221.entity;

import javax.persistence.*;

@Entity
@Table(name="juegosxusuario")
public class JuegosxUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idjuegosxusuario;
    private int idjuego;
    private int idusuario;
    private int cantidad;

    public int getIdjuegosxusuario() {
        return idjuegosxusuario;
    }

    public void setIdjuegosxusuario(int idjuegosxusuario) {
        this.idjuegosxusuario = idjuegosxusuario;
    }

    public int getIdjuego() {
        return idjuego;
    }

    public void setIdjuego(int idjuego) {
        this.idjuego = idjuego;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
