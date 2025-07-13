package com.psp.instapay.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Data Transfer Object (DTO) for API response.
 * Represents the standard structure of a response returned by the API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDto {

    /**
     * The HTTP status of the response.
     * Indicates the result of the API operation (e.g., OK, BAD_REQUEST).
     */
    private HttpStatus status;

    /**
     * A message providing additional information about the response.
     * Typically used for success or error descriptions.
     */
    private String message;

    /**
     * The data payload of the response.
     * Contains the result of the API operation, if applicable.
     */
    private Object data;

    /**
     * The errors associated with the response, if any.
     * Typically used to provide details about validation or processing errors.
     */
    private Object errors;
}