package com.example.demo.data.dto;

import com.example.demo.data.entity.Corso;

public class CorsoDTO {

    private Long id;
    private String nome;
    private Integer annoAccademico;
    private Long id_docente;
    private String NomeDocente;
    private String CognomeDocente;

    public CorsoDTO() {}

    public CorsoDTO(Corso corso) {
        this.id = corso.getId();
        this.nome = corso.getNome();
        this.annoAccademico = corso.getAnnoAccademico();
        this.id_docente = corso.getId_docente();
    }

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

    public Long getId_docente() {
        return id_docente;
    }
    public void setId_docente(Long id_docente) {
        this.id_docente = id_docente;
    }

    public String getNomeDocente() {
        return NomeDocente;
    }
    public void setNomeDocente(String nomeDocente) {
        this.NomeDocente = nomeDocente;
    }

    public String getCognomeDocente() {
        return CognomeDocente;
    }
    public void setCognomeDocente(String cognomeDocente) {
        this.CognomeDocente = cognomeDocente;
    }
}
