package com.alura.literalura.service;

public interface IConvertResponse {
    <T> T fetch(String json, Class<T> genericClass);
}
