package com.example.droneapp.entities;

import jakarta.validation.constraints.Pattern;

import java.io.Serializable;

/**
 * A DTO for the {@link Medication} entity
 */
public record MedicationDto(
        @Pattern(regexp = "^[a-zA-Z0-9_-]*$", message = "allowed only letters, numbers, '-', '_'") String name,
        Double weight,
        @Pattern(regexp = "^[A-Z0-9_]*$", message = "allowed only upper case letters, underscore and numbers") String code) implements Serializable {
}