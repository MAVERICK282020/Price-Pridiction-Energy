package com.example.energy.service;

import com.example.energy.model.EnergyReading;
import com.example.energy.repository.EnergyReadingRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class EnergyService {
    private final EnergyReadingRepository repository;
    private final double anomalyMultiplier;

    public EnergyService(EnergyReadingRepository repository,
                         @Value("${energy.anomaly.multiplier:1.4}") double anomalyMultiplier) {
        this.repository = repository;
        this.anomalyMultiplier = anomalyMultiplier;
    }

    public EnergyReading save(EnergyReading reading) {
        if (reading.getPowerWatt() <= 0 && reading.getVoltage() > 0 && reading.getCurrentAmp() > 0) {
            reading.setPowerWatt(reading.getVoltage() * reading.getCurrentAmp());
        }
        detectAnomaly(reading);
        return repository.save(reading);
    }

    private void detectAnomaly(EnergyReading reading) {
        List<EnergyReading> recent = repository.findTop30ByDeviceIdOrderByReadingTimeDesc(reading.getDeviceId());
        if (recent.size() < 3) {
            reading.setAnomaly(false);
            reading.setNote("Need more data for reliable anomaly detection.");
            return;
        }
        double avgPower = recent.stream().mapToDouble(EnergyReading::getPowerWatt).average().orElse(0);
        boolean abnormal = avgPower > 0 && reading.getPowerWatt() > avgPower * anomalyMultiplier;
        reading.setAnomaly(abnormal);
        reading.setNote(abnormal
                ? "High power usage detected compared with recent average."
                : "Normal usage pattern.");
    }

    public List<EnergyReading> latest(String deviceId) {
        List<EnergyReading> readings = repository.findTop30ByDeviceIdOrderByReadingTimeDesc(deviceId);
        Collections.reverse(readings);
        return readings;
    }

    public List<EnergyReading> all() {
        return repository.findAll();
    }

    public double todayKwh() {
        LocalDate today = LocalDate.now();
        return repository.sumEnergyBetween(today.atStartOfDay(), today.plusDays(1).atStartOfDay());
    }

    public double estimatedBill(double ratePerKwh) {
        return todayKwh() * ratePerKwh;
    }
}
