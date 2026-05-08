package com.example.energy.controller;

import com.example.energy.dto.PredictionResponse;
import com.example.energy.model.EnergyReading;
import com.example.energy.service.EnergyService;
import com.example.energy.service.PredictionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/energy")
@CrossOrigin(origins = "*")
public class EnergyController {
    private final EnergyService energyService;
    private final PredictionService predictionService;

    public EnergyController(EnergyService energyService, PredictionService predictionService) {
        this.energyService = energyService;
        this.predictionService = predictionService;
    }

    @PostMapping("/reading")
    public EnergyReading addReading(@Valid @RequestBody EnergyReading reading) {
        return energyService.save(reading);
    }

    @GetMapping("/readings")
    public List<EnergyReading> latest(@RequestParam(defaultValue = "HOME-001") String deviceId) {
        return energyService.latest(deviceId);
    }

    @GetMapping("/all")
    public List<EnergyReading> all() {
        return energyService.all();
    }

    @GetMapping("/today")
    public Map<String, Object> today() {
        return Map.of("todayKwh", energyService.todayKwh(), "estimatedBill", energyService.estimatedBill(8.0));
    }

    @GetMapping("/predict")
    public PredictionResponse predict(@RequestParam(defaultValue = "HOME-001") String deviceId) {
        return predictionService.predictNextReading(deviceId);
    }
}
