package com.vinicius.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Schema(name = "TutorRequest", description = "Corpo da requisição para criação de um tutor")
public class TutorRequest {
    @Schema(description = "Nome do tutor", example = "Vinicius")
    @NotBlank(message = "Campo name é obrigatório")
    private String name;

    @Schema(description = "Número do documento do tutor", example = "123456789")
    @NotBlank(message = "Campo idNumber é obrigatório")
    private String idNumber;

    @Schema(description = "Tipo do documento do tutor", example = "CPF")
    @NotBlank(message = "Campo idType é obrigatório")
    private String idType;

    @Schema(description = "Telefone do tutor", example = "123456789")
    @NotBlank(message = "Campo phone é obrigatório")
    private String phone;

    @Schema(description = "Email do tutor", example = "vinicius@sus-aninimals.org")
    @NotBlank(message = "Campo email é obrigatório")
    @Email(message = "Campo email deve ser um email válido")
    private String email;
}
