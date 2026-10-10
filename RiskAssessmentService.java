package com.example.agriloan.assessment;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class RiskAssessmentService {

    public RiskAssessmentResult assessRisk(
            RiskAssessmentRequest request) {

        double risk = 20;
        List<String> factors = new ArrayList<>();

        // Income analysis
        if (request.getAnnualIncome() <= 0) {
            risk += 30;
            factors.add("Income information is missing.");
        } else if (request.getAnnualIncome() < 100000) {
            risk += 20;
            factors.add("Annual income is below ₹1,00,000.");
        } else if (request.getAnnualIncome() < 250000) {
            risk += 10;
            factors.add("Annual income is moderate.");
        } else {
            factors.add("Annual income is above the basic threshold.");
        }

        // Loan amount analysis
        if (request.getLoanAmount() <= 0) {
            risk += 20;
            factors.add("Loan amount is missing or invalid.");
        } else if (request.getAnnualIncome() > 0) {
            double ratio = request.getLoanAmount()
                    / request.getAnnualIncome();

            if (ratio > 1.0) {
                risk += 25;
                factors.add("Loan amount exceeds annual income.");
            } else if (ratio > 0.5) {
                risk += 15;
                factors.add("Loan amount is relatively high.");
            } else {
                factors.add("Loan amount is within the basic threshold.");
            }
        }

        // Existing debt analysis
        if (request.getExistingDebt()
                > request.getAnnualIncome() * 0.5) {
            risk += 20;
            factors.add("Existing debt is high compared with income.");
        } else if (request.getExistingDebt() > 0) {
            risk += 8;
            factors.add("Existing financial obligations were reported.");
        } else {
            factors.add("No existing debt was reported.");
        }

        // Repayment history
        if (request.getMissedPayments() >= 3) {
            risk += 20;
            factors.add("Several missed payments were reported.");
        } else if (request.getMissedPayments() > 0) {
            risk += 10;
            factors.add("Previous missed payments were reported.");
        } else {
            factors.add("No missed payments were reported.");
        }

        // Agricultural weather risk
        String weather = request.getWeatherRisk() == null
                ? ""
                : request.getWeatherRisk().trim().toLowerCase();

        if (weather.equals("high")) {
            risk += 15;
            factors.add("High agricultural weather risk.");
        } else if (weather.equals("medium")) {
            risk += 8;
            factors.add("Medium agricultural weather risk.");
        } else if (weather.equals("low")) {
            factors.add("Low agricultural weather risk.");
        } else {
            risk += 5;
            factors.add("Weather risk information needs verification.");
        }

        // Keep score within 0–100
        risk = Math.min(100, Math.max(0, risk));
        risk = Math.round(risk * 100.0) / 100.0;

        String category;
        String summary;
        String action;

        if (risk >= 70) {
            category = "High";
            summary = "Several risk indicators require further review.";
            action = "Verify documents and arrange manual assessment.";
        } else if (risk >= 40) {
            category = "Medium";
            summary = "Some financial or agricultural factors need review.";
            action = "Review financial details and supporting documents.";
        } else {
            category = "Low";
            summary = "Fewer risk indicators were found in the supplied data.";
            action = "Continue with the normal verification process.";
        }

        return new RiskAssessmentResult(
                risk,
                category,
                summary,
                action,
                factors
        );
    }
}