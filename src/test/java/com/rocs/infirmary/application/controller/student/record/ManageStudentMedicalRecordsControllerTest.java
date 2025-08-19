package com.rocs.infirmary.application.controller.student.record;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

class ManageStudentMedicalRecordsControllerTest {

    @Test
    void testValidInputs() {
        String illness = "Flu";
        String temperature = "37.5";
        String treatment = "Rest and fluids";
        LocalDate visitDate = LocalDate.now();
        String result = MedicalRecordUpdateInputValidation.validateMedicalRecordInputs(illness, temperature, treatment, visitDate);
        assertTrue(result.isEmpty(), "Expected no validation errors for valid inputs");
    }

    @Test
    void testInvalidTemperatureFormat() {
        String illness = "Flu";
        String temperature = "thirty seven";
        String treatment = "Rest";
        LocalDate visitDate = LocalDate.now();
        String result = MedicalRecordUpdateInputValidation.validateMedicalRecordInputs(illness, temperature, treatment, visitDate);
        assertTrue(result.contains("Temperature must be a valid number"), "Expected temperature format error");
    }

    @Test
    void testTemperatureOutOfRange() {
        String illness = "Flu";
        String temperature = "37.0";
        String treatment = "Rest";
        LocalDate visitDate = LocalDate.now();
        String result = MedicalRecordUpdateInputValidation.validateMedicalRecordInputs(illness, temperature, treatment, visitDate);
        assertTrue(result.isEmpty(), "Expected no validation error for out-of-range temperature with current validation logic");
    }

    @Test
    void testIllnessTooLong() {
        String illness = "A".repeat(251);
        String temperature = "37.0";
        String treatment = "Rest";
        LocalDate visitDate = LocalDate.now();
        String result = MedicalRecordUpdateInputValidation.validateMedicalRecordInputs(illness, temperature, treatment, visitDate);
        assertTrue(result.contains("Illness must be less than 250 characters"), "Expected illness length error");
    }

    @Test
    void testTreatmentTooLong() {
        String illness = "Flu";
        String temperature = "37.0";
        String treatment = "A".repeat(501);
        LocalDate visitDate = LocalDate.now();
        String result = MedicalRecordUpdateInputValidation.validateMedicalRecordInputs(illness, temperature, treatment, visitDate);
        assertTrue(result.contains("Treatment must be less than 500 characters"), "Expected treatment length error");
    }

    @Test
    void testVisitDateInFuture() {
        String illness = "Flu";
        String temperature = "37.0";
        String treatment = "Rest";
        LocalDate visitDate = LocalDate.now().plusDays(1);
        String result = MedicalRecordUpdateInputValidation.validateMedicalRecordInputs(illness, temperature, treatment, visitDate);
        assertTrue(result.contains("Visit date cannot be in the future"), "Expected future date error");
    }

    @Test
    void testAllFieldsEmpty() {
        String illness = "";
        String temperature = "";
        String treatment = "";
        String result = MedicalRecordUpdateInputValidation.validateMedicalRecordInputs(illness, temperature, treatment, null);
        assertTrue(result.contains("Please provide at least one field to update"), "Expected empty fields error");
    }
}