package com.gmail.wa12rior.itstore;

/**
 * Created by Friizza on 21.04.2018.
 */

public class Product {
    /** String resource ID for the product id */
    private String mId;

    /** String resource ID for the product name */
    private String mProductName;

    /** String resource ID for the product quantity */
    private String mQuantity;

    /** Audio resource ID for the product price */
    private String mPrice;

    /** Image resource ID for the product supplier name */
    private String mSupplierName;

    /** Image resource ID for the product supplier phone */
    private String mSupplierPhone;

    /**
     * Create a new Word object.
     *
     * @param id - id of the product
     * @param productName - name of the product
     * @param quantity - quantitiy of the product
     * @param price - price of the product
     * @param supplierName - supplier name
     * @param supplierPhone - supplier phone
     */
    public Product(String id, String productName, String quantity, String price, String supplierName, String supplierPhone) {
        mId = id;
        mProductName = productName;
        mQuantity = quantity;
        mPrice = price;
        mSupplierName = supplierName;
        mSupplierPhone = supplierPhone;
    }

    /**
     * Get the string resource ID for the product id.
     */
    public String getProductId() {
        return mId;
    }

    /**
     * Get the string resource ID for the product name.
     */
    public String getProductName() {
        return mProductName;
    }

    /**
     * Get the string resource ID for the product quantity.
     */
    public String getQuantity() {
        return mQuantity;
    }

    /**
     * Get the string resource ID for the product price .
     */
    public String getPrice() {
        return mPrice;
    }

    /**
     * Get the string resource ID for the product supplier name.
     */
    public String getSupplierName() {
        return mSupplierName;
    }

    /**
     * Get the string resource ID for the product supplier phone.
     */
    public String getSupplierPhone() {
        return mSupplierPhone;
    }
}
