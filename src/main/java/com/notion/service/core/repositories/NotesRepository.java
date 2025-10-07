package com.notion.service.core.repositories;

import com.notion.service.core.models.Note;
import com.notion.service.core.models.NoteStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface NotesRepository extends MongoRepository<Note, String> {
    Note findByTitle(String title);

    List<Note> findAllByStatus(NoteStatus status, Sort sort);

    @Query("{ 'tags': { $all: ?0 }, 'status': ?1 }")
    List<Note> findAllByTagsContainingAllAndStatus(List<String> tags, NoteStatus status, Sort sort);
}
