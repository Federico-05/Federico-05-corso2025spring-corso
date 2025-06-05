package com.example.demo.data.dto;

import com.example.demo.data.entity.Corso;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class CorsoDTO {

    private Long id;
    private String nome;
    private Integer annoAccademico;
    private Long id_docente;
    private String nomeDocenteCompleto;

    public CorsoDTO() {}

    public CorsoDTO(Corso corso) {
        this.id = corso.getId();
        this.nome = corso.getNome();
        this.annoAccademico = corso.getAnnoAccademico();
        this.id_docente = corso.getId_docente();
    }

    // getter e setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Integer getAnnoAccademico() { return annoAccademico; }
    public void setAnnoAccademico(Integer annoAccademico) { this.annoAccademico = annoAccademico; }

    @JsonIgnore
    public Long getId_docente() { return id_docente; }
    @JsonIgnore
    public void setId_docente(Long id_docente) { this.id_docente = id_docente; }

    public String getNomeDocenteCompleto() { return nomeDocenteCompleto; }
    public void setNomeDocenteCompleto(String nomeDocenteCompleto) { this.nomeDocenteCompleto = nomeDocenteCompleto; }
}
