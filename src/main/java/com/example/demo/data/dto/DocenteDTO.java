package com.example.demo.data.dto;

public class DocenteDTO {

    private Long id;
    private String nome;
    private String cognome;
    private String email;

    // Costruttore vuoto necessario per la deserializzazione JSON
    public DocenteDTO() {
    }

    // Costruttore completo
    public DocenteDTO(Long id, String nome, String cognome, String email) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }

    // Costruttore per creazione (senza id ed email opzionale)
    public DocenteDTO(String nome, String cognome) {
        this.nome = nome;
        this.cognome = cognome;
    }

    // Getter e setter
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

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}