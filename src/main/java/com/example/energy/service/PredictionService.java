package com.example.energy.service;

import com.example.energy.dto.PredictionResponse;
import com.example.energy.model.EnergyReading;
import com.example.energy.repository.EnergyReadingRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class PredictionService {
    private final EnergyReadingRepository repository;
    private final int windowSize;

    public PredictionService(EnergyReadingRepository repository,
                             @Value("${energy.prediction.window:7}") int windowSize) {
        this.repository = repository;
        this.windowSize = windowSize;
    }

    public PredictionResponse predictNextReading(String deviceId) {
        List<EnergyReading> readings = repository.findTop30ByDeviceIdOrderByReadingTimeDesc(deviceId);
        if (readings.isEmpty()) {
            return new PredictionResponse(0, "moving-average", "Add readings first to generate predictions.");
        }

        double predicted = readings.stream()
                .sorted(Comparator.comparing(EnergyReading::getReadingTime).reversed())
                .limit(windowSize)
                .mapToDouble(EnergyReading::getEnergyKwh)
                .average()
                .orElse(0);

        String recommendation = predicted > 4
                ? "Predicted usage is high. Switch off idle appliances and check heavy loads."
                : "Predicted usage is normal. Keep monitoring daily usage.";

        return new PredictionResponse(round(predicted), "Java moving-average model", recommendation);
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
