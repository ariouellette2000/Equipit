package com.ouellette.equipit.model;

import android.os.Parcel;
import android.os.Parcelable;

//Parcelable interface
//1. Implements Parcelable
//2. Add private mData
//3. Add two methods (describeContents and writeToParcel
//4. Add final Parcelable Creator

public class Product implements Parcelable{
    private String p_id;
    private String p_cat_name;
    private String p_name;
    private String p_description;
    private String p_company;
    private String p_size;
    private Integer p_weight;
    private String p_waterproof;
    private String p_made_in;
    private String p_material;
    private Integer p_review;
    private String p_notes;

    //Parcelable interface
    private int mData;

    public Product() {
    }

    public Product(String p_id, String p_cat_name, String p_name, String p_description, String p_company, String p_size, Integer p_weight, String p_waterproof, String p_made_in, String p_material, Integer p_review, String p_notes) {
        this.p_id = p_id;
        this.p_cat_name = p_cat_name;
        this.p_name = p_name;
        this.p_description = p_description;
        this.p_company = p_company;
        this.p_size = p_size;
        this.p_weight = p_weight;
        this.p_waterproof = p_waterproof;
        this.p_made_in = p_made_in;
        this.p_material = p_material;
        this.p_review = p_review;
        this.p_notes = p_notes;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getP_cat_name() {
        return p_cat_name;
    }

    public void setP_cat_name(String p_cat_name) {
        this.p_cat_name = p_cat_name;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_description() {
        return p_description;
    }

    public void setP_description(String p_description) {
        this.p_description = p_description;
    }

    public String getP_company() {
        return p_company;
    }

    public void setP_company(String p_company) {
        this.p_company = p_company;
    }

    public String getP_size() {
        return p_size;
    }

    public void setP_size(String p_size) {
        this.p_size = p_size;
    }

    public Integer getP_weight() {
        return p_weight;
    }

    public void setP_weight(Integer p_weight) {
        this.p_weight = p_weight;
    }

    public String getP_waterproof() {
        return p_waterproof;
    }

    public void setP_waterproof(String p_waterproof) {
        this.p_waterproof = p_waterproof;
    }

    public String getP_made_in() {
        return p_made_in;
    }

    public void setP_made_in(String p_made_in) {
        this.p_made_in = p_made_in;
    }

    public String getP_material() {
        return p_material;
    }

    public void setP_material(String p_material) {
        this.p_material = p_material;
    }

    public Integer getP_review() {
        return p_review;
    }

    public void setP_review(Integer p_review) {
        this.p_review = p_review;
    }

    public String getP_notes() {
        return p_notes;
    }

    public void setP_notes(String p_notes) {
        this.p_notes = p_notes;
    }

    //Parcelable interface methods

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {

    }

    public static final Parcelable.Creator<com.ouellette.equipit.model.Product> CREATOR =
            new Parcelable.Creator<com.ouellette.equipit.model.Product>(){
                public com.ouellette.equipit.model.Product createFromParcel(Parcel in){
                    return new com.ouellette.equipit.model.Product(in);
                }

                public com.ouellette.equipit.model.Product[] newArray(int size){
                    return new com.ouellette.equipit.model.Product[size];
                }
            };
    private Product(Parcel in){
        mData = in.readInt();
    }
}
