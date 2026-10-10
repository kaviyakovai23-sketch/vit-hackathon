package com.example.agriloan.assessment;

import java.util.List;

public class RiskAssessmentResult {

    private double riskPercentage;
    private String riskCategory;
    private String assessmentSummary;
    private String suggestedAction;
    private List<String> riskFactors;

    public RiskAssessmentResult(
            double riskPercentage,
            String riskCategory,
            String assessmentSummary,
            String suggestedAction,
            List<String> riskFactors) {

        this.riskPercentage = riskPercentage;
        this.riskCategory = riskCategory;
        this.assessmentSummary = assessmentSummary;
        this.suggestedAction = suggestedAction;
        this.riskFactors = riskFactors;
    }

    public double getRiskPercentage() {
        return riskPercentage;
    }

    public String getRiskCategory() {
        return riskCategory;
    }

    public String getAssessmentSummary() {
        return assessmentSummary;
    }

    public String getSuggestedAction() {
        return suggestedAction;
    }

    public List<String> getRiskFactors() {
        return riskFactors;
    }
}