package com.gmail.wa12rior.itstore.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.gmail.wa12rior.itstore.data.StoreContract.StoreEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Friizza on 25.03.2018.
 */

public class StoreDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = StoreDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "store.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link StoreDbHelper}.
     *
     * @param context of the app
     */
    public StoreDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_PRODUCTS_TABLE =  "CREATE TABLE " + StoreEntry.TABLE_NAME + " ("
                + StoreEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + StoreEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
                + StoreEntry.COLUMN_PRODUCT_QUANTITY + " INTEGER NOT NULL, "
                + StoreEntry.COLUMN_PRODUCT_PRICE + " INTEGER NOT NULL, "
                + StoreEntry.COLUMN_PRODUCT_SUPPLIER_NAME + " TEXT NOT NULL, "
                + StoreEntry.COLUMN_PRODUCT_SUPPLIER_PHONE + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_PRODUCTS_TABLE);
    }

    public void updateProduct(String[] updates, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(StoreEntry.COLUMN_PRODUCT_NAME, updates[0]);
        values.put(StoreEntry.COLUMN_PRODUCT_QUANTITY, updates[1]);
        values.put(StoreEntry.COLUMN_PRODUCT_PRICE, updates[2]);
        values.put(StoreEntry.COLUMN_PRODUCT_SUPPLIER_NAME, updates[3]);
        values.put(StoreEntry.COLUMN_PRODUCT_SUPPLIER_PHONE, updates[4]);

        db.update(StoreEntry.TABLE_NAME, values, StoreEntry._ID + "="+id, null);
    }

    public String[] findProduct(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] ids = {id};
        String[] results = new String[5];
        String selection = StoreEntry._ID + " = ?";
        String[] projection = {
                StoreEntry._ID,
                StoreEntry.COLUMN_PRODUCT_NAME,
                StoreEntry.COLUMN_PRODUCT_QUANTITY,
                StoreEntry.COLUMN_PRODUCT_PRICE,
                StoreEntry.COLUMN_PRODUCT_SUPPLIER_NAME,
                StoreEntry.COLUMN_PRODUCT_SUPPLIER_PHONE };

        Cursor cursor = db.query(
                StoreEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                selection,                  // The columns for the WHERE clause
                ids,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);

        try {
            cursor.moveToFirst();
            results[0] = cursor.getString(cursor.getColumnIndex(StoreEntry.COLUMN_PRODUCT_NAME));
            results[1] = cursor.getString(cursor.getColumnIndex(StoreEntry.COLUMN_PRODUCT_QUANTITY));
            results[2] = cursor.getString(cursor.getColumnIndex(StoreEntry.COLUMN_PRODUCT_PRICE));
            results[3] = cursor.getString(cursor.getColumnIndex(StoreEntry.COLUMN_PRODUCT_SUPPLIER_NAME));
            results[4] = cursor.getString(cursor.getColumnIndex(StoreEntry.COLUMN_PRODUCT_SUPPLIER_PHONE));

        } finally {
            cursor.close();
        }

        return results;
    }

    public void insertProduct(String[] details) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(StoreEntry.COLUMN_PRODUCT_NAME, details[0]);
        values.put(StoreEntry.COLUMN_PRODUCT_QUANTITY, details[1]);
        values.put(StoreEntry.COLUMN_PRODUCT_PRICE, details[2]);
        values.put(StoreEntry.COLUMN_PRODUCT_SUPPLIER_NAME, details[3]);
        values.put(StoreEntry.COLUMN_PRODUCT_SUPPLIER_PHONE, details[4]);

        long newRowId = db.insert(StoreEntry.TABLE_NAME, null, values);
    }

    public boolean deleteProduct(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(StoreEntry.TABLE_NAME, StoreEntry._ID + "=" + id, null) > 0;
    }

    public ArrayList<String[]> getDatabaseProducts() {
        ArrayList<String[]> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                StoreEntry._ID,
                StoreEntry.COLUMN_PRODUCT_NAME,
                StoreEntry.COLUMN_PRODUCT_QUANTITY,
                StoreEntry.COLUMN_PRODUCT_PRICE,
                StoreEntry.COLUMN_PRODUCT_SUPPLIER_NAME,
                StoreEntry.COLUMN_PRODUCT_SUPPLIER_PHONE };

        Cursor cursor = db.query(
                StoreEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        try {

            int id = cursor.getColumnIndex(StoreEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(StoreEntry.COLUMN_PRODUCT_NAME);
            int quantityColumnIndex = cursor.getColumnIndex(StoreEntry.COLUMN_PRODUCT_QUANTITY);
            int priceColumnIndex = cursor.getColumnIndex(StoreEntry.COLUMN_PRODUCT_PRICE);
            int supNameColumnIndex = cursor.getColumnIndex(StoreEntry.COLUMN_PRODUCT_SUPPLIER_NAME);
            int supPhoneColumnIndex = cursor.getColumnIndex(StoreEntry.COLUMN_PRODUCT_SUPPLIER_PHONE);

            // filling list of String arrays with database data.

            while (cursor.moveToNext()) {
                products.add(new String[]{
                        cursor.getString(id),
                        cursor.getString(nameColumnIndex),
                        cursor.getString(quantityColumnIndex),
                        cursor.getString(priceColumnIndex),
                        cursor.getString(supNameColumnIndex),
                        cursor.getString(supPhoneColumnIndex)
                });
            }
        } finally {
            cursor.close();
        }

        return products;
    }

    public void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                StoreEntry._ID,
                StoreEntry.COLUMN_PRODUCT_NAME,
                StoreEntry.COLUMN_PRODUCT_QUANTITY,
                StoreEntry.COLUMN_PRODUCT_PRICE,
                StoreEntry.COLUMN_PRODUCT_SUPPLIER_NAME,
                StoreEntry.COLUMN_PRODUCT_SUPPLIER_PHONE };

        Cursor cursor = db.query(
                StoreEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

       try {

            int idColumnIndex = cursor.getColumnIndex(StoreEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(StoreEntry.COLUMN_PRODUCT_NAME);

            while (cursor.moveToNext()) {

                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);

                Log.v(currentID + "", currentName + "");
            }
        } finally {
            cursor.close();
        }
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}