package com.example.agriloan.assessment;

public class RiskAssessmentRequest {

    private double annualIncome;
    private double existingDebt;
    private double loanAmount;
    private int missedPayments;
    private String crop;
    private String weatherRisk;

    public double getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(double annualIncome) {
        this.annualIncome = annualIncome;
    }

    public double getExistingDebt() {
        return existingDebt;
    }

    public void setExistingDebt(double existingDebt) {
        this.existingDebt = existingDebt;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public int getMissedPayments() {
        return missedPayments;
    }

    public void setMissedPayments(int missedPayments) {
        this.missedPayments = missedPayments;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getWeatherRisk() {
        return weatherRisk;
    }

    public void setWeatherRisk(String weatherRisk) {
        this.weatherRisk = weatherRisk;
    }
}