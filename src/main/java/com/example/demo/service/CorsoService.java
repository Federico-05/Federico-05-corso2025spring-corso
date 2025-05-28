
package com.example.demo.service;

import com.example.demo.data.dto.CorsoDTO;
import com.example.demo.data.dto.CorsoFormDTO;
import com.example.demo.data.entity.Corso;
import com.example.demo.repository.CorsoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CorsoService {

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private CorsoRepository corsoRepository;

//    @Autowired
//    private DocenteRepository docenteRepository;
//
//    @Autowired
//    private DiscenteRepository discenteRepository;

    // Metodo per visualizzazione lista
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

//        if (corso.getDocente() != null) {
//            dto.setDocenteId(corso.getDocente().getId());
//            dto.setDocenteNomeCompleto(corso.getDocente().getNome() + " " + corso.getDocente().getCognome());
//        }
//
//        if (corso.getDiscenti() != null && !corso.getDiscenti().isEmpty()) {
//            dto.setDiscentiIds(corso.getDiscenti().stream()
//                    .map(Discente::getId)
//                    .collect(Collectors.toList()));
//
//            dto.setNomiDiscenti(corso.getDiscenti().stream()
//                    .map(d -> d.getNome() + " " + d.getCognome())
//                    .collect(Collectors.toList()));
//        }

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

//        if (dto.getDocenteNomeCompleto() != null) {
//            String[] nomeDocente = dto.getDocenteNomeCompleto().split(" ", 2);
//            if (nomeDocente.length == 2) {
//                corso.setDocente(docenteRepository.findByNomeAndCognome(nomeDocente[0], nomeDocente[1])
//                        .orElseGet(() -> {
//                            Docente nuovoDocente = new Docente();
//                            nuovoDocente.setNome(nomeDocente[0]);
//                            nuovoDocente.setCognome(nomeDocente[1]);
//                            return docenteRepository.save(nuovoDocente);
//                        }));
//            }
//        }
//
//        if (dto.getNomiDiscenti() != null && !dto.getNomiDiscenti().isEmpty()) {
//            List<Discente> discenti = dto.getNomiDiscenti().stream()
//                    .map(nomeCompleto -> {
//                        String[] nome = nomeCompleto.split(" ", 2);
//                        if (nome.length == 2) {
//                            return discenteRepository.findByNomeAndCognome(nome[0], nome[1])
//                                    .orElseGet(() -> {
//                                        Discente nuovoDiscente = new Discente();
//                                        nuovoDiscente.setNome(nome[0]);
//                                        nuovoDiscente.setCognome(nome[1]);
//                                        return discenteRepository.save(nuovoDiscente);
//                                    });
//                        }
//                        return null;
//                    })
//                    .filter(Objects::nonNull)
//                    .collect(Collectors.toList());
//            corso.setDiscenti(discenti);
//        }

        Corso savedCorso = corsoRepository.save(corso);
        return convertToDTO(savedCorso);
    }

    public CorsoDTO updateCorso(Long id, CorsoFormDTO dto) {
        return corsoRepository.findById(id)
                .map(corso -> {
                    corso.setNome(dto.getNome());
                    corso.setAnnoAccademico(dto.getAnnoAccademico());

//                    if (dto.getDocenteNomeCompleto() != null) {
//                        String[] nomeDocente = dto.getDocenteNomeCompleto().split(" ", 2);
//                        if (nomeDocente.length == 2) {
//                            corso.setDocente(docenteRepository.findByNomeAndCognome(nomeDocente[0], nomeDocente[1])
//                                    .orElseGet(() -> {
//                                        Docente nuovoDocente = new Docente();
//                                        nuovoDocente.setNome(nomeDocente[0]);
//                                        nuovoDocente.setCognome(nomeDocente[1]);
//                                        return docenteRepository.save(nuovoDocente);
//                                    }));
//                        }
//                    }
//                    else {
//                        corso.setDocente(null);
//                    }
//
//                    if (dto.getNomiDiscenti() != null) {
//                        List<Discente> discenti = dto.getNomiDiscenti().stream()
//                                .map(nomeCompleto -> {
//                                    String[] nome = nomeCompleto.split(" ", 2);
//                                    if (nome.length == 2) {
//                                        return discenteRepository.findByNomeAndCognome(nome[0], nome[1])
//                                                .orElseGet(() -> {
//                                                    Discente nuovoDiscente = new Discente();
//                                                    nuovoDiscente.setNome(nome[0]);
//                                                    nuovoDiscente.setCognome(nome[1]);
//                                                    return discenteRepository.save(nuovoDiscente);
//                                                });
//                                    }
//                                    return null;
//                                })
//                                .filter(Objects::nonNull)
//                                .collect(Collectors.toList());
//                        corso.setDiscenti(discenti);
//                    } else {
//                        corso.setDiscenti(List.of());
//                    }

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
