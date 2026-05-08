package com.example.energy.config;

import com.example.energy.model.EnergyReading;
import com.example.energy.service.EnergyService;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MqttSubscriber implements CommandLineRunner {
    private final EnergyService energyService;
    private final boolean enabled;
    private final String broker;
    private final String topic;
    private final String clientId;

    public MqttSubscriber(EnergyService energyService,
                          @Value("${mqtt.enabled:false}") boolean enabled,
                          @Value("${mqtt.broker}") String broker,
                          @Value("${mqtt.topic}") String topic,
                          @Value("${mqtt.clientId}") String clientId) {
        this.energyService = energyService;
        this.enabled = enabled;
        this.broker = broker;
        this.topic = topic;
        this.clientId = clientId;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!enabled) {
            return;
        }

        MqttClient client = new MqttClient(broker, clientId + "-" + System.currentTimeMillis());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);

        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) { }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                // Expected payload: deviceId,voltage,currentAmp,powerWatt,energyKwh
                try {
                    String[] parts = new String(message.getPayload()).split(",");
                    EnergyReading reading = new EnergyReading();
                    reading.setDeviceId(parts[0].trim());
                    reading.setVoltage(Double.parseDouble(parts[1].trim()));
                    reading.setCurrentAmp(Double.parseDouble(parts[2].trim()));
                    reading.setPowerWatt(Double.parseDouble(parts[3].trim()));
                    reading.setEnergyKwh(Double.parseDouble(parts[4].trim()));
                    energyService.save(reading);
                } catch (Exception ignored) {
                    // Ignore invalid IoT payloads instead of stopping the app.
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) { }
        });

        client.connect(options);
        client.subscribe(topic);
    }
}
