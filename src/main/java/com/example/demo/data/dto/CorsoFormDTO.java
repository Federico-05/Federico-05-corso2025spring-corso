package com.example.demo.data.dto;

import com.example.demo.data.entity.Corso;

import java.util.ArrayList;
import java.util.List;

public class CorsoFormDTO {

    private Long id;
    private String nome;
    private Integer annoAccademico;

//    private Docente docente;
//    private List<Discente> discenti = new ArrayList<>();

    // Binding via form (ID)
//    private Long docenteId;
//    private List<Long> discentiIds = new ArrayList<>();

    // Nuovi campi per update tramite nome e cognome
//    private String docenteNomeCompleto;
//    private List<String> nomiDiscenti = new ArrayList<>();

    public CorsoFormDTO() {}

    public CorsoFormDTO(Corso corso) {
        this.id = corso.getId();
        this.nome = corso.getNome();
        this.annoAccademico = corso.getAnnoAccademico();
//        this.docente = corso.getDocente();
//        this.discenti = corso.getDiscenti();
//        this.docenteId = corso.getDocente() != null ? corso.getDocente().getId() : null;

//        if (corso.getDiscenti() != null) {
//            for (Discente d : corso.getDiscenti()) {
//                this.discentiIds.add(d.getId());
//            }
//        }
//
//        // Per update: prepara nome completo docente e lista nomi discenti
//        if (corso.getDocente() != null) {
//            this.docenteNomeCompleto = corso.getDocente().getNome() + " " + corso.getDocente().getCognome();
//        }
//        if (corso.getDiscenti() != null) {
//            for (Discente d : corso.getDiscenti()) {
//                this.nomiDiscenti.add(d.getNome() + " " + d.getCognome());
//            }
//        }
    }

    // getter e setter per tutti i campi

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnnoAccademico() {
        return annoAccademico;
    }

    public void setAnnoAccademico(Integer annoAccademico) {
        this.annoAccademico = annoAccademico;
    }

//    public Docente getDocente() {
//        return docente;
//    }
//
//    public void setDocente(Docente docente) {
//        this.docente = docente;
//    }
//
//    public List<Discente> getDiscenti() {
//        return discenti;
//    }
//
//    public void setDiscenti(List<Discente> discenti) {
//        this.discenti = discenti;
//    }
//
//    public Long getDocenteId() {
//        return docenteId;
//    }
//
//    public void setDocenteId(Long docenteId) {
//        this.docenteId = docenteId;
//    }
//
//    public List<Long> getDiscentiIds() {
//        return discentiIds;
//    }
//
//    public void setDiscentiIds(List<Long> discentiIds) {
//        this.discentiIds = discentiIds;
//    }
//
//    public String getDocenteNomeCompleto() {
//        return docenteNomeCompleto;
//    }
//
//    public void setDocenteNomeCompleto(String docenteNomeCompleto) {
//        this.docenteNomeCompleto = docenteNomeCompleto;
//    }
//
//    public List<String> getNomiDiscenti() {
//        return nomiDiscenti;
//    }
//
//    public void setNomiDiscenti(List<String> nomiDiscenti) {
//        this.nomiDiscenti = nomiDiscenti;
//    }
}
