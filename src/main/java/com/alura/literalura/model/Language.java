package com.alura.literalura.model;

public enum Language {
    ES("es"),
    FR("fr"),
    EN("en");

    private String language;

    Language(String language) {
        this.language = language;
    }

    public static Language fromString(String text) {
        for (Language language : Language.values()) {
            if (language.language.equalsIgnoreCase(text)) {
                return language;
            }
        }
        throw new IllegalArgumentException("No constant with " + text + " found");
    }

}
