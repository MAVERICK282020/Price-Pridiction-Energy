package com.example.energy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "energy_readings")
public class EnergyReading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String deviceId;

    @DecimalMin("0.0")
    private double voltage;

    @DecimalMin("0.0")
    private double currentAmp;

    @DecimalMin("0.0")
    private double powerWatt;

    @DecimalMin("0.0")
    private double energyKwh;

    private LocalDateTime readingTime;

    private boolean anomaly;

    private String note;

    public EnergyReading() {
    }

    public EnergyReading(String deviceId, double voltage, double currentAmp, double powerWatt, double energyKwh, LocalDateTime readingTime) {
        this.deviceId = deviceId;
        this.voltage = voltage;
        this.currentAmp = currentAmp;
        this.powerWatt = powerWatt;
        this.energyKwh = energyKwh;
        this.readingTime = readingTime;
    }

    @PrePersist
    public void prePersist() {
        if (readingTime == null) {
            readingTime = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }
    public double getVoltage() { return voltage; }
    public void setVoltage(double voltage) { this.voltage = voltage; }
    public double getCurrentAmp() { return currentAmp; }
    public void setCurrentAmp(double currentAmp) { this.currentAmp = currentAmp; }
    public double getPowerWatt() { return powerWatt; }
    public void setPowerWatt(double powerWatt) { this.powerWatt = powerWatt; }
    public double getEnergyKwh() { return energyKwh; }
    public void setEnergyKwh(double energyKwh) { this.energyKwh = energyKwh; }
    public LocalDateTime getReadingTime() { return readingTime; }
    public void setReadingTime(LocalDateTime readingTime) { this.readingTime = readingTime; }
    public boolean isAnomaly() { return anomaly; }
    public void setAnomaly(boolean anomaly) { this.anomaly = anomaly; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
