package com.pfa.coursservice.categorie;

import com.pfa.coursservice.cours.entity.Cours;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private String description;

    private String imageUrl;
    @OneToMany(mappedBy = "category" ,cascade = CascadeType.REMOVE)
    private List<Cours> cours;
}
