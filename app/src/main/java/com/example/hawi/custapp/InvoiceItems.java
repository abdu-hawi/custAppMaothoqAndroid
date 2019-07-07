package com.example.hawi.custapp;

import android.content.Context;

import com.example.hawi.custapp.DBFolder.DBManager;

/**
 * Created by Hawi on 11/12/17.
 */

public class InvoiceItems {
    private String imgUrl;
    private String date;
    private String comName;
    private String braName;
    private String product;
    private int isStr , isFav , ordID , useID , inv_stat , inTrash , isRead, invNum;
    private double tot_amt;

    public InvoiceItems(){

    }

    public InvoiceItems(String imgUrl, String date, String comName, String braName, String product,
                        int isStr, int isFav, int ordID, int useID, int inv_stat, int inTrash, int isRead,
                        int invNum ,double tot_amt) {
        this.product = product;
        this.imgUrl = imgUrl;
        this.date = date;
        this.comName = comName;
        this.braName = braName;
        this.isStr = isStr;
        this.isFav = isFav;
        this.ordID = ordID;
        this.useID = useID;
        this.inv_stat = inv_stat;
        this.inTrash = inTrash;
        this.isRead = isRead;
        this.tot_amt = tot_amt;
        this.invNum = invNum;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getInvNum() {
        return invNum;
    }

    public void setInvNum(int invNum) {
        this.invNum = invNum;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getBraName() {
        return braName;
    }

    public void setBraName(String braName) {
        this.braName = braName;
    }

    public int getIsStr() {
        return isStr;
    }

    public void setIsStr(int isStr) {
        this.isStr = isStr;
    }

    public int getIsFav() {
        return isFav;
    }

    public void setIsFav(int isFav) {
        this.isFav = isFav;
    }

    public int getOrdID() {
        return ordID;
    }

    public void setOrdID(int ordID) {
        this.ordID = ordID;
    }

    public int getUseID() {
        return useID;
    }

    public void setUseID(int useID) {
        this.useID = useID;
    }

    public int getInv_stat() {
        return inv_stat;
    }

    public void setInv_stat(int inv_stat) {
        this.inv_stat = inv_stat;
    }

    public int getInTrash() {
        return inTrash;
    }

    public void setInTrash(int inTrash) {
        this.inTrash = inTrash;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public double getTot_amt() {
        return tot_amt;
    }

    public void setTot_amt(double tot_amt) {
        this.tot_amt = tot_amt;
    }
}
