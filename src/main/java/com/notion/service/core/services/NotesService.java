package com.notion.service.core.services;

import com.notion.service.core.models.Note;
import com.notion.service.core.models.NoteStatus;
import com.notion.service.core.models.RequestStatusDTO;
import com.notion.service.core.models.RequestStatusEntries;
import com.notion.service.core.repositories.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NotesService {

    @Autowired
    private NotesRepository repository;

    public RequestStatusDTO<List<Note>> getAllActiveNotes(List<String> tags) {
        List<Note> allNotes = null;

        NoteStatus activeStatus = NoteStatus.ACTIVE;
        Sort recencySort = Sort.by(Sort.Direction.DESC, "createdAt");

        allNotes = tags == null || tags.isEmpty() ?
                repository.findAllByStatus(activeStatus, recencySort) :
                repository.findAllByTagsContainingAllAndStatus(tags, activeStatus, recencySort);

        if (allNotes == null || allNotes.isEmpty())
            return new RequestStatusDTO<>(RequestStatusEntries.NO_NOTES, null);
        return new RequestStatusDTO<>(RequestStatusEntries.SUCCESS, allNotes);
    }

    public RequestStatusDTO<List<Note>> getAllTrashNotes() {
        List<Note> allTrashNotes = repository.findAllByStatus(
                NoteStatus.TRASH,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
        if (allTrashNotes.isEmpty())
            return new RequestStatusDTO<>(RequestStatusEntries.NO_NOTES, null);
        return new RequestStatusDTO<>(RequestStatusEntries.SUCCESS, allTrashNotes);
    }

    public RequestStatusDTO<Note> createNote(Note entry) {
        try {
            Note createdNote = repository.save(entry);
            return new RequestStatusDTO<>(RequestStatusEntries.SUCCESS, createdNote);
        } catch (DataAccessException exception) {
            return new RequestStatusDTO<>(RequestStatusEntries.FAILED_CREATE, null);
        }
    }

    public RequestStatusDTO<Void> moveToTrash(@PathVariable String id) {
        try {
            Optional<Note> existingNote = repository.findById(id);

            if (existingNote.isEmpty()) {
                return new RequestStatusDTO<>(RequestStatusEntries.NOT_FOUND, null);
            }

            Note originalNote = existingNote.get();
            originalNote.setStatus(NoteStatus.TRASH);

            repository.save(originalNote);

            return new RequestStatusDTO<>(RequestStatusEntries.TRASHED, null);
        } catch (DataAccessException exception) {
            return new RequestStatusDTO<>(RequestStatusEntries.FAILED_TRASH, null);
        }
    }

    public RequestStatusDTO<Note> updateNote(String id, Note entry) {
        try {
            Optional<Note> existingEntryOptional = repository.findById(id);

            if (existingEntryOptional.isEmpty()) {
                return new RequestStatusDTO<>(RequestStatusEntries.NOT_FOUND, null);
            }

            Note updatedEntry = updateExistingNote(existingEntryOptional.get(), entry);

            Note savedUpdatedEntry = repository.save(updatedEntry);

            return new RequestStatusDTO<>(RequestStatusEntries.SUCCESS, savedUpdatedEntry);
        } catch (DataAccessException exception) {
            return new RequestStatusDTO<>(RequestStatusEntries.FAILED_UPDATE, null);
        }
    }

    public RequestStatusDTO<Void> deleteNote(String id) {
        try {
            return repository.findById(id)
                    .map(note -> {
                        repository.delete(note);
                        return new RequestStatusDTO<Void>(RequestStatusEntries.SUCCESS, null);
                    })
                    .orElseGet(() -> new RequestStatusDTO<>(RequestStatusEntries.NOT_FOUND, null));
        } catch (DataAccessException exception) {
            return new RequestStatusDTO<>(RequestStatusEntries.FAILED_DELETE, null);
        }
    }

    private Note updateExistingNote(Note existingEntry, Note entry) {
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

        return existingEntry;
    }
}
