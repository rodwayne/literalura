package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthorRecord(
    @JsonAlias("name") String name,
    @JsonAlias("birth_year") int birthDate,
    @JsonAlias("death_year") int deathDate
) {

}
