package com.notion.service.core.controllers;

import com.notion.service.core.models.NoteLabel;
import com.notion.service.core.models.RequestStatusDTO;
import com.notion.service.core.services.LabelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "labels")
public class LabelsController {

    @Autowired
    private LabelsService labelsService;

    @GetMapping(value = "/all")
    public RequestStatusDTO<List<NoteLabel>> getAllLabels() {
        return labelsService.getAllLabels();
    }

    @PostMapping(value = "/create")
    public RequestStatusDTO<NoteLabel> createLabel(@RequestBody NoteLabel label) {
        return labelsService.createLabel(label);
    }

    @DeleteMapping(value = "/delete/{id}")
    public RequestStatusDTO<Void> deleteLabel(@PathVariable String id) {
        return labelsService.deleteLabel(id);
    }
}