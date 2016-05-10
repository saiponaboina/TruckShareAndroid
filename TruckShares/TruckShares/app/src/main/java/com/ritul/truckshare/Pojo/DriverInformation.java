package com.ritul.truckshare.Pojo;

import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;

/**
 * Created by admin on 16-Feb-16.
 */
public class DriverInformation implements InstanceCreator<DriverInformation> {
    String appUserId,driverDetailId,licenseNumber,licenseIssuedStateId,licenseExpiryDate,licenseImageBase64String,licenseImageFormat;

    public String getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(String appUserId) {
        this.appUserId = appUserId;
    }

    public String getDriverDetailId() {
        return driverDetailId;
    }

    public void setDriverDetailId(String driverDetailId) {
        this.driverDetailId = driverDetailId;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseIssuedStateId() {
        return licenseIssuedStateId;
    }

    public void setLicenseIssuedStateId(String licenseIssuedStateId) {
        this.licenseIssuedStateId = licenseIssuedStateId;
    }

    public String getLicenseExpiryDate() {
        return licenseExpiryDate;
    }

    public void setLicenseExpiryDate(String licenseExpiryDate) {
        this.licenseExpiryDate = licenseExpiryDate;
    }

    public String getLicenseImageBase64String() {
        return licenseImageBase64String;
    }

    public void setLicenseImageBase64String(String licenseImageBase64String) {
        this.licenseImageBase64String = licenseImageBase64String;
    }

    public String getLicenseImageFormat() {
        return licenseImageFormat;
    }

    public void setLicenseImageFormat(String licenseImageFormat) {
        this.licenseImageFormat = licenseImageFormat;
    }

    @Override
    public DriverInformation createInstance(Type type) {
        return new DriverInformation();
    }
}
