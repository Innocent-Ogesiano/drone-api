package com.example.droneapp.controller;

import com.example.droneapp.dtos.ApiResponse;
import com.example.droneapp.dtos.DroneDto;
import com.example.droneapp.dtos.DroneResponseDto;
import com.example.droneapp.dtos.MedicationRequest;
import com.example.droneapp.entities.MedicationDto;
import com.example.droneapp.services.DroneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/drones")
@RequiredArgsConstructor
public class DroneController {
    private final DroneService droneService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<DroneResponseDto>> registerDrone(@Valid @RequestBody DroneDto droneDto) {
        return ResponseEntity.ok(droneService.registerDrone(droneDto));
    }

    @PostMapping("/load")
    public ResponseEntity<ApiResponse<List<MedicationDto>>> loadDroneWithMedicationItems(@Valid @RequestBody MedicationRequest medicationRequest) {
        return ResponseEntity.ok(droneService.loadDroneWithMedicationItems(medicationRequest.medicationDtos()));
    }

    @GetMapping("/list/medications")
    public ResponseEntity<ApiResponse<List<MedicationDto>>> getLoadedMedications(@RequestParam String serialNo) {
        return ResponseEntity.ok(droneService.checkLoadedMedicationItemsInADrone(serialNo));
    }

    @GetMapping("/list/available-drones")
    public ResponseEntity<ApiResponse<List<DroneResponseDto>>> getDrones() {
        return ResponseEntity.ok(droneService.checkAvailableDronesForLoading());
    }

    @GetMapping("/battery-level")
    public ResponseEntity<ApiResponse<Integer>> getBatteryLevel(@RequestParam String serialNo) {
        return ResponseEntity.ok(droneService.checkDroneBatteryLevelForADrone(serialNo));
    }
}
