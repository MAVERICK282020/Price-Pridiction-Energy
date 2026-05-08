# Smart Energy Monitoring and Prediction System

A complete Java Spring Boot project for real-time electricity usage tracking, IoT-style data input, anomaly detection, prediction, chatbot responses, reports, and a web dashboard.

## Features

- Java Spring Boot REST API
- H2 database for instant running
- MySQL profile for real database usage
- IoT reading API for voltage, current, power, and kWh
- Optional MQTT subscriber for live sensor payloads
- Java moving-average prediction module
- Rule-based anomaly detection module
- NLP-style chatbot module
- HTML, CSS, JavaScript dashboard with Chart.js
- User registration and login API

## Tech Stack

- Java 17
- Spring Boot 4.0.6
- Spring Web MVC
- Spring Data JPA
- Validation
- MySQL Connector/J
- H2 Database
- Eclipse Paho MQTT Client
- HTML, CSS, JavaScript, Chart.js

## Project Structure

```text
smart-energy-monitoring-system/
├── pom.xml
├── README.md
├── database/
│   └── schema.sql
└── src/main/
    ├── java/com/example/energy/
    │   ├── SmartEnergyApplication.java
    │   ├── config/
    │   ├── controller/
    │   ├── dto/
    │   ├── model/
    │   ├── repository/
    │   └── service/
    └── resources/
        ├── application.properties
        ├── application-mysql.properties
        └── static/
            ├── index.html
            ├── style.css
            └── script.js
```

## How to Run with H2 Database

```bash
mvn clean spring-boot:run
```

Open:

```text
http://localhost:8080
```

H2 console:

```text
http://localhost:8080/h2-console
```

Use this JDBC URL:

```text
jdbc:h2:mem:energydb
```

## How to Run with MySQL

1. Create database manually or use `database/schema.sql`.
2. Open `src/main/resources/application-mysql.properties`.
3. Change MySQL username and password.
4. Run:

```bash
mvn clean spring-boot:run -Dspring-boot.run.profiles=mysql
```

## API Endpoints

### Add Energy Reading

```bash
curl -X POST http://localhost:8080/api/energy/reading \
  -H "Content-Type: application/json" \
  -d '{
    "deviceId":"HOME-001",
    "voltage":230,
    "currentAmp":2.4,
    "powerWatt":552,
    "energyKwh":3.2
  }'
```

### Get Latest Readings

```bash
curl "http://localhost:8080/api/energy/readings?deviceId=HOME-001"
```

### Get Today Usage and Bill

```bash
curl http://localhost:8080/api/energy/today
```

### Get Prediction

```bash
curl "http://localhost:8080/api/energy/predict?deviceId=HOME-001"
```

### Ask Chatbot

```bash
curl -X POST http://localhost:8080/api/chatbot/ask \
  -H "Content-Type: application/json" \
  -d '{"message":"What is my usage today?"}'
```

### Register User

```bash
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "fullName":"Test User",
    "email":"test@example.com",
    "password":"test123",
    "role":"USER"
  }'
```

### Login User

```bash
curl -X POST http://localhost:8080/api/users/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@example.com","password":"admin123"}'
```

## MQTT Payload Format

MQTT is disabled by default. To enable it, set this in `application.properties`:

```properties
mqtt.enabled=true
```

Expected MQTT message payload:

```text
HOME-001,230,2.4,552,3.2
```

Format:

```text
deviceId,voltage,currentAmp,powerWatt,energyKwh
```

## Notes for College Submission

Use this title in your report or resume:

**Smart Energy Monitoring and Prediction System | Java, Spring Boot, MySQL, IoT, ML, NLP**

Description:

- Developed a Java-based smart energy monitoring system for real-time electricity usage tracking and analysis.
- Integrated IoT-style sensor data collection using REST APIs and optional MQTT.
- Implemented prediction and anomaly detection modules to identify abnormal usage patterns.
- Built a chatbot and dashboard for energy-saving insights, reports, and estimated billing.
