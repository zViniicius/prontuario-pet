package com.vinicius.backend.controller;

import com.vinicius.backend.dto.request.AnimalRequest;
import com.vinicius.backend.dto.response.AnimalResponse;
import com.vinicius.backend.service.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/animals")
@Tag(name = "Animals Controller", description = "Endpoints para gerenciamento do cadastro de animais")
public class AnimalsController {

    private final AnimalService animalService;

    public AnimalsController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping
    @Operation(summary = "Obter todos os animais cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Animais encontrados", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AnimalResponse.class))
            })
    })
    public ResponseEntity<Page<AnimalResponse>> getAllAnimals(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<AnimalResponse> tutors = animalService.getAllAnimals(page, size);
        return ResponseEntity.ok(tutors);
    }

    @PostMapping
    @Operation(summary = "Cadastrar um novo animal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Animal cadastrado com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AnimalResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Erro na requisição", content = @Content),
            @ApiResponse(responseCode = "404", description = "Tutor informado não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content),
    })
    public ResponseEntity<AnimalResponse> createAnimal(
            @RequestBody AnimalRequest animalRequest) {
        AnimalResponse animal = animalService.createAnimal(animalRequest);
        return ResponseEntity.ok(animal);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter cadastro de um animal por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Animal encontrado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AnimalResponse.class))
            }), @ApiResponse(responseCode = "404", description = "Animal não encontrado", content = @Content),
    })
    public ResponseEntity<AnimalResponse> getAnimalById(
            @PathVariable Long id) {
        return ResponseEntity.ok(animalService.getAnimalById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cadastro de um animal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Animal atualizado com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AnimalResponse.class))
            }), @ApiResponse(responseCode = "404", description = "Animal não encontrado"),
    })
    public ResponseEntity<AnimalResponse> updateAnimal(
            @PathVariable Long id,
            @RequestBody AnimalRequest animalRequest) {
        AnimalResponse animal = animalService.updateAnimal(id, animalRequest);
        return ResponseEntity.ok(animal);

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar cadastro de um animal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Animal deletado com sucesso"), @ApiResponse(responseCode = "404", description = "Animal não encontrado"),
    })
    public void deleteAnimal(
            @PathVariable Long id) {
        animalService.deleteAnimal(id);
    }
}
