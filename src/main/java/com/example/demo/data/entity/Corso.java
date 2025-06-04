
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


    public Corso() {}

    public Corso(String nome, Integer annoAccademico,Long id_docente) {
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
//
//    public List<Discente> getDiscenti() {
//        return discenti;
//    }
//
//    public void setDiscenti(List<Discente> discenti) {
//        this.discenti = discenti;
//    }
}
