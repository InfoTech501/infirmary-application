package com.rocs.infirmary.application.data.model.person.employee;

import com.rocs.infirmary.application.data.model.person.Person;

/**
 * Represents a staff member within the infirmary.
 */
public class Employee extends Person {

    private Long nurseInChargeId;
    private String adviser;
    private String nurseInCharge;
    private String role;

    /**
     * This gets the nurse in charge id.
     * @return the Nurse In Charge id.
     */
    public Long getNurseInChargeId() {
        return nurseInChargeId;
    }
    /**
     * This sets the nurse in charge id.
     * @param nurseInChargeId is the Nurse In Charge id to be set.
     */
    public void setNurseInChargeId(Long nurseInChargeId) {
        this.nurseInChargeId = nurseInChargeId;
    }

    /**
     * This gets the adviser.
     * @return the Adviser.
     */
    public String getAdviser() {
        return adviser;
    }
    /**
     * This sets the adviser.
     * @param adviser is the Adviser to be set.
     */
    public void setAdviser(String adviser) {
        this.adviser = adviser;
    }

    /**
     * This gets the nurse in charge.
     * @return the Nurse In Charge.
     */
    public String getNurseInCharge() {
        return nurseInCharge;
    }
    /**
     * This sets the first name.
     * @param nurseInCharge is the Nurse In Charge to be set.
     */
    public void setNurseInCharge(String nurseInCharge) { this.nurseInCharge = nurseInCharge; }

    /**
     * This gets the role.
     * @return the Role.
     */
    public String getRole() {
        return role;
    }
    /**
     * This sets the first name.
     * @param role is the Role to be set.
     */
    public void setRole(String role) { this.role = role; }

    @Override
    public String toString() {
        return nurseInCharge;
    }
}
