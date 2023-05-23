package com.example.droneapp.services_impl;

import com.example.droneapp.dtos.ApiResponse;
import com.example.droneapp.dtos.DroneDto;
import com.example.droneapp.dtos.DroneResponseDto;
import com.example.droneapp.entities.Drone;
import com.example.droneapp.entities.Medication;
import com.example.droneapp.entities.MedicationDto;
import com.example.droneapp.enums.Model;
import com.example.droneapp.enums.State;
import com.example.droneapp.repositories.DroneRepository;
import com.example.droneapp.repositories.MedicationRepository;
import com.example.droneapp.services.DroneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.droneapp.utils.AppUtils.getSerialNo;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class DroneServiceImpl implements DroneService {
    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;

    @Override
    public ApiResponse<DroneResponseDto> registerDrone(DroneDto droneDto) {
        String serialNo = getSerialNo();
        while (droneRepository.existsBySerialNo(serialNo)) {
            serialNo = getSerialNo();
        }
        Drone drone = new Drone();
        drone.setModel(droneDto.model());
        drone.setSerialNo(serialNo);
        drone.setWeightLimit(droneDto.model().getMaxWeight());
        drone.setBatteryCapacity(droneDto.batteryCapacity());
        drone.setState(State.IDLE);
        droneRepository.save(drone);
        System.out.println(drone);
        return new ApiResponse<>(OK.value(), "Drone created successfully",
                new DroneResponseDto(drone.getSerialNo(), drone.getModel(), drone.getWeightLimit(), drone.getBatteryCapacity(), drone.getState()));
    }

    @Override
    public ApiResponse<List<MedicationDto>> loadDroneWithMedicationItems(List<MedicationDto> medicationDtos) {
        if (medicationDtos != null && !medicationDtos.isEmpty()) {
            double sum = medicationDtos.stream().mapToDouble(MedicationDto::weight).sum();
            Model model = assignDroneModel(sum);
            if (model == null) {
                return new ApiResponse<>(OK.value(), "Drone cannot carry the weight of the medication items", null);
            }
            Drone drone = droneRepository
                    .findFirstByBatteryCapacityGreaterThanAndModelAndState(24, model, State.IDLE)
                    .orElse(null);
            if (drone == null) {
                return new ApiResponse<>(OK.value(), "No available drone for loading", null);
            }
            drone.setState(State.LOADING);
            droneRepository.save(drone);
            List<Medication> medications = new ArrayList<>();
            medicationDtos.forEach(medicationDto -> {
                Medication medication = new Medication(medicationDto.name(), medicationDto.weight(), medicationDto.code(), "");
                medicationRepository.save(medication);
                medications.add(medication);
            });
            drone.setLoadedMedication(medications);
            drone.setState(State.LOADED);
            droneRepository.save(drone);
            return new ApiResponse<>(OK.value(), "Drone loaded successfully", medicationDtos);
        } else {
            return new ApiResponse<>(NO_CONTENT.value(), "Medication items cannot be empty", null);
        }
    }

    private Model assignDroneModel(double sum) {
        Model model;
        if (sum <= 20) {
            model = Model.LIGHT_WEIGHT;
        } else if (sum <= 30) {
            model = Model.MIDDLE_WEIGHT;
        } else if (sum <= 40) {
            model = Model.CRUISER_WEIGHT;
        } else if (sum <= 50) {
            model = Model.HEAVY_WEIGHT;
        } else {
            model = null;
        }
        return model;
    }

    @Override
    public ApiResponse<List<MedicationDto>> checkLoadedMedicationItemsInADrone(String droneSerialNo) {
        Drone drone = droneRepository.findBySerialNo(droneSerialNo).orElse(null);
        if (drone != null) {
            List<Medication> medications = drone.getLoadedMedication();
            if (medications != null && !medications.isEmpty()) {
                List<MedicationDto> medicationDtos = new ArrayList<>();
                medications.forEach(medication -> medicationDtos.add(new MedicationDto(medication.getName(), medication.getWeight(), medication.getCode())));
                return new ApiResponse<>(OK.value(), "Loaded medication items in a drone", medicationDtos);
            } else {
                return new ApiResponse<>(OK.value(), "No loaded medication items in a drone", null);
            }
        } else {
            return new ApiResponse<>(NOT_FOUND.value(), "Drone not found", null);
        }
    }

    @Override
    public ApiResponse<List<DroneResponseDto>> checkAvailableDronesForLoading() {
        List<Drone> drones = droneRepository.findAllByState(State.IDLE);
        if (drones != null && !drones.isEmpty()) {
            List<DroneResponseDto> droneResponseDtos = new ArrayList<>();
            drones.forEach(drone -> droneResponseDtos.add(new DroneResponseDto(drone.getSerialNo(), drone.getModel(), drone.getWeightLimit(), drone.getBatteryCapacity(), drone.getState())));
            return new ApiResponse<>(OK.value(), "Available drones for loading", droneResponseDtos);
        } else {
            return new ApiResponse<>(OK.value(), "No available drones for loading", null);
        }
    }

    @Override
    public ApiResponse<Integer> checkDroneBatteryLevelForADrone(String droneSerialNo) {
        Drone drone = droneRepository.findBySerialNo(droneSerialNo).orElse(null);
        if (drone != null) {
            return new ApiResponse<>(OK.value(), "Drone battery level", drone.getBatteryCapacity());
        } else {
            return new ApiResponse<>(NOT_FOUND.value(), "Drone not found", null);
        }
    }
}
