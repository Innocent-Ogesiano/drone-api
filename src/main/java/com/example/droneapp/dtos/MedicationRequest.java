package com.example.droneapp.dtos;

import com.example.droneapp.entities.MedicationDto;

import java.util.List;

public record MedicationRequest (List<MedicationDto> medicationDtos) {
}
