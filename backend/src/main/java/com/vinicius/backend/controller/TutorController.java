package com.vinicius.backend.controller;

import com.vinicius.backend.domain.Tutor;
import com.vinicius.backend.dto.request.TutorRequest;
import com.vinicius.backend.service.TutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tutors")
@Tag(name = "Tutors Controller", description = "Endpoints para gerenciamento do cadastro de tutores")
public class TutorController {

    private final TutorService tutorService;

    public TutorController(TutorService tutorService) {
        this.tutorService = tutorService;
    }

    @PostMapping
    @Operation(summary = "Criar novo cadastrado de tutor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tutor cadastrado com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Tutor.class))
            }), @ApiResponse(responseCode = "400", description = "Erro na requisição", content = @Content), @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content),
    })
    public ResponseEntity<Tutor> createTutor(
            @RequestBody
            @Valid
            TutorRequest tutorRequest) {
        Tutor tutor = tutorService.createTutor(tutorRequest);
        return ResponseEntity.ok(tutor);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter tutor por id do cadastro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tutor encontrado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Tutor.class))
            }), @ApiResponse(responseCode = "404", description = "Tutor não encontrado", content = @Content),
    })
    public ResponseEntity<Tutor> getTutorById(
            @PathVariable
            @Parameter(description = "Tutor id")
            Long id) {
        Tutor tutor = tutorService.getTutorById(id);
        return ResponseEntity.ok(tutor);
    }

    @GetMapping
    @Operation(summary = "Obter todos os tutores cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tutores encontrados", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Tutor.class))
            })
    })
    public ResponseEntity<Page<Tutor>> getAllTutors(
            @RequestParam(defaultValue = "0")
            @Parameter(description = "Page number")
            Integer page,
            @RequestParam(defaultValue = "10")
            @Parameter(description = "Page size")
            Integer size) {
        Page<Tutor> tutors = tutorService.getAllTutors(page, size);
        return ResponseEntity.ok(tutors);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cadastro de tutor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tutor atualizado com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Tutor.class))
            }), @ApiResponse(responseCode = "404", description = "Tutor não encontrado"),
    })
    public ResponseEntity<Tutor> updateTutor(
            @PathVariable
            @Parameter(description = "Tutor id")
            Long id,
            @RequestBody
            @Valid
            TutorRequest tutorRequest) {
        Tutor updatedTutor = tutorService.updateTutor(id, tutorRequest);
        return ResponseEntity.ok(updatedTutor);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar cadastro de tutor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tutor deletado com sucesso", content = @Content), @ApiResponse(responseCode = "404", description = "Tutor não encontrado"),
    })
    public ResponseEntity<String> deleteTutor(
            @PathVariable
            @Parameter(description = "Tutor id")
            Long id) {
        tutorService.deleteTutor(id);
        return ResponseEntity.ok()
                .body("Tutor com id " + id + " deletado com sucesso!");
    }
}
