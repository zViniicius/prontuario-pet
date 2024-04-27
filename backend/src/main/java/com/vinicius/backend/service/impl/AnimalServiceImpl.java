package com.vinicius.backend.service.impl;

import com.vinicius.backend.domain.Animal;
import com.vinicius.backend.domain.Tutor;
import com.vinicius.backend.dto.request.AnimalRequest;
import com.vinicius.backend.dto.response.AnimalResponse;
import com.vinicius.backend.repository.AnimalRepository;
import com.vinicius.backend.service.AnimalService;
import com.vinicius.backend.service.TutorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final TutorService tutorService;

    public AnimalServiceImpl(AnimalRepository animalRepository,
                             TutorService tutorService) {
        this.animalRepository = animalRepository;
        this.tutorService = tutorService;
    }

    @Override
    public AnimalResponse createAnimal(AnimalRequest animalRequest) {
        Tutor tutor = tutorService.getTutorByIdNumber(animalRequest.getTutorIdNumber());
        if (tutor == null) {
            throw new IllegalArgumentException("Não foi possível encontrar o tutor com o número de identificação " + animalRequest.getTutorIdNumber());
        }

        Animal animal = toEntity(animalRequest, tutor);

        animalRepository.save(animal);
        return toResponse(animal);
    }

    @Override
    public AnimalResponse getAnimalById(Long id) {
        return animalRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Não foi possível encontrar o animal com o id informado: " + id));
    }

    @Override
    public Page<AnimalResponse> getAllAnimals(Integer page,
                                              Integer size) {
        Pageable pageable = Pageable.ofSize(size)
                .withPage(page);
        return animalRepository.findAll(pageable)
                .map(this::toResponse);
    }

    @Override
    public AnimalResponse updateAnimal(Long id,
                                       AnimalRequest animalRequest) {
        Tutor tutor = tutorService.getTutorByIdNumber(animalRequest.getTutorIdNumber());
        if (tutor == null) {
            throw new IllegalArgumentException("Tutor com número de identificação " + animalRequest.getTutorIdNumber() + " não encontrado na base.");
        }

        Animal animal = toEntity(animalRequest, tutor);
        animalRepository.save(animal);
        return toResponse(animal);
    }

    @Override
    public void deleteAnimal(Long id) {
        boolean exists = animalRepository.existsById(id);
        if (!exists) {
            throw new IllegalArgumentException("Não foi possível encontrar o animal com o id informado: " + id);
        }
        animalRepository.deleteById(id);
    }


    private Animal toEntity(AnimalRequest animalRequest,
                            Tutor tutor) throws IllegalArgumentException {
        return Animal.builder()
                .name(animalRequest.getName())
                .species(Animal.Species.valueOf(String.valueOf(animalRequest.getSpecies())))
                .breed(animalRequest.getBreed())
                .color(animalRequest.getColor())
                .birthDate(String.valueOf(animalRequest.getBirthDate()))
                .tutor(tutor)
                .build();
    }


    private AnimalResponse toResponse(Animal animal) {
        return new AnimalResponse(animal.getId(), animal.getName(), animal.getSpecies()
                .name(), animal.getBreed(), animal.getColor(), animal.getBirthDate(), animal.getTutor()
                                          .getIdNumber(), animal.getTutor()
                                          .getName());
    }
}
