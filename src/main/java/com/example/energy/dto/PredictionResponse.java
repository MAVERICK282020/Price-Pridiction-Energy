package com.example.energy.dto;

public class PredictionResponse {
    private double predictedNextKwh;
    private String method;
    private String recommendation;

    public PredictionResponse(double predictedNextKwh, String method, String recommendation) {
        this.predictedNextKwh = predictedNextKwh;
        this.method = method;
        this.recommendation = recommendation;
    }

    public double getPredictedNextKwh() { return predictedNextKwh; }
    public void setPredictedNextKwh(double predictedNextKwh) { this.predictedNextKwh = predictedNextKwh; }
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
    public String getRecommendation() { return recommendation; }
    public void setRecommendation(String recommendation) { this.recommendation = recommendation; }
}
