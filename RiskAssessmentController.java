package com.example.agriloan.assessment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assessment")
@CrossOrigin(origins = {
        "http://localhost:5500",
        "http://127.0.0.1:5500"
})
public class RiskAssessmentController {

    private final RiskAssessmentService service;

    public RiskAssessmentController(
            RiskAssessmentService service) {
        this.service = service;
    }

    @PostMapping("/risk")
    public ResponseEntity<?> assessRisk(
            @RequestBody RiskAssessmentRequest request) {

        if (request.getAnnualIncome() < 0
                || request.getExistingDebt() < 0
                || request.getLoanAmount() < 0
                || request.getMissedPayments() < 0) {

            return ResponseEntity.badRequest()
                    .body("Values cannot be negative.");
        }

        RiskAssessmentResult result =
                service.assessRisk(request);

        return ResponseEntity.ok(result);
    }
}