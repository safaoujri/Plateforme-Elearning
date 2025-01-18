package com.pfa.userservice.user.controller;

import com.pfa.userservice.user.dto.request.UserRequest;
import com.pfa.userservice.user.dto.response.UserResponse;
import com.pfa.userservice.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService service;
    public UserController(UserService service) {
        this.service = service;
    }


    @PostMapping("/cree")
    public ResponseEntity<Long> createUser(@RequestBody @Valid UserRequest request){
        return ResponseEntity.ok(service.createUser(request));
    }
    @PutMapping("/update")
    public ResponseEntity<Void> updateUser(@RequestBody @Valid UserRequest request){
        service.updateUser(request);
        return ResponseEntity.accepted().build();
    }
    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> findAll(){
        return ResponseEntity.ok(service.findAllUsers());
    }
    @GetMapping("/{user-id}")
    public ResponseEntity<UserResponse> findById(@PathVariable("user-id") Long userId) {
        return ResponseEntity.ok(service.findById(userId));
    }
    @DeleteMapping("/{user-id}")
    public ResponseEntity<Void> deleteById(@PathVariable("user-id") Long userId) {
        this.service.deleteUser(userId);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/{user-id}/uploadPhoto")
    public ResponseEntity<String> uploadPhoto(@PathVariable("user-id") Long id, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty() || !file.getContentType().startsWith("image/")) {
            return ResponseEntity.badRequest().body("Invalid file. Only image files are allowed.");
        }

        try {
            service.saveUserPhoto(id, file);
            return ResponseEntity.ok("Photo uploaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to upload photo");
        }
    }

    @GetMapping("/{user-id}/photo")
    public ResponseEntity<FileSystemResource> getUserPhoto(@PathVariable("user-id") Long id) {
        try {
            String photoPath = service.getUserPhotoPath(id);
            File photoFile = new File(photoPath);

            if (photoFile.exists()) {
                FileSystemResource resource = new FileSystemResource(photoFile);
                return ResponseEntity.ok()
                        .header("Content-Type", Files.probeContentType(photoFile.toPath()))  // Détecte le type MIME de l'image
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}
