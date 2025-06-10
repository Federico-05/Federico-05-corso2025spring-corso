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
        List<CorsoDTO> corsi = corsoService.getAllCorsiDTO();
        return ResponseEntity.ok(corsi);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CorsoDTO> getCorso(@PathVariable Long id) {
        CorsoDTO corso = corsoService.getCorsoById(id);
        return corso != null ? ResponseEntity.ok(corso) : ResponseEntity.notFound().build();
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
    public ResponseEntity<CorsoDTO> updateCorso(@PathVariable Long id, @RequestBody CorsoFormDTO CorsoFormDTO) {
        CorsoDTO updateCorso = corsoService.updateCorso(id, CorsoFormDTO);
        return ResponseEntity.ok(updateCorso);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        corsoService.deleteCorso(id);
        return ResponseEntity.noContent().build();
    }
}