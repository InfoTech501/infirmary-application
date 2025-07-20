package com.rocs.infirmary.application.data.model.person.nurse;

import com.rocs.infirmary.application.data.model.person.Person;

public class Nurse extends Person {
    Long nurseInChargeId;
    String nurseInCharge;

    public Long getNurseInChargeId() { return nurseInChargeId; }

    public void setNurseInChargeId(Long nurseInChargeId) { this.nurseInChargeId = nurseInChargeId; }

    public String getNurseInCharge() { return nurseInCharge; }

    public void setNurseInCharge(String nurseInCharge) { this.nurseInCharge = nurseInCharge; }

    @Override
    public String toString() {
        return nurseInCharge;
    }
}
