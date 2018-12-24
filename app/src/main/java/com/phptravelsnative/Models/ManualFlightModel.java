package com.phptravelsnative.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ManualFlightModel implements Parcelable  {


    private String des = "";
    private String total_time = "";
    private String price = "";
    private String aero_name = "";
    private String currSymbol = "";
    private String currCode = "";
    private String id = "";
    private ArrayList<OneWay> models_array = new ArrayList<>();
    private ArrayList<OneWay> return_array = new ArrayList<>();


    public ManualFlightModel() {

    }


    protected ManualFlightModel(Parcel in) {
        des = in.readString();
        total_time = in.readString();
        price = in.readString();
        aero_name = in.readString();
        currSymbol = in.readString();
        currCode = in.readString();
        id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(des);
        dest.writeString(total_time);
        dest.writeString(price);
        dest.writeString(aero_name);
        dest.writeString(currSymbol);
        dest.writeString(currCode);
        dest.writeString(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ManualFlightModel> CREATOR = new Creator<ManualFlightModel>() {
        @Override
        public ManualFlightModel createFromParcel(Parcel in) {
            return new ManualFlightModel(in);
        }

        @Override
        public ManualFlightModel[] newArray(int size) {
            return new ManualFlightModel[size];
        }
    };

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setTotal_time(String total_time) {
        this.total_time = total_time;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setAero_name(String aero_name) {
        this.aero_name = aero_name;
    }

    public void setCurrSymbol(String currSymbol) {
        this.currSymbol = currSymbol;
    }

    public void setCurrCode(String currCode) {
        this.currCode = currCode;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setModels_array(ArrayList<OneWay> models_array) {
        this.models_array = models_array;
    }

    public void setReturn_array(ArrayList<OneWay> return_array) {
        this.return_array = return_array;
    }

    public String getTotal_time() {
        return total_time;
    }

    public String getPrice() {
        return price;
    }

    public String getAero_name() {
        return aero_name;
    }

    public String getCurrSymbol() {
        return currSymbol;
    }

    public String getCurrCode() {
        return currCode;
    }

    public String getId() {
        return id;
    }

    public ArrayList<OneWay> getModels_array() {
        return models_array;
    }

    public ArrayList<OneWay> getReturn_array() {
        return return_array;
    }
}
