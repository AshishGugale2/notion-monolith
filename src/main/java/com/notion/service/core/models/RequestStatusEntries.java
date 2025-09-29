package com.notion.service.core.models;

public enum RequestStatusEntries {
    SUCCESS("Request completed successfully"),
    ERROR("Something went wrong"),
    NOT_FOUND("The requested resource was not found"),
    UNAUTHORIZED("You are not authorized to perform this action"),
    VALIDATION_FAILED("Validation failed for the request"),

    // listAll | listAllTrash
    NO_NOTES("Seems pretty empty here eh?"),

    // create
    FAILED_CREATE("Failed to create the note"),
    // trash
    TRASHED("Note trashed successfully"),
    FAILED_TRASH("Failed to trash this note"),

    // update
    FAILED_UPDATE("Failed to update the note"),

    // delete
    FAILED_DELETE("Failed to delete the note");

    private final String message;

    RequestStatusEntries(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
