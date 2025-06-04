package com.example.demo.service;

import com.example.demo.data.dto.CorsoDTO;
import com.example.demo.data.dto.CorsoFormDTO;
import com.example.demo.data.entity.Corso;
import com.example.demo.repository.CorsoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CorsoService {

    @Autowired
    private CorsoRepository corsoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    private final String docenteServiceUrl = "http://localhost:8081/docenti/{id_docente}";

    public List<CorsoDTO> getAllCorsiDTO() {
        return corsoRepository.findAll(Sort.by("id")).stream()
                .map(corso -> modelMapper.map(corso, CorsoDTO.class))
                .collect(Collectors.toList());
    }

    public CorsoFormDTO getCorsoById(Long id) {
        return corsoRepository.findById(id)
                .map(corso -> modelMapper.map(corso, CorsoFormDTO.class))
                .orElse(null);
    }

    public CorsoDTO saveCorso(CorsoFormDTO dto) {
        // ✅ Verifica se il docente esiste chiamando il microservizio Docente
        if (dto.getId_docente() != null) {
            try {
                ResponseEntity<String> response = restTemplate.getForEntity(
                        docenteServiceUrl, String.class, dto.getId_docente()
                );

                if (!response.getStatusCode().is2xxSuccessful()) {
                    throw new RuntimeException("Docente con id " + dto.getId_docente() + " non trovato");
                }
            } catch (RestClientException e) {
                throw new RuntimeException("Errore durante la verifica del docente: " + e.getMessage());
            }
        }

        Corso corso = modelMapper.map(dto, Corso.class);
        Corso salvato = corsoRepository.save(corso);
        return modelMapper.map(salvato, CorsoDTO.class);
    }

    public CorsoDTO updateCorso(Long id, CorsoFormDTO dto) {
        Optional<Corso> optionalCorso = corsoRepository.findById(id);
        if (optionalCorso.isPresent()) {
            Corso corso = optionalCorso.get();
            corso.setNome(dto.getNome());
            corso.setAnnoAccademico(dto.getAnnoAccademico());
            corso.setId_docente(dto.getId_docente());
            Corso aggiornato = corsoRepository.save(corso);
            return modelMapper.map(aggiornato, CorsoDTO.class);
        } else {
            return null;
        }
    }

    public void deleteCorso(Long id) {
        if (corsoRepository.existsById(id)) {
            corsoRepository.deleteById(id);
        }
    }
}
