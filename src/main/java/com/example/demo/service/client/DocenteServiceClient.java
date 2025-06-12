
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

    public DocenteDTO createDocente(DocenteDTO docenteDTO) {
        logger.info("Tentativo di creazione docente: {} {}", docenteDTO.getNome(), docenteDTO.getCognome());

        DocenteDTO created = webClient.post()
                .uri("/nuovo")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(docenteDTO)
                .retrieve()
                .bodyToMono(DocenteDTO.class)
                .doOnError(e -> logger.error("Errore durante la chiamata API: {}", e.getMessage()))
                .block();

        if (created == null) {
            throw new RuntimeException("Errore nella creazione del docente: risposta nulla");
        }

        if (created.getId() == null) {
            throw new RuntimeException("Errore nella creazione del docente: ID non presente");
        }

        return created;
    }

    public DocenteDTO getOrCreateDocente(String nomeDocente, String cognomeDocente) {
        if (nomeDocente == null || cognomeDocente == null) {
            throw new IllegalArgumentException("Nome e cognome del docente sono obbligatori");
        }

        String nomeTrimmed = nomeDocente.trim();
        String cognomeTrimmed = cognomeDocente.trim();

        // Prima cerca il docente
        DocenteDTO esistente = getDocenteByNomeECognome(nomeTrimmed, cognomeTrimmed);
        if (esistente != null && esistente.getId() != null) {
            logger.info("Docente esistente trovato con ID: {}", esistente.getId());
            return esistente;
        }

        // Se non esiste, crea nuovo docente
        DocenteDTO nuovoDocente = new DocenteDTO();
        nuovoDocente.setNome(nomeTrimmed);
        nuovoDocente.setCognome(cognomeTrimmed);

        try {
            DocenteDTO creato = createDocente(nuovoDocente);
            logger.info("Nuovo docente creato con ID: {}", creato.getId());
            return creato;
        } catch (Exception e) {
            logger.error("Errore nella creazione del docente: {}", e.getMessage());
            throw new RuntimeException("Impossibile creare il docente", e);
        }
    }
}
