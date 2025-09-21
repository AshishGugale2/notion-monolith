package com.notion.service.core.repositories;

import com.notion.service.core.models.NoteLabel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NoteLabelsRepository extends MongoRepository<NoteLabel, String> {
}
