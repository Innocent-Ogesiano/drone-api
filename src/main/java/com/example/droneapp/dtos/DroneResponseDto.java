package com.example.droneapp.dtos;

import com.example.droneapp.entities.Drone;
import com.example.droneapp.enums.Model;
import com.example.droneapp.enums.State;

import java.io.Serializable;

/**
 * A DTO for the {@link Drone} entity
 */
public record DroneResponseDto(String serialNo, Model model, Double weightLimit, int batteryCapacity,
                               State state) implements Serializable {
}