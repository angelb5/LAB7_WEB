package edu.pucp.gtics.lab5_gtics_20221.dto;

import edu.pucp.gtics.lab5_gtics_20221.entity.Distribuidoras;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class DistribuidorasDto {
    private String result;
    private String msg;
    private Distribuidoras distribuidoras;
}
