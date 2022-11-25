package com.module.blog.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiError  {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private HttpStatus status;
    private String message;
    private List<ApiSubError> subErrors;

    public static ResponseEntity<Object> buildApiError(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
