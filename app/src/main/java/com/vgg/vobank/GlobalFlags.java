package com.vgg.vobank;

import android.app.Application;

public class GlobalFlags extends Application {

    private String globalFingerprintCheck;

    public void setGlobalFingerprintCheck(String globalFingerprintCheck) {
        this.globalFingerprintCheck = globalFingerprintCheck;
    }

    public String getGlobalFingerprintCheck() {
        return globalFingerprintCheck;
    }
}