package com.example.demo.service;

import com.example.demo.client.DocenteClient;
import com.example.demo.data.dto.CorsoDTO;
import com.example.demo.data.dto.CorsoFormDTO;
import com.example.demo.data.dto.DocenteDTO;
import com.example.demo.data.entity.Corso;
import com.example.demo.repository.CorsoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CorsoService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CorsoRepository corsoRepository;

    @Autowired
    private DocenteClient docenteClient;  // injection del Feign client

    public List<CorsoDTO> getAllCorsiDTO() {
        return corsoRepository.findAll(Sort.by("id")).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CorsoDTO convertToDTO(Corso corso) {
        CorsoDTO dto = new CorsoDTO();
        dto.setId(corso.getId());
        dto.setNome(corso.getNome());
        dto.setAnnoAccademico(corso.getAnnoAccademico());
        dto.setId_docente(corso.getId_docente());

        if (corso.getId_docente() != null) {
            DocenteDTO docenteDTO = docenteClient.getDocenteById(corso.getId_docente());
            if (docenteDTO != null) {
                dto.setNomeDocente(docenteDTO.getNome());
                dto.setCognomeDocente(docenteDTO.getCognome());
            }
        }

        return dto;
    }

    public CorsoFormDTO getCorsoById(Long id) {
        return corsoRepository.findById(id)
                .map(CorsoFormDTO::new)
                .orElse(null);
    }

    public CorsoDTO saveCorso(CorsoFormDTO dto) {
        Corso corso = new Corso();
        corso.setNome(dto.getNome());
        corso.setAnnoAccademico(dto.getAnnoAccademico());
        corso.setId_docente(dto.getId_docente());

        Corso savedCorso = corsoRepository.save(corso);
        return convertToDTO(savedCorso);
    }

    public CorsoDTO updateCorso(Long id, CorsoFormDTO dto) {
        return corsoRepository.findById(id)
                .map(corso -> {
                    corso.setNome(dto.getNome());
                    corso.setAnnoAccademico(dto.getAnnoAccademico());
                    corso.setId_docente(dto.getId_docente());

                    return convertToDTO(corsoRepository.save(corso));
                })
                .orElse(null);
    }

    public void deleteCorso(Long id) {
        if (corsoRepository.existsById(id)) {
            corsoRepository.deleteById(id);
        }
    }
}
