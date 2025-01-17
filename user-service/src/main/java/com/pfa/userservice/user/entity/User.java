    package com.pfa.userservice.user.entity;

    import jakarta.persistence.*;
    import lombok.*;


    @Entity
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)

        private Long id;
        private String nom;
        private String prenom;
        private String email;
        private String motDePasse;
        private String photoProfil;
        private String niveauEtudes;
        @Version
        private Long version;




        @Enumerated(EnumType.STRING)
        private Role role;







    }
