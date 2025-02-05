package com.rocs.medical.records.application;

import com.rocs.medical.records.application.controller.CommonAilmentsController;

public class InfirmarySystemApplication {
    public static void main(String[] args) {
        CommonAilmentsController commonAilmentsController = new CommonAilmentsController();
        commonAilmentsController.viewCommonAilments();
    }
}
