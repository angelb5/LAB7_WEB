package edu.pucp.gtics.lab5_gtics_20221.dao;

import edu.pucp.gtics.lab5_gtics_20221.dto.JuegoDto;
import edu.pucp.gtics.lab5_gtics_20221.entity.Juego;
import edu.pucp.gtics.lab5_gtics_20221.entity.JuegosUserDto;
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
public class JuegoDao {

    public List<Juego> listarJuegos() {
        RestTemplate restTemplate = new RestTemplateBuilder().
                basicAuthentication("jbarturen@pucp.pe", "123456")
                .build();
        ResponseEntity<Juego[]> response = restTemplate.getForEntity(
                "http://localhost:8080/juegos", Juego[].class);

        return Arrays.asList(response.getBody());
    }

    public List<Juego> listarJuegosPrecio() {
        RestTemplate restTemplate = new RestTemplateBuilder().
                basicAuthentication("jbarturen@pucp.pe", "123456")
                .build();
        ResponseEntity<Juego[]> response = restTemplate.getForEntity(
                "http://localhost:8080/juegos/precio", Juego[].class);

        return Arrays.asList(response.getBody());
    }

    public List<JuegosUserDto> listarJuegosUser(int id) {
        RestTemplate restTemplate = new RestTemplateBuilder().
                basicAuthentication("jbarturen@pucp.pe", "123456")
                .build();
        ResponseEntity<JuegosUserDto[]> response = restTemplate.getForEntity(
                "http://localhost:8080/juegos/usuario/"+id, JuegosUserDto[].class);

        return Arrays.asList(response.getBody());
    }

    public List<Juego> listarJuegosNoComprados(int id) {
        RestTemplate restTemplate = new RestTemplateBuilder().
                basicAuthentication("jbarturen@pucp.pe", "123456")
                .build();
        ResponseEntity<Juego[]> response = restTemplate.getForEntity(
                "http://localhost:8080/juegos/nocomprados/"+id, Juego[].class);

        return Arrays.asList(response.getBody());
    }

    public Juego obtenerJuegoPorId(int id) {
        RestTemplate restTemplate = new RestTemplateBuilder().
                basicAuthentication("jbarturen@pucp.pe", "123456")
                .build();
        String url = "http://localhost:8080/juegos/" + id;
        ResponseEntity<JuegoDto> responseMap = restTemplate.getForEntity(url, JuegoDto.class);

        JuegoDto juegoDto = responseMap.getBody();

        return juegoDto.getJuego();
    }


    public void guardarJuego(Juego juego) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = "http://localhost:8080/juegos";
        HttpEntity<Juego> httpEntity = new HttpEntity<>(juego, headers);

        RestTemplate restTemplate = new RestTemplateBuilder().
                basicAuthentication("jbarturen@pucp.pe", "123456")
                .build();
        if (juego.getId() > 0) {
            restTemplate.put(url, httpEntity, Juego.class);
        } else {
            restTemplate.postForEntity(url, httpEntity, Juego.class);
        }

    }

    public void borrarJuego(int id) {
        RestTemplate restTemplate = new RestTemplateBuilder().
                basicAuthentication("jbarturen@pucp.pe", "123456")
                .build();
        restTemplate.delete("http://localhost:8080/juegos/" + id);
    }

}
