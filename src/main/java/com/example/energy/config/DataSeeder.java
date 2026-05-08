package com.example.energy.config;

import com.example.energy.model.AppUser;
import com.example.energy.model.EnergyReading;
import com.example.energy.repository.UserRepository;
import com.example.energy.service.EnergyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class DataSeeder {
    @Bean
    CommandLineRunner seedData(EnergyService energyService, UserRepository userRepository) {
        return args -> {
            if (!userRepository.existsByEmail("admin@example.com")) {
                AppUser admin = new AppUser();
                admin.setFullName("Admin User");
                admin.setEmail("admin@example.com");
                admin.setPassword("admin123");
                admin.setRole("ADMIN");
                userRepository.save(admin);
            }

            for (int i = 10; i >= 1; i--) {
                double kwh = 2.1 + (10 - i) * 0.18;
                double watt = 420 + (10 - i) * 22;
                energyService.save(new EnergyReading("HOME-001", 230, watt / 230, watt, kwh, LocalDateTime.now().minusHours(i)));
            }
            energyService.save(new EnergyReading("HOME-001", 230, 4.8, 1104, 5.3, LocalDateTime.now()));
        };
    }
}
