package edu.pucp.gtics.lab5_gtics_20221.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "plataformas")
public class Plataformas implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 0, message = "Plataforma no puede estar vacío")
    @Column(name = "idplataforma")
    private int id;

    @Size(min=3, max = 45, message = "Debe contener entre 3 y 45 caracteres")
    private String nombre;

    @Size(min=3, max = 198, message = "Debe contener entre 3 y 198 caracteres")
    private String descripcion;

    public int getId() {
        return id;
    }

    public void setId(int idplataforma) {
        this.id = idplataforma;
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

}
