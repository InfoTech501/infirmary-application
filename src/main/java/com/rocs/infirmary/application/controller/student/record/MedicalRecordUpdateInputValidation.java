package com.rocs.infirmary.application.controller.student.record;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class MedicalRecordUpdateInputValidation {
    private static final Logger LOGGER = LoggerFactory.getLogger(MedicalRecordUpdateInputValidation.class);

    /**
     * A function that validates input on managing health records with these parameters:
     *
     * @param illness the parameter illness of MedicalRecords object
     * @param temperature the parameter temperature of MedicalRecords object
     * @param treatment the parameter treatment of MedicalRecords object
     * @param visitDate the parameter visit date of MedicalRecords object
     */
    public static String validateMedicalRecordInputs(String illness, String temperature, String treatment, LocalDate visitDate) {
        StringBuilder errorMessage = new StringBuilder();

        if (!isEmpty(illness)) {
            if (illness.length() > 250) {
                errorMessage.append("Illness must be less than 250 characters.\n");
            } else if (hasInvalidCharacters(illness)) {
                errorMessage.append("Illness contains invalid characters.\n");
            }
        }

        if (!isEmpty(temperature) && !isValidTemperature(temperature)) {
          try {
            Double.parseDouble(temperature);
          } catch (NumberFormatException e) {
            errorMessage.append("Temperature must be a valid number between 30.0 and 50.0°C (e.g., 37.5).\n");
          }
        }

        if (!isEmpty(treatment)) {
            if (treatment.length() > 500) {
                errorMessage.append("Treatment must be less than 500 characters.\n");
            } else if (hasInvalidCharacters(treatment)) {
                errorMessage.append("Treatment contains invalid characters.\n");
            }
        }

        if (visitDate != null && isVisitDateInFuture(Date.valueOf(visitDate))) {
            errorMessage.append("Visit date cannot be in the future.\n");
        }

        if (isEmpty(illness) && isEmpty(temperature) && isEmpty(treatment) && visitDate == null) {
            errorMessage.append("Please provide at least one field to update.\n");
        }

        return errorMessage.toString();
    }
    private static boolean hasInvalidCharacters(String text) {
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\s\\-'.,()]");
        return pattern.matcher(text).find();
    }

    private static boolean isValidTemperature(String temperature) {
        try {
            double temp = Double.parseDouble(temperature);
            return temp >= 30.0 && temp <= 50.0;
        } catch (NumberFormatException e) {
            LOGGER.error("Number format exception{}", String.valueOf(e));
            return false;
        }
    }

    private static boolean isVisitDateInFuture(Date visitDate) {
        return visitDate.after(new Date(System.currentTimeMillis()));
    }

    private static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
