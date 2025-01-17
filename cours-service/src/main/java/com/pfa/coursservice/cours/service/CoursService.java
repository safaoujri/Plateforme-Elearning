package com.pfa.coursservice.cours.service;

import com.pfa.coursservice.cours.dto.request.CoursRequest;
import com.pfa.coursservice.cours.dto.response.CoursResponse;
import com.pfa.coursservice.cours.entity.Cours;
import com.pfa.coursservice.cours.mapper.CoursMapper;
import com.pfa.coursservice.cours.repository.CoursRepository;
import com.pfa.coursservice.exception.BusinessException;
import com.pfa.coursservice.user.UserClient;
import com.pfa.coursservice.user.UserResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoursService {

    private final CoursRepository repository;
    private final CoursMapper mapper;
    private final UserClient userClient;

    /**
     * Crée un cours avec les informations données dans la requête.
     *
     * @param request l'objet contenant les informations du cours
     * @return l'ID du cours créé
     */
    @Transactional
    public Long createCours(CoursRequest request) {
        // Récupérer l'utilisateur via le client (communication avec un autre service)
        UserResponse user = userClient.findUserById(request.getUserId())
                .orElseThrow(() -> new BusinessException("User not found"));

        // Convertir la requête en entité Cours et l'enregistrer dans la base de données
        Cours cours = mapper.toCours(request);
        cours.setUser(user); // Assurez-vous d'assigner l'utilisateur au cours si nécessaire

        // Enregistrer le cours dans la base de données
        cours = repository.save(cours);

        // Retourner l'ID du cours créé
        return cours.getId();
    }

    /**
     * Trouve tous les cours.
     *
     * @return une liste de réponses contenant les informations des cours
     */
    public List<CoursResponse> findAllCourses() {
        return repository.findAll()
                .stream()
                .map(mapper::fromCours)
                .collect(Collectors.toList());
    }

    /**
     * Trouve un cours par son ID.
     *
     * @param id l'ID du cours à rechercher
     * @return un objet de réponse contenant les informations du cours
     * @throws EntityNotFoundException si aucun cours n'est trouvé avec cet ID
     */
    public CoursResponse findById(Long id) {
        // Récupérer le cours par son ID
        Cours cours = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Cours with id %s not found", id)));

        // Récupérer les informations de l'utilisateur associé au cours
        UserResponse userResponse = userClient.findUserById(cours.getUserId()) // Récupérer l'utilisateur à partir de son ID
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id %s not found", cours.getUserId())));

        // Mapper l'entité Cours en CoursResponse
        CoursResponse response = mapper.fromCours(cours);

        // Ajouter l'utilisateur au CoursResponse
        response = new CoursResponse(
                response.id_cours(),
                response.titre(),
                response.description(),
                response.photoCours(),
                response.cheminVideo(),
                response.userId(),
                response.categoryId(),
                response.categoryName(),
                response.categoryDescription(),
                response.categoryUrlImage(),
                userResponse
        );

        return response;
    }

//        return repository.findById(id)
//                .map(mapper::fromCours)
//                .orElseThrow(() -> new EntityNotFoundException(String.format("Cours not found with id: %d", id)));

}