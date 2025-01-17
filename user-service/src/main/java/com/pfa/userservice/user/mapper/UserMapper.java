package com.pfa.userservice.user.mapper;

import com.pfa.userservice.user.dto.request.UserRequest;
import com.pfa.userservice.user.dto.response.UserResponse;
import com.pfa.userservice.user.entity.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public User toUser(@Valid UserRequest request) {
        if (request == null) {
            return null;
        }
        return User.builder()
                .id(request.id())
                .nom(request.nom())
                .email(request.email())
                .motDePasse(request.motDePasse())
                .niveauEtudes(request.niveauEtudes())
                .photoProfil(request.photoProfil())
                .role(request.role())
                .build();
    }
    public UserResponse fromUser(@Valid User user) {
        return new UserResponse(
                user.getId(),
                user.getNom(),
                user.getPrenom(),
                user.getEmail(),
                user.getMotDePasse(),
                user.getNiveauEtudes(),
                user.getRole()
        );
    }
}
