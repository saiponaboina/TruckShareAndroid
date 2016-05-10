package com.ritul.truckshare.Pojo;

import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;

/**
 * Created by Vipul Mangukiya on 14-Feb-16.
 */
public class Truckothertype implements InstanceCreator<Truckothertype> {

    String truckTypeId,truckTypeName,truckTypeDescription;

    public String getTruckTypeId() {
        return truckTypeId;
    }

    public void setTruckTypeId(String truckTypeId) {
        this.truckTypeId = truckTypeId;
    }

    public String getTruckTypeDescription() {
        return truckTypeDescription;
    }

    public void setTruckTypeDescription(String truckTypeDescription) {
        this.truckTypeDescription = truckTypeDescription;
    }

    public String getTruckTypeName() {
        return truckTypeName;
    }

    public void setTruckTypeName(String truckTypeName) {
        this.truckTypeName = truckTypeName;
    }

    @Override
    public Truckothertype createInstance(Type type) {

        return new Truckothertype();

    }
}
