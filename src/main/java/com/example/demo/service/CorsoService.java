
package com.example.demo.service;

import com.example.demo.data.dto.CorsoDTO;
import com.example.demo.data.dto.CorsoFormDTO;
import com.example.demo.data.dto.DocenteDTO;
import com.example.demo.data.entity.Corso;
import com.example.demo.repository.CorsoRepository;
import com.example.demo.service.client.DocenteServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CorsoService {
    private static final Logger logger = LoggerFactory.getLogger(CorsoService.class);
    private final CorsoRepository corsoRepository;
    private final DocenteServiceClient docenteServiceClient;

    @Autowired
    public CorsoService(CorsoRepository corsoRepository, DocenteServiceClient docenteServiceClient) {
        this.corsoRepository = corsoRepository;
        this.docenteServiceClient = docenteServiceClient;
    }

    @Transactional(readOnly = true)
    public List<CorsoDTO> getAllCorsiDTO() {
        try {
            List<Corso> corsi = corsoRepository.findAll(Sort.by("id"));
            return corsi.stream().map(this::convertToDTO).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Errore nel recupero dei corsi", e);
        }
    }

    @Transactional(readOnly = true)
    public CorsoDTO getCorsoById(Long id) {
        try {
            return corsoRepository.findById(id).map(this::convertToDTO)
                    .orElseThrow(() -> new RuntimeException("Corso non trovato con id: " + id));
        } catch (Exception e) {
            throw new RuntimeException("Errore nel recupero del corso", e);
        }
    }

    @Transactional
    public CorsoDTO saveCorso(CorsoFormDTO formDTO) {

        try {

            DocenteDTO docente = docenteServiceClient.getOrCreateDocente(
                    formDTO.getNomeDocente().trim(),
                    formDTO.getCognomeDocente().trim()
            );

            if (docente == null || docente.getId() == null) {
                throw new RuntimeException("Docente non valido");
            }

            Corso corso = new Corso();
            corso.setNome(formDTO.getNome().trim());
            corso.setAnnoAccademico(formDTO.getAnnoAccademico());
            corso.setId_docente(docente.getId());

            Corso savedCorso = corsoRepository.save(corso);

            CorsoDTO result = new CorsoDTO(savedCorso);
            result.setNomeDocenteCompleto(docente.getNome() + " " + docente.getCognome());

            return result;

        } catch (Exception e) {
            logger.error("Errore nel salvataggio del corso: {}", e.getMessage());
            throw new RuntimeException("Impossibile salvare il corso", e);
        }
    }

    @Transactional
    public CorsoDTO updateCorso(Long id, CorsoFormDTO formDTO) {
        logger.debug("Inizio aggiornamento corso con ID: {}", id);


        Corso esistente = corsoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Corso non trovato con id: " + id));


        DocenteDTO docente = aggiornaDocente(formDTO);

        esistente.setNome(formDTO.getNome().trim());
        esistente.setAnnoAccademico(formDTO.getAnnoAccademico());
        esistente.setId_docente(docente.getId());

        try {
            Corso updatedCorso = corsoRepository.save(esistente);

            CorsoDTO result = new CorsoDTO(updatedCorso);
            result.setNomeDocenteCompleto(docente.getNome() + " " + docente.getCognome());

            logger.debug("Corso aggiornato con successo. ID: {}", id);
            return result;

        } catch (Exception e) {
            logger.error("Errore durante l'aggiornamento del corso con ID: {}", id, e);
            throw new RuntimeException("Errore durante l'aggiornamento del corso", e);
        }
    }


    @Transactional
    private DocenteDTO aggiornaDocente(CorsoFormDTO formDTO) {
        try {
            DocenteDTO docente = docenteServiceClient.getOrCreateDocente(
                    formDTO.getNomeDocente().trim(),
                    formDTO.getCognomeDocente().trim()
            );

            if (docente == null || docente.getId() == null) {
                logger.error("Docente non creato o senza ID");
                throw new RuntimeException("Impossibile ottenere/creare il docente con ID valido");
            }

            logger.debug("Docente recuperato/creato con ID: {}", docente.getId());
            return docente;

        } catch (Exception e) {
            logger.error("Errore durante l'operazione sul docente", e);
            throw new RuntimeException("Errore durante l'operazione sul docente: " + e.getMessage());
        }
    }


    @Transactional
    public void deleteCorso(Long id) {
        try {
            if (!corsoRepository.existsById(id)) {
                throw new RuntimeException("Corso non trovato con id: " + id);
            }
            corsoRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Errore durante l'eliminazione del corso", e);
        }
    }

    private CorsoDTO convertToDTO(Corso corso) {
        try {
            CorsoDTO dto = new CorsoDTO(corso);
            if (corso.getId_docente() != null) {
                DocenteDTO docente = docenteServiceClient.getDocenteById(corso.getId_docente());
                if (docente != null) {
                    dto.setNomeDocenteCompleto(docente.getNome() + " " + docente.getCognome());
                } else {
                    dto.setNomeDocenteCompleto("Docente non disponibile");
                }
            } else {
                dto.setNomeDocenteCompleto("Nessun docente assegnato");
            }
            return dto;
        } catch (Exception e) {
            CorsoDTO dto = new CorsoDTO(corso);
            dto.setNomeDocenteCompleto("Errore nel recupero del docente");
            return dto;
        }
    }
}
