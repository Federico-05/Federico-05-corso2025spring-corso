package com.example.demo.service;

import com.example.demo.data.dto.CorsoDTO;
import com.example.demo.data.dto.CorsoFormDTO;
import com.example.demo.data.dto.DiscenteDTO;
import com.example.demo.data.dto.DocenteDTO;
import com.example.demo.data.entity.Corso;
import com.example.demo.data.entity.CorsoDiscente;
import com.example.demo.repository.CorsoDiscenteRepository;
import com.example.demo.repository.CorsoRepository;
import com.example.demo.service.client.DiscenteServiceClient;
import com.example.demo.service.client.DocenteServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CorsoService {

    private static final Logger logger = LoggerFactory.getLogger(CorsoService.class);

    private final CorsoRepository corsoRepository;
    private final CorsoDiscenteRepository corsoDiscenteRepository;
    private final DocenteServiceClient docenteServiceClient;
    private final DiscenteServiceClient discenteServiceClient;

    @Autowired
    public CorsoService(CorsoRepository corsoRepository,
                        CorsoDiscenteRepository corsoDiscenteRepository,
                        DocenteServiceClient docenteServiceClient,
                        DiscenteServiceClient discenteServiceClient) {
        this.corsoRepository = corsoRepository;
        this.corsoDiscenteRepository = corsoDiscenteRepository;
        this.docenteServiceClient = docenteServiceClient;
        this.discenteServiceClient = discenteServiceClient;
    }

    @Transactional(readOnly = true)
    public List<CorsoDTO> getAllCorsiDTO() {
        List<Corso> corsi = corsoRepository.findAll(Sort.by("id"));
        return corsi.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CorsoDTO getCorsoById(Long id) {
        return corsoRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Corso non trovato con id: " + id));
    }

    @Transactional
    public CorsoDTO saveCorso(CorsoFormDTO formDTO) {
        try {
            // Recupera o crea docente
            DocenteDTO docente = docenteServiceClient.getOrCreateDocente(
                    formDTO.getNomeDocente().trim(),
                    formDTO.getCognomeDocente().trim()
            );

            if (docente == null || docente.getId() == null) {
                throw new RuntimeException("Docente non valido");
            }

            // Crea corso e assegna docente
            Corso corso = new Corso();
            corso.setNome(formDTO.getNome().trim());
            corso.setAnnoAccademico(formDTO.getAnnoAccademico());
            corso.setId_docente(docente.getId());

            Corso savedCorso = corsoRepository.save(corso);

            // Associa i discenti singolarmente
            if (formDTO.getDiscenti() != null && !formDTO.getDiscenti().isEmpty()) {
                for (DiscenteDTO discenteForm : formDTO.getDiscenti()) {
                    DiscenteDTO discenteSalvato = discenteServiceClient.getOrCreateDiscente(
                            discenteForm.getNome().trim(),
                            discenteForm.getCognome().trim()
                    );

                    if (discenteSalvato != null && discenteSalvato.getId() != null) {
                        CorsoDiscente corsoDiscente = new CorsoDiscente();
                        corsoDiscente.setIdCorso(savedCorso.getId());
                        corsoDiscente.setIdDiscente(discenteSalvato.getId());
                        corsoDiscenteRepository.save(corsoDiscente);
                    }
                }
            }

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
        try {
            Corso corsoEsistente = corsoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Corso non trovato con id: " + id));

            // Recupera o crea docente
            DocenteDTO docente = docenteServiceClient.getOrCreateDocente(
                    formDTO.getNomeDocente().trim(),
                    formDTO.getCognomeDocente().trim()
            );

            if (docente == null || docente.getId() == null) {
                throw new RuntimeException("Docente non valido");
            }

            // Aggiorna dati corso
            corsoEsistente.setNome(formDTO.getNome().trim());
            corsoEsistente.setAnnoAccademico(formDTO.getAnnoAccademico());
            corsoEsistente.setId_docente(docente.getId());

            Corso corsoAggiornato = corsoRepository.save(corsoEsistente);

            // Rimuove associazioni discenti precedenti
            corsoDiscenteRepository.deleteByIdCorso(id);

            // Associa i discenti singolarmente
            if (formDTO.getDiscenti() != null && !formDTO.getDiscenti().isEmpty()) {
                for (DiscenteDTO discenteForm : formDTO.getDiscenti()) {
                    DiscenteDTO discenteSalvato = discenteServiceClient.getOrCreateDiscente(
                            discenteForm.getNome().trim(),
                            discenteForm.getCognome().trim()
                    );

                    if (discenteSalvato != null && discenteSalvato.getId() != null) {
                        CorsoDiscente corsoDiscente = new CorsoDiscente();
                        corsoDiscente.setIdCorso(corsoAggiornato.getId());
                        corsoDiscente.setIdDiscente(discenteSalvato.getId());
                        corsoDiscenteRepository.save(corsoDiscente);
                    }
                }
            }

            CorsoDTO result = new CorsoDTO(corsoAggiornato);
            result.setNomeDocenteCompleto(docente.getNome() + " " + docente.getCognome());

            return result;

        } catch (Exception e) {
            logger.error("Errore nell'aggiornamento del corso: {}", e.getMessage());
            throw new RuntimeException("Impossibile aggiornare il corso", e);
        }
    }

    private CorsoDTO convertToDTO(Corso corso) {
        CorsoDTO dto = new CorsoDTO(corso);

        // Recupera docente per nome completo (potresti anche chiamare direttamente il servizio docente)
        if (corso.getId_docente() != null) {
            DocenteDTO docente = docenteServiceClient.getDocenteById(corso.getId_docente());
            if (docente != null) {
                dto.setNomeDocenteCompleto(docente.getNome() + " " + docente.getCognome());
            }
        }

        // Recupera discenti associati (potresti fare un metodo per ottenerli via servizio discente se vuoi)
        List<CorsoDiscente> relazioni = corsoDiscenteRepository.findByIdCorso(corso.getId());
        List<DiscenteDTO> discenti = relazioni.stream()
                .map(rel -> discenteServiceClient.getDiscenteById(rel.getIdDiscente()))
                .filter(d -> d != null)
                .collect(Collectors.toList());

        dto.setDiscenti(discenti);

        return dto;
    }

    @Transactional
    public void deleteCorso(Long id) {
        corsoDiscenteRepository.deleteByIdCorso(id);
        corsoRepository.deleteById(id);
    }
}
