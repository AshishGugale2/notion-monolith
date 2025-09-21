package com.notion.service.core.controllers;

import com.notion.service.core.models.NoteLabel;
import com.notion.service.core.repositories.NoteLabelsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO: Return a status DTO to be displayed as a toast in the UI instead of returning the entire object / void
@RestController(value = "labels")
public class LabelsController {
    @Autowired
    private NoteLabelsRepository repository;

    @GetMapping(value = "/all")
    public List<NoteLabel> getAllLabels() {
        return repository.findAll();
    }

    @PostMapping(value = "create")
    public void createLabel(@RequestBody NoteLabel label) {
        repository.save(label);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> deleteLabel(@PathVariable String id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
