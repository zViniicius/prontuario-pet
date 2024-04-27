package com.vinicius.backend.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatusCode;

import java.io.Serial;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)

public class BusinessException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private final HttpStatusCode httpStatusCode;
    private final String code;
    private final String message;
    private final String details;

    public BusinessExceptionBody getBody() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return BusinessExceptionBody.builder()
                .timestamp(sdf.format(new Date()))
                .code(this.code)
                .message(this.message)
                .details(this.details)
                .build();
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class BusinessExceptionBody {
        private String timestamp;
        private String code;
        private String message;
        private String details;
    }
}
