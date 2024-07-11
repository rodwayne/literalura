package com.alura.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertResponse implements IConvertResponse {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T fetch(String json, Class<T> genericClass) {
        try {
            return objectMapper.readValue(json, genericClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    
}
