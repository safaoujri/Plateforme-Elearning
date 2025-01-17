package com.pfa.userservice.user.dto.request;

import com.pfa.userservice.user.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UserRequest(
//        @NotNull(message = "User ID must not be null")
        Long id,
        @NotNull(message = "User nom is required")
        String nom,
        @NotNull(message = "User prenom is required")
        String prenom,
        @Email(message = "User Email is not valid email adress")
        String email,
        @NotNull(message = "Password is required")
        String motDePasse,

        String photoProfil,
        @NotNull(message = "Niveau is required")
        String niveauEtudes,
        @NotNull(message = "Role is required")
        Role role
) {
}
