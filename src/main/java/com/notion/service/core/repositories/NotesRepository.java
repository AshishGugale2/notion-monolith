package com.notion.service.core.repositories;

import com.notion.service.core.models.Note;
import com.notion.service.core.models.NoteStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotesRepository extends MongoRepository<Note, String> {
    Note findByTitle(String title);

    List<Note> findAllByStatus(NoteStatus status, Sort sort);
}
