package com.shreeharibi.aggregator.Controller;

import com.shreeharibi.aggregator.Model.Note;
import com.shreeharibi.aggregator.Service.AggregatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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

    @PostMapping("add")
    public ResponseEntity<Boolean> addNewNote(
        @RequestBody Note note
    ) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/add").toUriString());
        return ResponseEntity.created(uri).body(service.createNote(note));
    }

}
