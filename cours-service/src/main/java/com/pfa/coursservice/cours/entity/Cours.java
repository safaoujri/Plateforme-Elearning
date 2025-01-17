package com.pfa.coursservice.cours.entity;

import com.pfa.coursservice.categorie.Category;
import com.pfa.coursservice.user.UserResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.Version;

@Entity
@Data
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Builder
@Table(name = "user_cours")
public class Cours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_cours;
    private String titre;
    private String description;
    private String photoCours;
    private String cheminVideo; // Stocker le chemin de la vidéo

    private Long userId;
    @Version
    private Long version;


    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;



    public void setUser(UserResponse user) {
        if (user != null) {
            this.userId = user.getId();  // Mettre à jour l'ID utilisateur à partir du DTO
        }
    }

    public Long getId() {
        return id_cours;
    }



}
