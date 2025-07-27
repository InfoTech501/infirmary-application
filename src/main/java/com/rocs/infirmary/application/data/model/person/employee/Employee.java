package com.rocs.infirmary.application.data.model.person.employee;

import com.rocs.infirmary.application.data.model.person.Person;

/**
 * Represents a staff member within the infirmary.
 */
public class Employee extends Person {

    private Long nurseInChargeId;
    private String nurseInCharge;
    private String role = "Nurse";

    public Long getNurseInChargeId() { return nurseInChargeId; }
    public void setNurseInChargeId(Long nurseInChargeId) { this.nurseInChargeId = nurseInChargeId; }

    public String getNurseInCharge() { return nurseInCharge; }
    public void setNurseInCharge(String nurseInCharge) { this.nurseInCharge = nurseInCharge; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    @Override
    public String toString() {
        return nurseInCharge;
    }
}
