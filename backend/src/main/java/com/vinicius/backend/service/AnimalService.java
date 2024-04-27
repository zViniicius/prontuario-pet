package com.vinicius.backend.service;

import com.vinicius.backend.dto.request.AnimalRequest;
import com.vinicius.backend.dto.response.AnimalResponse;
import org.springframework.data.domain.Page;

public interface AnimalService {
    AnimalResponse createAnimal(AnimalRequest animalRequest);

    AnimalResponse getAnimalById(Long id);

    Page<AnimalResponse> getAllAnimals(Integer page,
                                       Integer size);

    AnimalResponse updateAnimal(Long id,
                                AnimalRequest animalRequest);

    void deleteAnimal(Long id);
}
