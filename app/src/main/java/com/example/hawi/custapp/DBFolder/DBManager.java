package com.example.hawi.custapp.DBFolder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.hawi.custapp.InboxActivity;
import com.example.hawi.custapp.InvoiceItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hawi on 17/12/17.
 */

/*
{"order_id":146,"invoice_no":206,"shop_name":null,"shop_desc":null,"isRead":1,"inTrash":0,"isStr":0,"isFav":0,
"total_amount":"1532.00","date_invoice":"2017-11-19 23:10:31","bra_name":"الجامعة","com_name":"هايبر بنده",
"com_img":"hyper_panda","user_id":7,"invoice_status":1}
 */

public class DBManager extends SQLiteOpenHelper {
    private static final String DB_NAME = "inv_view";
    private static final int DB_VERSION = 1;
    private static final String TB_NAME = "invoice";
    private Context cmt;

    public DBManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        //context.deleteDatabase()
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TB_NAME+"(ordID INTEGER ,inv_no INTEGER ,isRead INTEGER ,inTrash INTEGER ," +
                "isStr INTEGER ,isFav INTEGER ,inv_stat INTEGER ,tot_amt REAL , date_inv TEXT , com_name TEXT , " +
                "bra_name TEXT , com_img TEXT , product TEXT )");
        //todo: I need sure if create database

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TB_NAME);
        onCreate(db);
    }

    public void addInvData(JSONArray response , Context addCmt){
        this.cmt = addCmt;
        Log.d("dbinvaddInv",response.toString());
        // Parsing json
        for (int count = 0; count < response.length(); count++) {
            try {
                JSONObject obj = response.getJSONObject(count);

                ContentValues val = new ContentValues();

                val.put("ordID",obj.getInt("order_id"));
                val.put("inv_no",obj.getInt("invoice_no"));
                val.put("isRead",obj.getInt("isRead"));
                val.put("inTrash",obj.getInt("inTrash"));
                val.put("isStr",obj.getInt("isStr"));
                val.put("isFav",obj.getInt("isFav"));
                val.put("inv_stat",obj.getInt("invoice_status"));
                val.put("tot_amt",obj.getDouble("total_amount"));

                val.put("com_img",obj.getString("com_img"));
                val.put("com_name",obj.getString("com_name"));
                val.put("bra_name",obj.getString("bra_name"));
                val.put("date_inv",obj.getString("date_invoice"));
                val.put("product",obj.getString("prod")+" ...");

                SQLiteDatabase db = this.getWritableDatabase();
                long res = db.insert(TB_NAME,null,val);
                if(res > 0){
                    Log.d("dbins","insert");
                }else{
                    Log.d("dbins","not insert");
                }

            }// end try
            catch(JSONException e) {
                e.printStackTrace();
            }// end catch
        }//end for loop
        getInboxFromDB();
    }

    public List<InvoiceItems> getInboxFromDB(){
        SQLiteDatabase db = this.getReadableDatabase();
        String qry = "SELECT * FROM "+TB_NAME+" WHERE inv_stat = 1 AND inTrash = 0 ";//
        Cursor cursor = db.rawQuery(qry,null);
        int cnt = 0;
        List<InvoiceItems> itemsList = new ArrayList<InvoiceItems>();
        if(cursor.moveToFirst()){
            do {
                cnt += 1;
                InvoiceItems items = new InvoiceItems();
                items.setImgUrl(cursor.getString(cursor.getColumnIndex("com_img")));
                items.setComName(cursor.getString(cursor.getColumnIndex("com_name")));
                items.setBraName(cursor.getString(cursor.getColumnIndex("bra_name")));
                items.setDate(cursor.getString(cursor.getColumnIndex("date_inv")));
                items.setProduct(cursor.getString(cursor.getColumnIndex("product")));

                items.setIsStr(cursor.getInt(cursor.getColumnIndex("isStr")));
                items.setIsFav(cursor.getInt(cursor.getColumnIndex("isFav")));
                items.setOrdID(cursor.getInt(cursor.getColumnIndex("ordID")));
                items.setInv_stat(cursor.getInt(cursor.getColumnIndex("inv_stat")));
                items.setInTrash(cursor.getInt(cursor.getColumnIndex("inTrash")));
                items.setIsRead(cursor.getInt(cursor.getColumnIndex("isRead")));
                items.setInvNum(cursor.getInt(cursor.getColumnIndex("inv_no")));

                items.setTot_amt(cursor.getDouble(cursor.getColumnIndex("tot_amt")));

                itemsList.add(items);

            }while (cursor.moveToNext());
        }
        Log.d("cnt: ",""+cnt);
        return itemsList;
    }

    public void updateFav(Integer favStat , Integer ordID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE "+TB_NAME+" SET isFav = "+Integer.toString(favStat)+" WHERE ordID = "+Integer.toString(ordID));
    }

    public void updateStr(Integer strStat , Integer ordID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE "+TB_NAME+" SET isStr = "+Integer.toString(strStat)+" WHERE ordID = "+Integer.toString(ordID));
    }

    public void updateTrash(Integer ordID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE "+TB_NAME+" SET inTrash = 1 WHERE ordID = "+Integer.toString(ordID));
        this.getInboxFromDB();
        //TODO: reload tableView

        int i =  0;
        int x  = 0;
        SQLiteDatabase d = this.getReadableDatabase();
        Cursor cursor = d.rawQuery("SELECT * FROM "+TB_NAME+" WHERE ordID = "+ordID , null);
        Log.d("upinv","ordID: "+x+"  / after: "+i);
        if(cursor.moveToFirst()) {
            do {
                i = cursor.getInt(cursor.getColumnIndex("inTrash"));
                x = cursor.getInt(cursor.getColumnIndex("ordID"));
            } while (cursor.moveToNext());

            Log.d("upinv", "ordID: " + x + " / after: " + i);
        }
    }

}
