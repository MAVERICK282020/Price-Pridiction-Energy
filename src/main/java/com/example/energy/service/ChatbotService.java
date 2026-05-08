package com.example.energy.service;

import org.springframework.stereotype.Service;

@Service
public class ChatbotService {
    private final EnergyService energyService;
    private final PredictionService predictionService;

    public ChatbotService(EnergyService energyService, PredictionService predictionService) {
        this.energyService = energyService;
        this.predictionService = predictionService;
    }

    public String reply(String message) {
        String m = message == null ? "" : message.toLowerCase();

        if (m.contains("today") || m.contains("usage")) {
            return "Today's electricity usage is approximately " + round(energyService.todayKwh()) + " kWh.";
        }
        if (m.contains("bill") || m.contains("cost")) {
            double bill = energyService.estimatedBill(8.0);
            return "Estimated today's bill is ₹" + round(bill) + " using ₹8 per kWh. You can change this rate in the API.";
        }
        if (m.contains("predict") || m.contains("forecast")) {
            return predictionService.predictNextReading("HOME-001").getRecommendation();
        }
        if (m.contains("save") || m.contains("reduce") || m.contains("tip")) {
            return "Energy-saving tips: use LED bulbs, switch off idle devices, avoid peak-hour heavy loads, and maintain AC temperature around 24°C.";
        }
        if (m.contains("anomaly") || m.contains("alert")) {
            return "An anomaly means the current power use is much higher than your recent average. Check high-load appliances immediately.";
        }
        return "I can help with usage, bill estimate, prediction, anomaly alerts, and energy-saving tips.";
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
