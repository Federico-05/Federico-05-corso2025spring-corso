package com.example.demo.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DiscenteDTO {
    private Long id;
    private String nome;
    private String cognome;
    private Integer eta;
    private String cittaResidenza;

    public DiscenteDTO() {}

    public DiscenteDTO(String nome, String cognome, Integer eta, String cittaResidenza) {
        this.nome = nome;
        this.cognome = cognome;
        this.eta = eta;
        this.cittaResidenza = cittaResidenza;
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

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    @JsonIgnore
    public Integer getEta() {
        return eta;
    }

    public void setEta(Integer eta) {
        this.eta = eta;
    }
    @JsonIgnore
    public String getCittaResidenza() {
        return cittaResidenza;
    }

    public void setCittaResidenza(String cittaResidenza) {
        this.cittaResidenza = cittaResidenza;
    }

    @Override
    public String toString() {
        return "DiscenteDTO{" +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", eta=" + eta +
                ", cittaResidenza='" + cittaResidenza + '\'' +
                '}';
    }
}
