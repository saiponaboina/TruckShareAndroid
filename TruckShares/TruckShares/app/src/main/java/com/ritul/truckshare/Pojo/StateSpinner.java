package com.ritul.truckshare.Pojo;

import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;

/**
 * Created by Vipul Mangukiya on 14-Feb-16.
 */
public class StateSpinner implements InstanceCreator<StateSpinner> {
    String stateId,stateName,stateCode;

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StateSpinner that = (StateSpinner) o;

        return !(stateId != null ? !stateId.equals(that.stateId) : that.stateId != null);

    }

    @Override
    public int hashCode() {
        return stateId != null ? stateId.hashCode() : 0;
    }

    @Override
    public StateSpinner createInstance(Type type) {
        return new StateSpinner();
    }
}
