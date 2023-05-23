package com.example.droneapp.entities;

import com.example.droneapp.enums.Model;
import com.example.droneapp.enums.State;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class Drone extends BaseClass{
    private String serialNo;
    @Enumerated(EnumType.STRING)
    private Model model;
    private Double weightLimit;
    private int batteryCapacity;
    @Enumerated(EnumType.STRING)
    private State state;
    @OneToMany
    @ToString.Exclude
    List<Medication> loadedMedication;
}
