package com.shreeharibi.aggregator.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class to model comment
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private String noteId;
    private String comment;

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
