package com.example.demo.data.dto;

public class DocenteDTO {
    private Long id;
    private String nome;
    private String cognome;

    public DocenteDTO() {}

    public DocenteDTO(String nome, String cognome) {
        this.nome = nome;
        this.cognome = cognome;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
}

