package edu.pucp.gtics.lab5_gtics_20221.dao;

import edu.pucp.gtics.lab5_gtics_20221.dto.DistribuidorasDto;
import edu.pucp.gtics.lab5_gtics_20221.entity.Distribuidoras;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class DistribuidorasDao {

    public List<Distribuidoras> listarDistribuidoras() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Distribuidoras[]> response = restTemplate.getForEntity(
                "http://localhost:8080/distribuidoras", Distribuidoras[].class);

        return Arrays.asList(response.getBody());
    }

    public void guardarDistribuidoras(Distribuidoras distribuidoras) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = "http://localhost:8080/distribuidoras";
        HttpEntity<Distribuidoras> httpEntity = new HttpEntity<>(distribuidoras, headers);

        RestTemplate restTemplate = new RestTemplate();
        if (distribuidoras.getIddistribuidora() > 0) {
            restTemplate.put(url, httpEntity, Distribuidoras.class);
        } else {
            restTemplate.postForEntity(url, httpEntity, Distribuidoras.class);
        }
    }

    public void borrarDistribuidoras(int id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete("http://localhost:8080/distribuidoras/" + id);
    }

    public Distribuidoras obtenerDistribuidoraPorId(int id) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8080/distribuidoras/" + id;

        ResponseEntity<DistribuidorasDto> responseMap = restTemplate.getForEntity(url, DistribuidorasDto.class);

        DistribuidorasDto distribuidorasDto = responseMap.getBody();

        return distribuidorasDto.getDistribuidoras();
    }
}
