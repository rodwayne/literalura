package com.alura.literalura.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookRecord(
    @JsonAlias("title") String title,
    @JsonAlias("authors") List<AuthorRecord> author,
    @JsonAlias("languages") List<String> availableLanguages,
    @JsonAlias("download_count") Double downloads
) {

}
