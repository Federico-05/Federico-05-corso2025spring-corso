
package com.example.demo.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CorsoFormDTO {
    private String nome;
    private Integer annoAccademico;
    private String nomeDocente;
    private String cognomeDocente;
    private List<DiscenteDTO> discenti;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Integer getAnnoAccademico() { return annoAccademico; }
    public void setAnnoAccademico(Integer annoAccademico) { this.annoAccademico = annoAccademico; }

    public String getNomeDocente() { return nomeDocente; }
    public void setNomeDocente(String nomeDocente) { this.nomeDocente = nomeDocente; }

    public String getCognomeDocente() { return cognomeDocente; }
    public void setCognomeDocente(String cognomeDocente) { this.cognomeDocente = cognomeDocente; }

    public List<DiscenteDTO> getDiscenti() { return discenti; }
    public void setDiscenti(List<DiscenteDTO> discenti) { this.discenti = discenti; }
}
