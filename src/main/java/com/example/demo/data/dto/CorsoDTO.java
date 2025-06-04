
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


    public CorsoDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Integer getAnnoAccademico() { return annoAccademico; }
    public void setAnnoAccademico(Integer annoAccademico) { this.annoAccademico = annoAccademico; }

    public Long getId_docente() { return id_docente; }
    public void setId_docente(Long id_docente) { this.id_docente = id_docente; }

    public CorsoDTO(Corso corso) {
        this.id = corso.getId();
        this.nome = corso.getNome();
        this.annoAccademico = corso.getAnnoAccademico();
        this.id_docente = corso.getId_docente();
    }

}
