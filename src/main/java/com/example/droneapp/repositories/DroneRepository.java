package com.example.droneapp.repositories;

import com.example.droneapp.entities.Drone;
import com.example.droneapp.enums.Model;
import com.example.droneapp.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DroneRepository extends JpaRepository<Drone, Long> {
    boolean existsBySerialNo(String serialNo);
    Optional<Drone> findFirstByBatteryCapacityGreaterThanAndModelAndState(
            int batteryCapacity, Model model, State state
    );
    Optional<Drone> findBySerialNo(String serialNo);
    List<Drone> findAllByState(State state);
}