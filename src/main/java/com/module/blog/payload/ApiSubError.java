package com.module.blog.payload;

import lombok.*;
import org.springframework.validation.FieldError;

import java.util.List;

abstract class ApiSubError {

}
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
class ApiValidationError extends ApiSubError {

    private String message;
    private String object;
    private String field;
    private Object rejectedValue;



}

