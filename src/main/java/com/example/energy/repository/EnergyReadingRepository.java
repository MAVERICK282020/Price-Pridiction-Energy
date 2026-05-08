package com.example.energy.repository;

import com.example.energy.model.EnergyReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;

public interface EnergyReadingRepository extends JpaRepository<EnergyReading, Long> {
    List<EnergyReading> findTop30ByDeviceIdOrderByReadingTimeDesc(String deviceId);
    List<EnergyReading> findByDeviceIdOrderByReadingTimeAsc(String deviceId);
    List<EnergyReading> findByReadingTimeBetweenOrderByReadingTimeAsc(LocalDateTime start, LocalDateTime end);

    @Query("select coalesce(sum(e.energyKwh), 0) from EnergyReading e where e.readingTime between :start and :end")
    double sumEnergyBetween(LocalDateTime start, LocalDateTime end);
}
