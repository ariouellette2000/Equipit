package com.ouellette.equipit.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.ouellette.equipit.R;
import com.ouellette.equipit.model.Receipt;
import com.ouellette.equipit.model.Product;
import com.ouellette.equipit.util.Util;

import java.util.Date;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    //private SQLiteDatabase data;
    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            String CREATE_PRODUCT_TABLE = "CREATE TABLE " + Util.TABLE_NAME_PROD + "(" +
                    Util.KEY_P_ID + " TEXT NOT NULL PRIMARY KEY, " +
                    Util.KEY_P_CAT_NAME + " TEXT NOT NULL, " +
                    Util.KEY_P_NAME + " TEXT NOT NULL, " +
                    Util.KEY_P_DESCRIPTION + " TEXT, " +
                    Util.KEY_P_COMPANY + " TEXT, " +
                    Util.KEY_P_SIZE + " TEXT, " +
                    Util.KEY_P_WEIGHT + " INTEGER, " +
                    Util.KEY_P_WATERPROOF + " TEXT, " +
                    Util.KEY_P_MADE_IN + " TEXT, " +
                    Util.KEY_P_MATERIAL + " TEXT, " +
                    Util.KEY_P_REVIEW + " INTEGER, " +
                    Util.KEY_P_NOTES + " TEXT);";

            String CREATE_RECEIPT_TABLE = "CREATE TABLE " + Util.TABLE_NAME_REC + "(" +
                    Util.KEY_R_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Util.KEY_P_ID + " TEXT NOT NULL, " +
                    Util.KEY_R_PAY_DATE + " DATE, " +
                    Util.KEY_R_WARRANTY_DATE + " DATE, " +
                    Util.KEY_R_WARRANTY_LOCATION + " TEXT, " +
                    " CONSTRAINT FK_P_ID FOREIGN KEY (" + Util.KEY_P_ID + ") REFERENCES " +
                    Util.TABLE_NAME_PROD + " (" + Util.KEY_P_ID + ")" + ");";



            db.execSQL(CREATE_PRODUCT_TABLE);

            db.execSQL(CREATE_RECEIPT_TABLE);


        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String DROP_TABLE_PROD = String.valueOf(R.string.dp_drop + Util.TABLE_NAME_PROD);
        String DROP_TABLE_REC = String.valueOf(R.string.dp_drop + Util.TABLE_NAME_REC);
        db.execSQL(DROP_TABLE_PROD);
        db.execSQL(DROP_TABLE_REC);
        onCreate(db);
    }

    //add a product
    public void addProduct(Product product) {
        try{
        //get the db
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_P_ID, product.getP_id());
        values.put(Util.KEY_P_NAME, product.getP_name());
        values.put(Util.KEY_P_CAT_NAME, product.getP_cat_name());
        values.put(Util.KEY_P_DESCRIPTION, product.getP_description());
        values.put(Util.KEY_P_COMPANY, product.getP_company());
        values.put(Util.KEY_P_SIZE, product.getP_size());
        values.put(Util.KEY_P_WEIGHT, product.getP_weight());
        values.put(Util.KEY_P_WATERPROOF, product.getP_waterproof());
        values.put(Util.KEY_P_MADE_IN, product.getP_made_in());
        values.put(Util.KEY_P_MATERIAL, product.getP_material());
        values.put(Util.KEY_P_REVIEW, product.getP_review());
        values.put(Util.KEY_P_NOTES, product.getP_notes());

        db.insert(Util.TABLE_NAME_PROD, null, values);

        db.close();}
        catch(Exception e){
            e.printStackTrace();
        }


    }

    //add a contact
    public void addReceipt(Receipt receipt) {
        try{
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        values.put(Util.KEY_P_ID, receipt.getP_id());
        values.put(Util.KEY_R_PAY_DATE, dateFormat.format(receipt.getR_pay_date()));
        values.put(Util.KEY_R_WARRANTY_DATE, dateFormat.format(receipt.getR_warranty_date()));
        values.put(Util.KEY_R_WARRANTY_LOCATION, receipt.getR_warranty_location());

        db.insert(Util.TABLE_NAME_REC, null, values);

        db.close();}
        catch(Exception e){
          e.printStackTrace();
        }
    }



    public List<Product> getAllProduct() {
        List<Product> productList = new ArrayList<>();

        //get the db
        SQLiteDatabase db = this.getReadableDatabase();
        String selectAll = "SELECT * FROM " + Util.TABLE_NAME_PROD;
        Cursor cursor = db.rawQuery(selectAll, null);
        if(cursor.moveToFirst()){
            do{
                Product prod = new Product();
                prod.setP_id(cursor.getString(0));

                productList.add(prod);
            }while(cursor.moveToNext());
        }

        return productList;
    }

    public List<Receipt> getAllReceipt() {
        List<Receipt> receiptList = new ArrayList<>();

        //get the db
        SQLiteDatabase db = this.getReadableDatabase();
        String selectAll = "SELECT * FROM " + Util.TABLE_NAME_REC;
        Cursor cursor = db.rawQuery(selectAll, null);
        if(cursor.moveToFirst()){
            do{
                Receipt rec = new Receipt();
                rec.setP_id(cursor.getString(0));

                receiptList.add(rec);
            }while(cursor.moveToNext());
        }

        return receiptList;
    }


    public int getProductCount(){
        SQLiteDatabase db = this.getReadableDatabase();

        String countQuery = "SELECT * FROM " + Util.TABLE_NAME_PROD;

        Cursor cursor = db.rawQuery(countQuery,null);

        return cursor.getCount();
    }

    public int getReceiptCount(){
        SQLiteDatabase db = this.getReadableDatabase();

        String countQuery = "SELECT * FROM " + Util.TABLE_NAME_REC;

        Cursor cursor = db.rawQuery(countQuery,null);

        return cursor.getCount();
    }

    public List<Product> getAllCategories() {
        List<Product> catList = new ArrayList<>();

        //get the db
        SQLiteDatabase db = this.getReadableDatabase();
        String selectAllCat = "SELECT "+ Util.KEY_P_CAT_NAME +" FROM " + Util.TABLE_NAME_PROD +" GROUP BY "+Util.KEY_P_CAT_NAME;
        Cursor cursor = db.rawQuery(selectAllCat, null);
        if(cursor.moveToFirst()){
            do{
                Product prod = new Product();
                prod.setP_cat_name(cursor.getString(0));

                catList.add(prod);
            }while(cursor.moveToNext());
        }

        return catList;
    }

    public List<Product> getSpecificCategoryProduct(String cat_name){
        List<Product> productList = new ArrayList<>();

        //get the db
        SQLiteDatabase db = this.getReadableDatabase();
        String selectSpecificPro = "SELECT " + Util.KEY_P_ID +", "+ Util.KEY_P_NAME +", " + Util.KEY_P_COMPANY + ", "
                + Util.KEY_P_CAT_NAME + ", " + Util.KEY_P_DESCRIPTION + ", " + Util.KEY_P_SIZE +", " + Util.KEY_P_WEIGHT
                + ", " + Util.KEY_P_WATERPROOF + ", " + Util.KEY_P_MADE_IN + ", " + Util.KEY_P_MATERIAL +
                ", " + Util.KEY_P_REVIEW + ", " + Util.KEY_P_NOTES +
                " FROM " + Util.TABLE_NAME_PROD +" WHERE "+ Util.KEY_P_CAT_NAME + " = \""+ cat_name+"\"";
        Cursor cursor = db.rawQuery(selectSpecificPro, null);
        if(cursor.moveToFirst()){
            do{
                Product prod = new Product();
                prod.setP_id(cursor.getString(0));
                prod.setP_name(cursor.getString(1));
                prod.setP_company(cursor.getString(2));
                prod.setP_cat_name(cursor.getString(3));
                prod.setP_description(cursor.getString(4));
                prod.setP_size(cursor.getString(5));
                prod.setP_weight(cursor.getInt(6));
                prod.setP_waterproof(cursor.getString(7));
                prod.setP_made_in(cursor.getString(8));
                prod.setP_material(cursor.getString(9));
                prod.setP_review(cursor.getInt(10));
                prod.setP_notes(cursor.getString(11));

                productList.add(prod);
            }while(cursor.moveToNext());
        }

        return productList;
    }

    public Receipt getSpecificReceipt(Product product){
        Receipt receipt = new Receipt();

        //get the db
        SQLiteDatabase db = this.getReadableDatabase();
        String selectSpecificPro = "SELECT " + Util.KEY_R_PAY_DATE +", "+ Util.KEY_R_WARRANTY_DATE +", "
                + Util.KEY_R_WARRANTY_LOCATION +
                " FROM " + Util.TABLE_NAME_REC +" WHERE "+ Util.KEY_P_ID + " = \""+ product.getP_id()+"\"";

        Cursor cursor = db.rawQuery(selectSpecificPro, null);
        if(cursor.moveToFirst()) {
            do {
                Date paydate = null;
                try {
                    paydate = new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(cursor.getColumnIndexOrThrow(Util.KEY_R_PAY_DATE)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                receipt.setR_pay_date(paydate);

                Date wardate = null;
                try {
                    wardate = new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(cursor.getColumnIndexOrThrow(Util.KEY_R_WARRANTY_DATE)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                receipt.setR_warranty_date(wardate);

                receipt.setR_warranty_location(cursor.getString(2));
            } while (cursor.moveToNext());
        }
        return receipt;
    }

    //Update Product
    public int upgradeProduct(Product product){

        //get the db
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Util.KEY_P_NAME, product.getP_name());
        values.put(Util.KEY_P_COMPANY, product.getP_company());
        values.put(Util.KEY_P_CAT_NAME, product.getP_cat_name());
        values.put(Util.KEY_P_DESCRIPTION, product.getP_description());
        values.put(Util.KEY_P_SIZE, product.getP_size());
        values.put(Util.KEY_P_WEIGHT, product.getP_weight());
        values.put(Util.KEY_P_MADE_IN, product.getP_made_in());
        values.put(Util.KEY_P_MATERIAL, product.getP_material());
        values.put(Util.KEY_P_REVIEW, product.getP_review());
        values.put(Util.KEY_P_NOTES, product.getP_notes());


        return db.update(Util.TABLE_NAME_PROD, values,Util.KEY_P_ID+"=?", new String[]{String.valueOf(product.getP_id())});
    }

    //Update Receipt
    public int upgradeReceipt(Receipt receipt){
        //get the db
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        values.put(Util.KEY_P_ID, receipt.getP_id());
        values.put(Util.KEY_R_PAY_DATE, dateFormat.format(receipt.getR_pay_date()));
        values.put(Util.KEY_R_WARRANTY_DATE, dateFormat.format(receipt.getR_warranty_date()));
        values.put(Util.KEY_R_WARRANTY_LOCATION, receipt.getR_warranty_location());

        return db.update(Util.TABLE_NAME_REC, values,Util.KEY_P_ID+"=?", new String[]{String.valueOf(receipt.getP_id())});
    }

    public void deleteItem(Product product){

        //Reach the db
        SQLiteDatabase db = this.getWritableDatabase();

        //Delete product and receipt
        db.delete(Util.TABLE_NAME_PROD, Util.KEY_P_ID+"=?",new String[]{String.valueOf(product.getP_id())});
        db.delete(Util.TABLE_NAME_REC, Util.KEY_P_ID+"=?",new String[]{String.valueOf(product.getP_id())});

        db.close();
    }

}
