package com.ritul.truckshare.Pojo;

import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;

/**
 * Created by admin on 16-Feb-16.
 */
public class Insurence implements InstanceCreator<Insurence> {

    String truckInsuranceId,truckDetailId,insuranceProvider,policyNumber,dateOfExpiry,insuranceDocImageBase64String,insuranceDocImageFormat;

    public String getTruckInsuranceId() {
        return truckInsuranceId;
    }

    public void setTruckInsuranceId(String truckInsuranceId) {
        this.truckInsuranceId = truckInsuranceId;
    }

    public String getTruckDetailId() {
        return truckDetailId;
    }

    public void setTruckDetailId(String truckDetailId) {
        this.truckDetailId = truckDetailId;
    }

    public String getInsuranceProvider() {
        return insuranceProvider;
    }

    public void setInsuranceProvider(String insuranceProvider) {
        this.insuranceProvider = insuranceProvider;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getDateOfExpiry() {
        return dateOfExpiry;
    }

    public void setDateOfExpiry(String dateOfExpiry) {
        this.dateOfExpiry = dateOfExpiry;
    }

    public String getInsuranceDocImageBase64String() {
        return insuranceDocImageBase64String;
    }

    public void setInsuranceDocImageBase64String(String insuranceDocImageBase64String) {
        this.insuranceDocImageBase64String = insuranceDocImageBase64String;
    }

    public String getInsuranceDocImageFormat() {
        return insuranceDocImageFormat;
    }

    public void setInsuranceDocImageFormat(String insuranceDocImageFormat) {
        this.insuranceDocImageFormat = insuranceDocImageFormat;
    }

    @Override
    public Insurence createInstance(Type type) {
        return new Insurence();
    }
}
