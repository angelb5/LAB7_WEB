package edu.pucp.gtics.lab5_gtics_20221.dto;

import edu.pucp.gtics.lab5_gtics_20221.entity.Juego;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JuegoDto {
    private String result;
    private String msg;
    private Juego juego;
}
