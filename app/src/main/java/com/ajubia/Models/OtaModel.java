package com.ajubia.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class OtaModel implements Parcelable{
    private String ota_id;
    private String businessName;
    private String copyRights;
    private String logo;
    private String favIcon;
    private String slider;
    private String status;
    private String businessSlogan;
    private String type;


    public OtaModel( Parcel in ) {
        ota_id = in.readString();
        businessName = in.readString();
        copyRights = in.readString();
        logo = in.readString();
        favIcon = in.readString();
        slider = in.readString();
        status = in.readString();
        businessSlogan = in.readString();
        type = in.readString();
    }

    public static final Creator<OtaModel> CREATOR = new Creator<OtaModel>() {
        @Override
        public OtaModel createFromParcel( Parcel in ) {
            return new OtaModel(in);
        }

        @Override
        public OtaModel[] newArray( int size ) {
            return new OtaModel[size];
        }
    };

    public OtaModel() {

    }

    public String getOta_id() {
        return ota_id;
    }

    public void setOta_id( String ota_id ) {
        this.ota_id = ota_id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName( String businessName ) {
        this.businessName = businessName;
    }

    public String getCopyRights() {
        return copyRights;
    }

    public void setCopyRights( String copyRights ) {
        this.copyRights = copyRights;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo( String logo ) {
        this.logo = logo;
    }

    public String getFavIcon() {
        return favIcon;
    }

    public void setFavIcon( String favIcon ) {
        this.favIcon = favIcon;
    }

    public String getSlider() {
        return slider;
    }

    public void setSlider( String slider ) {
        this.slider = slider;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus( String status ) {
        this.status = status;
    }

    public String getBusinessSlogan() {
        return businessSlogan;
    }

    public void setBusinessSlogan( String businessSlogan ) {
        this.businessSlogan = businessSlogan;
    }

    public String getType() {
        return type;
    }

    public void setType( String type ) {
        this.type = type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel( Parcel dest,int flags ) {
        dest.writeString(ota_id);
        dest.writeString(businessName);
        dest.writeString(copyRights);
        dest.writeString(logo);
        dest.writeString(favIcon);
        dest.writeString(slider);
        dest.writeString(status);
        dest.writeString(businessSlogan);
        dest.writeString(type);
    }
}
