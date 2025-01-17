package com.pfa.coursservice.cours.repository;

import com.pfa.coursservice.cours.entity.Cours;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursRepository extends JpaRepository<Cours, Long> {
}
