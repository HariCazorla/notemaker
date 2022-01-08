package com.shreeharibi.aggregator.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notemaker")
@Slf4j
public class AggregatorController {

    @GetMapping("health")
    public ResponseEntity<Boolean> getHealth() {
        return ResponseEntity.ok().body(true);
    }

}