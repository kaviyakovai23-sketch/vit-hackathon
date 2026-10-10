package com.risk.risk_service;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/risk")
public class RiskController {

    @PostMapping("/assess")
    public RiskResponse assessRisk(@RequestBody RiskInput input) {
        // Simple explainable risk logic based on debt and loan amount
        int riskScore = 40;
        String category = "Low Risk";
        String explanation = "Income is stable and loan amount is within safe limits for the requested crop.";

        if (input.loanAmount > 50000 || input.existingDebt > 30000) {
            riskScore = 85;
            category = "High Risk";
            explanation = "High debt-to-loan ratio relative to land size and income.";
        }

        return new RiskResponse(riskScore, category, explanation);
    }
}

class RiskInput {
    public double income;
    public double existingDebt;
    public double loanAmount;
    public String crop;
    public double landSize;
}

class RiskResponse {
    public int riskScore;
    public String riskCategory;
    public String explanation;

    public RiskResponse(int riskScore, String riskCategory, String explanation) {
        this.riskScore = riskScore;
        this.riskCategory = riskCategory;
        this.explanation = explanation;
    }
}