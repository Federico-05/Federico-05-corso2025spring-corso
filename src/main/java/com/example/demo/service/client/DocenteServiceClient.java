package com.example.demo.service.client;

import com.example.demo.data.dto.DocenteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class DocenteServiceClient {

    @Autowired
    private WebClient docenteWebClient;

    public DocenteDTO getDocenteByNomeECognome(String nome, String cognome) {
        try {
            return docenteWebClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/cerca")
                            .queryParam("nome", nome)
                            .queryParam("cognome", cognome)
                            .build())
                    .retrieve()
                    .bodyToMono(DocenteDTO.class)
                    .block();
        } catch (Exception e) {
            return null;
        }
    }

    public DocenteDTO createDocente(DocenteDTO dto) {
        try {
            return docenteWebClient.post()
                    .uri("/")
                    .bodyValue(dto)
                    .retrieve()
                    .bodyToMono(DocenteDTO.class)
                    .block();
        } catch (Exception e) {
            return null;
        }
    }

    public DocenteDTO getDocenteById(Long id) {
        try {
            return docenteWebClient.get()
                    .uri("/{id}", id)
                    .retrieve()
                    .bodyToMono(DocenteDTO.class)
                    .block();
        } catch (Exception e) {
            return null;
        }
    }

}