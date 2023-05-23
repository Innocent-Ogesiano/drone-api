package com.example.droneapp.entities;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Medication extends BaseClass{
    @Pattern(regexp = "^[a-zA-Z0-9_-]*$", message = "allowed only letters, numbers, '-', '_'")
    private String name;
    private Double weight;
    @Pattern(regexp = "^[A-Z0-9_]*$", message = "allowed only upper case letters, underscore and numbers")
    private String code;
    private String imageUrl;
}
