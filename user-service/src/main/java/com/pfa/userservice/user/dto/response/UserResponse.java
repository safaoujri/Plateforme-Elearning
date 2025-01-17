package com.pfa.userservice.user.dto.response;

import com.pfa.userservice.user.entity.Role;

public record UserResponse(
        Long id,
        String nom,
        String prenom,
        String email,
        String motDePasse,
        String photoProfil,
        Role role
) {
}
