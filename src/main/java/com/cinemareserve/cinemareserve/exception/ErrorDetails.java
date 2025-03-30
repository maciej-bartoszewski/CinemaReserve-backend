package com.cinemareserve.cinemareserve.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorDetails {
    private int statusCode;
    private String message;
    private String details;
}