package com.pfa.coursservice.cours.dto.response;


import com.pfa.coursservice.user.UserResponse;

public record CoursResponse(
        Long id_cours,
        String titre,
        String description,
        String photoCours,
        String cheminVideo,
        Long userId,
//        String usernom,
//        String userprenom,
//        String useremail,
//        String usermotDePasse,
//        String photoProfil,
//        Role role,
        Long categoryId,
        String categoryName,
        String categoryDescription,
        String categoryUrlImage,
        UserResponse user
) {

}
