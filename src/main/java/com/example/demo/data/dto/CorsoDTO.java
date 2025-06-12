// CorsoDTO.java
package com.example.demo.data.dto;

import com.example.demo.data.entity.Corso;
import java.util.List;

public class CorsoDTO {
    private Long id;
    private String nome;
    private Integer annoAccademico;
    private String nomeDocenteCompleto;

    // Lista discenti associati al corso
    private List<DiscenteDTO> discenti;

    public CorsoDTO() {}

    public CorsoDTO(Corso corso) {
        this.id = corso.getId();
        this.nome = corso.getNome();
        this.annoAccademico = corso.getAnnoAccademico();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Integer getAnnoAccademico() { return annoAccademico; }
    public void setAnnoAccademico(Integer annoAccademico) { this.annoAccademico = annoAccademico; }

    public String getNomeDocenteCompleto() { return nomeDocenteCompleto; }
    public void setNomeDocenteCompleto(String nomeDocenteCompleto) { this.nomeDocenteCompleto = nomeDocenteCompleto; }

    public List<DiscenteDTO> getDiscenti() {
        return discenti;
    }

    public void setDiscenti(List<DiscenteDTO> discenti) {
        this.discenti = discenti;
    }
}
