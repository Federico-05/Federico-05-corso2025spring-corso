
package com.example.demo.data.dto;

import com.example.demo.data.entity.Corso;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.stream.Collectors;

public class CorsoDTO {

    private Long id;
    private String nome;
    private Integer annoAccademico;
    private Long id_docente;

    // Per la vista
//    private String docenteNomeCompleto;
//    private List<String> nomiDiscenti;

    public CorsoDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Integer getAnnoAccademico() { return annoAccademico; }
    public void setAnnoAccademico(Integer annoAccademico) { this.annoAccademico = annoAccademico; }

    public Long getId_docente() { return id_docente; }
    public void setId_docente(Long id_docente) { this.id_docente = id_docente; }
//
//    @JsonIgnore
//    public List<Long> getDiscentiIds() { return discentiIds; }
//    public void setDiscentiIds(List<Long> discentiIds) { this.discentiIds = discentiIds; }
//
//    public String getDocenteNomeCompleto() { return docenteNomeCompleto; }
//    public void setDocenteNomeCompleto(String docenteNomeCompleto) { this.docenteNomeCompleto = docenteNomeCompleto; }
//
//    public List<String> getNomiDiscenti() { return nomiDiscenti; }
//    public void setNomiDiscenti(List<String> nomiDiscenti) { this.nomiDiscenti = nomiDiscenti; }
    public CorsoDTO(Corso corso) {
        this.id = corso.getId();
        this.nome = corso.getNome();
        this.annoAccademico = corso.getAnnoAccademico();

//        Docente docente = corso.getDocente();
//        if (docente != null) {
//            this.docenteId = docente.getId();
//            this.docenteNomeCompleto = docente.getNome() + " " + docente.getCognome();
//        }
//
//        if (corso.getDiscenti() != null && !corso.getDiscenti().isEmpty()) {
//            this.discentiIds = corso.getDiscenti().stream()
//                    .map(discente -> discente.getId())
//                    .collect(Collectors.toList());
//
//            this.nomiDiscenti = corso.getDiscenti().stream()
//                    .map(discente -> discente.getNome() + " " + discente.getCognome())
//                    .collect(Collectors.toList());
//        }
    }

}
