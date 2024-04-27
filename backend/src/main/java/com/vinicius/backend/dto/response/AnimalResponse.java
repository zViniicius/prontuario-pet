package com.vinicius.backend.dto.response;

public record AnimalResponse(Long id, String name, String species, String breed, String color, String birthDate, String tutorIdNumber, String tutorName) {}
