package com.example.demo.service.client;

import com.example.demo.data.dto.DiscenteDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.http.MediaType;

@Component
public class DiscenteServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(DiscenteServiceClient.class);
    private final WebClient webClient;

    @Autowired
    public DiscenteServiceClient(WebClient discenteWebClient) {
        this.webClient = discenteWebClient;
    }

    public DiscenteDTO getDiscenteById(Long discenteId) {
        return webClient.get()
                .uri("/{id}", discenteId)
                .retrieve()
                .bodyToMono(DiscenteDTO.class)
                .onErrorResume(e -> {
                    logger.error("Errore nel recupero del discente con ID {}: {}", discenteId, e.getMessage());
                    return Mono.empty();
                })
                .block();
    }

    public DiscenteDTO getDiscenteByNomeECognome(String nome, String cognome) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/cerca")
                        .queryParam("nome", nome)
                        .queryParam("cognome", cognome)
                        .build())
                .retrieve()
                .bodyToMono(DiscenteDTO.class)
                .onErrorResume(e -> {
                    logger.debug("Discente non trovato con nome {} e cognome {}", nome, cognome);
                    return Mono.empty();
                })
                .block();
    }

    public DiscenteDTO createDiscente(DiscenteDTO discenteDTO) {
        logger.info("Tentativo di creazione discente: {} {}", discenteDTO.getNome(), discenteDTO.getCognome());

        DiscenteDTO created = webClient.post()
                .uri("/nuovo")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(discenteDTO)
                .retrieve()
                .bodyToMono(DiscenteDTO.class)
                .doOnError(e -> logger.error("Errore durante la chiamata API: {}", e.getMessage()))
                .block();

        if (created == null) {
            throw new RuntimeException("Errore nella creazione del discente: risposta nulla");
        }

        if (created.getId() == null) {
            throw new RuntimeException("Errore nella creazione del discente: ID non presente");
        }

        return created;
    }

    public DiscenteDTO getOrCreateDiscente(String nome, String cognome) {
        if (nome == null || cognome == null) {
            throw new IllegalArgumentException("Nome e cognome del discente sono obbligatori");
        }

        String nomeTrimmed = nome.trim();
        String cognomeTrimmed = cognome.trim();

        // Prima cerca il discente
        DiscenteDTO esistente = getDiscenteByNomeECognome(nomeTrimmed, cognomeTrimmed);
        if (esistente != null && esistente.getId() != null) {
            logger.info("Discente esistente trovato con ID: {}", esistente.getId());
            return esistente;
        }

        // Se non esiste, crea nuovo discente
        DiscenteDTO nuovoDiscente = new DiscenteDTO();
        nuovoDiscente.setNome(nomeTrimmed);
        nuovoDiscente.setCognome(cognomeTrimmed);

        try {
            DiscenteDTO creato = createDiscente(nuovoDiscente);
            logger.info("Nuovo discente creato con ID: {}", creato.getId());
            return creato;
        } catch (Exception e) {
            logger.error("Errore nella creazione del discente: {}", e.getMessage());
            throw new RuntimeException("Impossibile creare il discente", e);
        }
    }
}
