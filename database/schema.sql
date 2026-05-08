CREATE DATABASE IF NOT EXISTS smart_energy_db;
USE smart_energy_db;

CREATE TABLE IF NOT EXISTS app_users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20),
    created_at DATETIME
);

CREATE TABLE IF NOT EXISTS energy_readings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    device_id VARCHAR(100) NOT NULL,
    voltage DOUBLE NOT NULL,
    current_amp DOUBLE NOT NULL,
    power_watt DOUBLE NOT NULL,
    energy_kwh DOUBLE NOT NULL,
    reading_time DATETIME,
    anomaly BOOLEAN,
    note VARCHAR(255)
);
