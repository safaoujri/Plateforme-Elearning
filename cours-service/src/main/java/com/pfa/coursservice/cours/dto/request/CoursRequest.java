package com.pfa.coursservice.cours.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)

public record CoursRequest(
         Long id_cours,
         String titre,
         String description,
         String photoCours,
         String cheminVideo,
         Long userId,
         Long categoryId

) {
    public Long getUserId() {
        return userId;
    }
}
