package com.example.demo.data.dto;

public class CorsoFormDTO {

    private String nome;
    private Integer annoAccademico;
    private Long id_docente;

    public CorsoFormDTO() {}



    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Integer getAnnoAccademico() { return annoAccademico; }
    public void setAnnoAccademico(Integer annoAccademico) { this.annoAccademico = annoAccademico; }

    public Long getId_docente() { return id_docente; }
    public void setId_docente(Long id_docente) { this.id_docente = id_docente; }
}
