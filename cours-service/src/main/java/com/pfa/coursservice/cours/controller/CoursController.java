package com.pfa.coursservice.cours.controller;

import com.pfa.coursservice.cours.dto.request.CoursRequest;
import com.pfa.coursservice.cours.dto.response.CoursResponse;
import com.pfa.coursservice.cours.service.CoursService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cours")
@RequiredArgsConstructor
public class CoursController {

    private final CoursService service;

    @PostMapping("/create")
    public ResponseEntity<Long> createCours(@RequestBody @Valid CoursRequest request) {
        Long courseId = service.createCours(request);
        return ResponseEntity.status(201).body(courseId);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CoursResponse>> findAll() {
        return ResponseEntity.ok(service.findAllCourses());
    }
    @GetMapping("/{cours-id}")
    public ResponseEntity<CoursResponse> findById(@PathVariable("cours-id") Long coursId) {
        return ResponseEntity.ok(service.findById(coursId));
    }
}
