package edu.pucp.gtics.lab5_gtics_20221.dao;

import edu.pucp.gtics.lab5_gtics_20221.dto.DistribuidorasDto;
import edu.pucp.gtics.lab5_gtics_20221.dto.JuegoDto;
import edu.pucp.gtics.lab5_gtics_20221.entity.Distribuidoras;
import edu.pucp.gtics.lab5_gtics_20221.entity.Juego;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class DistribuidoraDao {
    public List<Distribuidoras> listarDistribuidoras() {
        RestTemplate restTemplate = new RestTemplateBuilder().
                basicAuthentication("jbarturen@pucp.pe", "123456")
                .build();
        ResponseEntity<Distribuidoras[]> response = restTemplate.getForEntity(
                "http://localhost:8080/distribuidoras", Distribuidoras[].class);

        return Arrays.asList(response.getBody());
    }

    public Distribuidoras obtenerDistribuidoraPorId(int id) {
        RestTemplate restTemplate = new RestTemplateBuilder().
                basicAuthentication("jbarturen@pucp.pe", "123456")
                .build();
        String url = "http://localhost:8080/distribuidoras/" + id;
        ResponseEntity<DistribuidorasDto> responseMap = restTemplate.getForEntity(url, DistribuidorasDto.class);

        DistribuidorasDto distribuidorasDto = responseMap.getBody();

        return distribuidorasDto.getDistribuidora();
    }

    public void guardarDistribuidoras(Distribuidoras distribuidoras) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = "http://localhost:8080/distribuidoras";
        HttpEntity<Distribuidoras> httpEntity = new HttpEntity<>(distribuidoras, headers);

        RestTemplate restTemplate = new RestTemplateBuilder().
                basicAuthentication("jbarturen@pucp.pe", "123456")
                .build();
        if (distribuidoras.getId() > 0) {
            restTemplate.put(url, httpEntity, Distribuidoras.class);
        } else {
            restTemplate.postForEntity(url, httpEntity, Distribuidoras.class);
        }
    }

    public void borrarDistribuidora(int id) {
        RestTemplate restTemplate = new RestTemplateBuilder().
                basicAuthentication("jbarturen@pucp.pe", "123456")
                .build();
        restTemplate.delete("http://localhost:8080/distribuidoras/" + id);
    }
}
