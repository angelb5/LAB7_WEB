package edu.pucp.gtics.lab5_gtics_20221.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Entity
@Table(name = "paises")
public class Paises implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 0, message = "Sede no puede estar vacío")
    @Column(name = "idpais", nullable = false)
    private int id;
    private String iso;
    private String nombre;


    public int getId() {
        return id;
    }

    public void setId(int idpais) {
        this.id = idpais;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
