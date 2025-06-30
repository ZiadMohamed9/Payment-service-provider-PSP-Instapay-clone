package com.psp.nbebank.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Data Transfer Object (DTO) for API responses.
 * Encapsulates the standard structure of a response returned by the API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse {

    /**
     * The HTTP status of the response.
     */
    private HttpStatus status;

    /**
     * A message providing additional information about the response.
     */
    private String message;

    /**
     * The data payload of the response, if any.
     */
    private Object data;

    /**
     * Any errors associated with the response, if applicable.
     */
    private Object errors;
}