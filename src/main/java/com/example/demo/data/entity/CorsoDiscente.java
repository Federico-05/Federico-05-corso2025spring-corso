package com.example.demo.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "corso_discente")
public class CorsoDiscente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_corso", nullable = false)
    private Long idCorso;

    @Column(name = "id_discente", nullable = false)
    private Long idDiscente;

    public CorsoDiscente() {
    }

    public CorsoDiscente(Long idCorso, Long idDiscente) {
        this.idCorso = idCorso;
        this.idDiscente = idDiscente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCorso() {
        return idCorso;
    }

    public void setIdCorso(Long idCorso) {
        this.idCorso = idCorso;
    }

    public Long getIdDiscente() {
        return idDiscente;
    }

    public void setIdDiscente(Long idDiscente) {
        this.idDiscente = idDiscente;
    }
}
