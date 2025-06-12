package com.example.demo.repository;

import com.example.demo.data.entity.CorsoDiscente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CorsoDiscenteRepository extends JpaRepository<CorsoDiscente, Long> {

    void deleteByIdCorso(Long idCorso);

    List<CorsoDiscente> findByIdCorso(Long idCorso);
}
