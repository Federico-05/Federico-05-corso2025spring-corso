package com.example.demo.service.client;

import com.example.demo.data.dto.DocenteDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.http.MediaType;

@Component
public class DocenteServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(DocenteServiceClient.class);
    private final WebClient webClient;

    @Autowired
    public DocenteServiceClient(WebClient docenteWebClient) {
        this.webClient = docenteWebClient;
    }

    public DocenteDTO getDocenteById(Long docenteId) {
        return webClient.get()
                .uri("/{id}", docenteId)
                .retrieve()
                .bodyToMono(DocenteDTO.class)
                .onErrorResume(e -> {
                    logger.error("Errore nel recupero del docente con ID {}: {}", docenteId, e.getMessage());
                    return Mono.empty();
                })
                .block();
    }

    public DocenteDTO createDocente(DocenteDTO docenteDTO) {
        return webClient.post()
                .uri("/nuovo")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(docenteDTO)
                .retrieve()
                .bodyToMono(DocenteDTO.class)
                .onErrorResume(e -> {
                    logger.error("Errore nella creazione del docente: {}", e.getMessage());
                    return Mono.empty();
                })
                .block();
    }

    public DocenteDTO getDocenteByNomeECognome(String nome, String cognome) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/cerca")
                        .queryParam("nome", nome)
                        .queryParam("cognome", cognome)
                        .build())
                .retrieve()
                .bodyToMono(DocenteDTO.class)
                .onErrorResume(e -> {
                    logger.debug("Docente non trovato con nome {} e cognome {}", nome, cognome);
                    return Mono.empty();
                })
                .block();
    }

    public DocenteDTO getOrCreateDocente(String nomeDocente, String cognomeDocente) {
        if (nomeDocente == null || cognomeDocente == null) {
            throw new IllegalArgumentException("Nome e cognome del docente sono obbligatori");
        }

        DocenteDTO docente = getDocenteByNomeECognome(nomeDocente.trim(), cognomeDocente.trim());

        if (docente == null) {
            DocenteDTO newDocente = new DocenteDTO();
            newDocente.setNome(nomeDocente.trim());
            newDocente.setCognome(cognomeDocente.trim());
            return createDocente(newDocente);
        }

        return docente;
    }
}