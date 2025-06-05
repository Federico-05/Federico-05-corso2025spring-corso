
package com.example.demo.data.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "corso")
public class Corso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "nome")
    private String nome;

    @Column(name = "anno_accademico", nullable = false)
    private Integer annoAccademico;

    @Column
    private Long id_docente;
//
//    @ManyToMany
//    @JoinTable(
//            name = "corso_discente",
//            joinColumns = @JoinColumn(name = "id_corso"),
//            inverseJoinColumns = @JoinColumn(name = "id_discente")
//    )
//    private List<Discente> discenti;


    public Corso() {
    }

    public Corso(String nome, Integer annoAccademico, Long id_docente) {
        this.nome = nome;
        this.annoAccademico = annoAccademico;
        this.id_docente = id_docente;
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
}