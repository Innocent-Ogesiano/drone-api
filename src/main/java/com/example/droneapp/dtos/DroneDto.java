package com.example.droneapp.dtos;

import com.example.droneapp.enums.Model;
import com.example.droneapp.enums.State;

import java.io.Serializable;

/**
 * A DTO for the {@link com.example.droneapp.entities.Drone} entity
 */
public record DroneDto(Model model, int batteryCapacity) implements Serializable {
}