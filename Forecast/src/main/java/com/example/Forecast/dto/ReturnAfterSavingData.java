package com.example.Forecast.dto;


import com.example.Forecast.entity.CurrentHourStatusEntity;

import java.io.Serializable;

public class ReturnAfterSavingData implements Serializable {
    private Integer val;
    private CurrentHourStatusEntity currentHourStatus;

    public Integer getVal() {
        return val;
    }

    public void setVal(Integer val) {
        this.val = val;
    }

    public CurrentHourStatusEntity getCurrentHourStatus() {
        return currentHourStatus;
    }

    public void setCurrentHourStatus(CurrentHourStatusEntity currentHourStatus) {
        this.currentHourStatus = currentHourStatus;
    }

    public ReturnAfterSavingData(Integer val, CurrentHourStatusEntity currentHourStatus) {

        this.val = val;
        this.currentHourStatus = currentHourStatus;
    }
}
