package com.gmail.wa12rior.itstore.data;

import android.provider.BaseColumns;

/**
 * Created by Friizza on 25.03.2018.
 */

public final class StoreContract {
    private StoreContract() {}

    public static final class StoreEntry implements BaseColumns {

        public final static String TABLE_NAME = "products";

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_PRODUCT_NAME ="name";

        public final static String COLUMN_PRODUCT_QUANTITY= "quantity";

        public final static String COLUMN_PRODUCT_PRICE = "price";

        public final static String COLUMN_PRODUCT_SUPPLIER_NAME = "supplier_name";

        public final static String COLUMN_PRODUCT_SUPPLIER_PHONE = "supplier_phone";

    }
}
