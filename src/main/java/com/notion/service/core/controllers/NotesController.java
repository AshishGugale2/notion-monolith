package com.notion.service.core.controllers;

import com.notion.service.core.models.Note;
import com.notion.service.core.models.RequestStatusDTO;
import com.notion.service.core.repositories.NotesRepository;
import com.notion.service.core.services.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "notes")
public class NotesController {

    private final NotesService service;

    @Autowired
    private NotesRepository repository;

    public NotesController(NotesService service) {
        this.service = service;
    }

    @GetMapping(value = "/listAll")
    public RequestStatusDTO<List<Note>> getAllActiveNotes(@RequestParam(required = false) List<String> tags) {
        return service.getAllActiveNotes(tags);
    }

    @GetMapping(value = "/listAllTrash")
    public RequestStatusDTO<List<Note>> getAllTrashNotes() {
        return service.getAllTrashNotes();
    }

    @PostMapping(value = "/create")
    public RequestStatusDTO<Note> createNote(@RequestBody Note entry) {
        return service.createNote(entry);
    }

    @PatchMapping(value = "/trash/{id}")
    public RequestStatusDTO<Void> moveToTrash(@PathVariable String id) {
        return service.moveToTrash(id);
    }

    @PatchMapping(value = "/edit/{id}")
    public RequestStatusDTO<Note> updateNote(@PathVariable String id, @RequestBody Note entry) {
        return service.updateNote(id, entry);
    }

    @DeleteMapping(value = "/delete/{id}")
    public RequestStatusDTO<Void> deleteNote(@PathVariable String id) {
        return service.deleteNote(id);
    }
}
