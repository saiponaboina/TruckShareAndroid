package com.ritul.truckshare.Pojo;

import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;

/**
 * Created by admin on 16-Feb-16.
 */
public class TruckDetail implements InstanceCreator<TruckDetail> {

    String truckDetailId,driverDetailId,truckRegistrationNumber,truckVinNumber,truckRegisteredStateId,truckTitleDocImageBase64String,truckTitleDocImageFormat,
            truckSize,truckTypeId;


    public String getTruckDetailId() {
        return truckDetailId;
    }

    public void setTruckDetailId(String truckDetailId) {
        this.truckDetailId = truckDetailId;
    }

    public String getDriverDetailId() {
        return driverDetailId;
    }

    public void setDriverDetailId(String driverDetailId) {
        this.driverDetailId = driverDetailId;
    }

    public String getTruckRegistrationNumber() {
        return truckRegistrationNumber;
    }

    public void setTruckRegistrationNumber(String truckRegistrationNumber) {
        this.truckRegistrationNumber = truckRegistrationNumber;
    }

    public String getTruckVinNumber() {
        return truckVinNumber;
    }

    public void setTruckVinNumber(String truckVinNumber) {
        this.truckVinNumber = truckVinNumber;
    }

    public String getTruckRegisteredStateId() {
        return truckRegisteredStateId;
    }

    public void setTruckRegisteredStateId(String truckRegisteredStateId) {
        this.truckRegisteredStateId = truckRegisteredStateId;
    }

    public String getTruckTitleDocImageBase64String() {
        return truckTitleDocImageBase64String;
    }

    public void setTruckTitleDocImageBase64String(String truckTitleDocImageBase64String) {
        this.truckTitleDocImageBase64String = truckTitleDocImageBase64String;
    }

    public String getTruckTitleDocImageFormat() {
        return truckTitleDocImageFormat;
    }

    public void setTruckTitleDocImageFormat(String truckTitleDocImageFormat) {
        this.truckTitleDocImageFormat = truckTitleDocImageFormat;
    }

    public String getTruckSize() {
        return truckSize;
    }

    public void setTruckSize(String truckSize) {
        this.truckSize = truckSize;
    }

    public String getTruckTypeId() {
        return truckTypeId;
    }

    public void setTruckTypeId(String truckTypeId) {
        this.truckTypeId = truckTypeId;
    }

    @Override
    public TruckDetail createInstance(Type type) {
        return new TruckDetail();
    }
}
