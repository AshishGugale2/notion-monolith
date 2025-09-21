package com.notion.service.core.controllers;

import com.notion.service.core.models.Note;
import com.notion.service.core.models.NoteStatus;
import com.notion.service.core.repositories.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

// TODO: Return a status DTO to be displayed as a toast in the UI instead of returning the entire object / void
@RestController(value = "notes")
public class NotesController {
    @Autowired
    private NotesRepository repository;

    @GetMapping(value = "/listAll")
    public List<Note> getAllActiveNotes() {
        return repository.findAllByStatus(
                NoteStatus.ACTIVE,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
    }

    @GetMapping(value = "/listAllTrash")
    public List<Note> getAllTrashNotes() {
        return repository.findAllByStatus(
                NoteStatus.TRASH,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
    }

    @PostMapping(value = "/create")
    public Note createNote(@RequestBody Note entry) {
        return repository.save(entry);
    }

    @PatchMapping(value = "/trash/{id}")
    public void moveToTrash(@PathVariable String id) {
        Optional<Note> existingNote = repository.findById(id);

        if (existingNote.isEmpty()) {
            return;
        }

        Note originalNote = existingNote.get();
        originalNote.setStatus(NoteStatus.TRASH);
        repository.save(originalNote);
    }

    @PatchMapping(value = "/edit/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable String id, @RequestBody Note entry) {
        Optional<Note> existingEntryOptional = repository.findById(id);

        if (existingEntryOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Note existingEntry = existingEntryOptional.get();

        if (entry.getContent() != null)
            existingEntry.setContent(entry.getContent());
        if (entry.getTitle() != null)
            existingEntry.setTitle(entry.getTitle());
        if (entry.getDescription() != null)
            existingEntry.setDescription(entry.getDescription());
        if (entry.getTags() != null)
            existingEntry.setTags(entry.getTags());
        if (entry.getColor() != null)
            existingEntry.setColor(entry.getColor());
        if (entry.getStatus() != null)
            existingEntry.setStatus(entry.getStatus());

        Date currentDate = new Date();
        existingEntry.setLastUpdatedOn(currentDate);

        Note updatedEntry = repository.save(existingEntry);
        return ResponseEntity.ok(updatedEntry);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable String id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
