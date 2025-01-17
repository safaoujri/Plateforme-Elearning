package com.pfa.coursservice.cours.mapper;

import com.pfa.coursservice.categorie.Category;
import com.pfa.coursservice.cours.dto.request.CoursRequest;
import com.pfa.coursservice.cours.dto.response.CoursResponse;
import com.pfa.coursservice.cours.entity.Cours;
import com.pfa.coursservice.user.UserResponse;
import com.pfa.coursservice.user.UserClient;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoursMapper {

    @Autowired
    private UserClient userClient; // Injection du client utilisateur

    public Cours toCours(CoursRequest request){
        if(request == null){ return null;}
        return Cours.builder()
                .id_cours(request.id_cours())
                .photoCours(request.photoCours())
                .userId(request.userId())
                .titre(request.titre())
                .description(request.description())
                .category(
                        Category.builder()
                                .id(request.categoryId()).build()
                )
                .build();
    }

    public CoursResponse fromCours(Cours cours){
        // Récupérer l'utilisateur associé à ce cours
        UserResponse userResponse = userClient.findUserById(cours.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Mapper l'entité Cours en CoursResponse et ajouter l'objet UserResponse
        return new CoursResponse(
                cours.getId_cours(),
                cours.getPhotoCours(),
                cours.getDescription(),
                cours.getTitre(),
                cours.getCheminVideo(),
                cours.getUserId(),
                cours.getCategory().getId(),
                cours.getCategory().getNom(),
                cours.getCategory().getDescription(),
                cours.getCategory().getImageUrl(),
                userResponse // Ajouter ici l'objet UserResponse
        );
    }
}
