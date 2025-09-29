package com.notion.service.core.services;

import com.notion.service.core.models.NoteLabel;
import com.notion.service.core.models.RequestStatusDTO;
import com.notion.service.core.models.RequestStatusEntries;
import com.notion.service.core.repositories.NoteLabelsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelsService {

    @Autowired
    private NoteLabelsRepository repository;

    public RequestStatusDTO<List<NoteLabel>> getAllLabels() {
        List<NoteLabel> labels = repository.findAll();
        if (labels.isEmpty()) {
            return new RequestStatusDTO<>(RequestStatusEntries.NO_NOTES, labels);
        }
        return new RequestStatusDTO<>(RequestStatusEntries.SUCCESS, labels);
    }

    public RequestStatusDTO<NoteLabel> createLabel(NoteLabel label) {
        try {
            NoteLabel savedLabel = repository.save(label);
            return new RequestStatusDTO<>(RequestStatusEntries.SUCCESS, savedLabel);
        } catch (DataAccessException e) {
            return new RequestStatusDTO<>(RequestStatusEntries.FAILED_CREATE, null);
        }
    }

    public RequestStatusDTO<Void> deleteLabel(String id) {
        try {
            return repository.findById(id)
                    .map(label -> {
                        repository.delete(label);
                        return new RequestStatusDTO<Void>(RequestStatusEntries.SUCCESS, null);
                    })
                    .orElseGet(() -> new RequestStatusDTO<>(RequestStatusEntries.NOT_FOUND, null));
        } catch (DataAccessException e) {
            return new RequestStatusDTO<>(RequestStatusEntries.FAILED_DELETE, null);
        }
    }
}