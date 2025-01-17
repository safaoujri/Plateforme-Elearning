package com.pfa.coursservice.user;

public record UserResponse(
        Long id,
        String nom,
        String prenom,
        String email,
        String motDePasse,
        String photoProfil,
        String niveauEtudes
) {


    public Long getId() {
        return id();
    }

}
