package com.example.droneapp.services;

import com.example.droneapp.dtos.ApiResponse;
import com.example.droneapp.dtos.DroneDto;
import com.example.droneapp.dtos.DroneResponseDto;
import com.example.droneapp.entities.MedicationDto;

import java.util.List;

public interface DroneService {
    ApiResponse<DroneResponseDto> registerDrone(DroneDto droneDto);
    ApiResponse<List<MedicationDto>> loadDroneWithMedicationItems(List<MedicationDto> medicationDtos);
    ApiResponse<List<MedicationDto>> checkLoadedMedicationItemsInADrone(String droneSerialNo);
    ApiResponse<List<DroneResponseDto>> checkAvailableDronesForLoading();
    ApiResponse<Integer> checkDroneBatteryLevelForADrone(String droneSerialNo);
}
