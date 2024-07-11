package com.alura.literalura.model;

import java.util.List;

import com.alura.literalura.entities.Author;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookRecord(
    @JsonAlias("title") String title,
    @JsonAlias("name") Author author,
    @JsonAlias("languages") List<String> availableLanguages,
    @JsonAlias("download_count") Integer downloads
) {

}
