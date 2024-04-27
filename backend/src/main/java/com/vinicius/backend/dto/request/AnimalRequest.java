package com.vinicius.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@Schema(name = "AnimalRequest", description = "Corpo da requisição para criação de um animal")
public class AnimalRequest {


    @Schema(description = "Nome do animal", example = "Rex")
    @NotBlank(message = "Campo name é obrigatório")
    private String name;

    @Schema(description = "Espécie do animal", example = "CACHORRO")
    @NotBlank(message = "Campo species é obrigatório")
    private String species;

    @Schema(description = "Raça do animal", example = "Vira-lata")
    @NotBlank(message = "Campo breed é obrigatório")
    private String breed;

    @Schema(description = "Cor do animal", example = "Marrom")
    @NotBlank(message = "Campo color é obrigatório")
    private String color;

    @Schema(description = "Data de nascimento do animal", example = "01/01/2021")
    @NotBlank(message = "Campo birthDate é obrigatório")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private String birthDate;

    @Schema(description = "Número do documento do tutor do animal", example = "123456789")
    @NotBlank(message = "Campo tutorIdNumber é obrigatório")
    private String tutorIdNumber;

}
