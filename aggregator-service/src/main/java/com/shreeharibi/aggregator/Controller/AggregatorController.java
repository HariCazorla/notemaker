package com.shreeharibi.aggregator.Controller;

import com.shreeharibi.aggregator.Model.Comment;
import com.shreeharibi.aggregator.Model.Note;
import com.shreeharibi.aggregator.Service.AggregatorService;
import com.shreeharibi.notemaker.notes.NoteDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Main Controller Class Implementation
 */
@RestController
@RequestMapping("/api/v1/notemaker")
@Slf4j
public class AggregatorController {

    @Autowired
    public AggregatorService service;

    @GetMapping("health")
    public ResponseEntity<Boolean> getHealth() {
        return ResponseEntity.ok().body(true);
    }

    @PostMapping("add-note")
    public ResponseEntity<Boolean> addNewNote(
        @RequestBody Note note
    ) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/add-note").toUriString());
        return ResponseEntity.created(uri).body(service.createNote(note));
    }

    @PostMapping("add-comment")
    public ResponseEntity<Boolean> addNewCommentToNote(
            @RequestBody Comment comment
    ) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/add-comment").toUriString());
        return ResponseEntity.created(uri).body(service.addNewCommentToNote(comment));
    }

    @GetMapping("fetch-notes")
    public ResponseEntity<List<String>> fetchAllNotes() {
        List<String> notes = service.fetchAllNotes();
        if (notes != null) {
            return ResponseEntity.ok(notes);
        } else {
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
