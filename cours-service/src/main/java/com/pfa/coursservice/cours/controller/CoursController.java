package com.pfa.coursservice.cours.controller;

import com.pfa.coursservice.cours.dto.request.CoursRequest;
import com.pfa.coursservice.cours.dto.response.CoursResponse;
import com.pfa.coursservice.cours.service.CoursService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/api/cours")
public class CoursController {

    private final CoursService service;

    public CoursController(CoursService service) {
        this.service = service;
    }

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

    @GetMapping("/{cours-id}/video")
    public ResponseEntity<FileSystemResource> getCoursVideo(@PathVariable("cours-id") Long coursId) {
        try {
            String videoPath = service.getCheminVideoById(coursId); // Récupérer le chemin de la vidéo
            File videoFile = new File(videoPath);

            if (videoFile.exists()) {
                // Retourner la vidéo en réponse HTTP
                FileSystemResource resource = new FileSystemResource(videoFile);
                return ResponseEntity.ok()
                        .header("Content-Type", "video/mp4")  // Définir le type de contenu vidéo
                        .body(resource);
            } else {
                // Si la vidéo n'existe pas, renvoyer une erreur
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Loguer l'exception
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
    // Méthode pour uploader une vidéo
    @PostMapping("/{cours-id}/upload")
    public ResponseEntity<String> uploadCoursVideo(@PathVariable("cours-id") Long coursId, @RequestParam("file") MultipartFile file) {
        try {
            // Définir le chemin de stockage pour la vidéo
            String directory = "C:/Users/user/Documents";  // Remplacer par le chemin de stockage sur votre serveur
            File uploadDir = new File(directory);

            // Créer le répertoire s'il n'existe pas
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Définir le nom du fichier vidéo
            String fileName = coursId + "_video.mp4";  // Vous pouvez ajouter un mécanisme pour générer un nom unique

            // Sauvegarder le fichier vidéo dans le répertoire
            Path filePath = Path.of(directory, fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Enregistrer le chemin du fichier vidéo dans la base de données si nécessaire
            service.saveVideoPath(coursId, filePath.toString());

            return ResponseEntity.ok("Vidéo téléchargée avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur lors de l'upload de la vidéo.");
        }
    }


}
