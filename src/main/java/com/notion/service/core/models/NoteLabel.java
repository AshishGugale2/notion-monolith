package com.notion.service.core.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "entry_labels")
public class NoteLabel {
    @Id
    private String id;
    private String label_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel_name() {
        return label_name;
    }

    public void setLabel_name(String label_name) {
        this.label_name = label_name;
    }
}
