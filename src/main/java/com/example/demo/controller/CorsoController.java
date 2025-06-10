package com.example.demo.controller;

import com.example.demo.data.dto.CorsoDTO;
import com.example.demo.data.dto.CorsoFormDTO;
import com.example.demo.service.CorsoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/corsi")
public class CorsoController {

    @Autowired
    private CorsoService corsoService;

    @GetMapping("/lista")
    public ResponseEntity<List<CorsoDTO>> getAllCorsi() {
        try {
            List<CorsoDTO> corsi = corsoService.getAllCorsiDTO();
            return ResponseEntity.ok(corsi);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CorsoDTO> getCorso(@PathVariable Long id) {
        try {
            CorsoDTO corso = corsoService.getCorsoById(id);
            return ResponseEntity.ok(corso);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/nuovo")
    public ResponseEntity<CorsoDTO> create(@RequestBody CorsoFormDTO corsoDTO) {
        try {
            CorsoDTO corso = corsoService.saveCorso(corsoDTO);
            return ResponseEntity.ok(corso);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<CorsoDTO> updateCorso(@PathVariable Long id, @RequestBody CorsoFormDTO corsoFormDTO) {
        try {
            CorsoDTO updateCorso = corsoService.updateCorso(id, corsoFormDTO);
            return ResponseEntity.ok(updateCorso);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            corsoService.deleteCorso(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}