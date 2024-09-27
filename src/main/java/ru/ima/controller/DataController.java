package ru.ima.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class DataController {
    @RequestMapping
    public ResponseEntity getData() {
        return ResponseEntity.ok("Hello!✨");
    }
}