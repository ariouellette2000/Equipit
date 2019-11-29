package com.ouellette.equipit.model;

import java.util.Date;



public class Receipt {

    private int r_id;
    private String p_id;
    private Date r_pay_date;
    private Date r_warranty_date;
    private String r_warranty_location;

    public Receipt() {
    }

    public Receipt(int r_id, String p_id, Date r_pay_date, Date r_warranty_date, String r_warranty_location) {
        this.r_id = r_id;
        this.p_id = p_id;
        this.r_pay_date = r_pay_date;
        this.r_warranty_date = r_warranty_date;
        this.r_warranty_location = r_warranty_location;
    }

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public Date getR_pay_date() {
        return r_pay_date;
    }

    public void setR_pay_date(Date r_pay_date) {
        this.r_pay_date = r_pay_date;
    }

    public Date getR_warranty_date() {
        return r_warranty_date;
    }

    public void setR_warranty_date(Date r_warranty_date) {
        this.r_warranty_date = r_warranty_date;
    }

    public String getR_warranty_location() {
        return r_warranty_location;
    }

    public void setR_warranty_location(String r_warranty_location) {
        this.r_warranty_location = r_warranty_location;
    }
}
