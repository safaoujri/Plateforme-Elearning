package com.pfa.userservice.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@Data
public class UserNotFoundException extends RuntimeException {
    private final String message;
}
