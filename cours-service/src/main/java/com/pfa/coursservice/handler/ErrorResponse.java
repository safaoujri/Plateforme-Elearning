package com.pfa.coursservice.handler;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors

) {
}
