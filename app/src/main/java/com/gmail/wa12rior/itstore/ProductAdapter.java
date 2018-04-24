package com.gmail.wa12rior.itstore;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.gmail.wa12rior.itstore.data.StoreDbHelper;

import java.util.ArrayList;

/**
 * Created by Friizza on 21.04.2018.
 */

public class ProductAdapter extends ArrayAdapter<Product> {

/**
 * Create a new {@link ProductAdapter} object.
 *
 * @param context is the current context (i.e. Activity) that the adapter is being created in.
 * @param products is the list of {@link Product}s to be displayed.
 */

    private Context ctx;

    public ProductAdapter(Context context, ArrayList<Product> products) {
        super(context, 0, products);
        ctx = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link Product} object located at this position in the list
        final Product currentProduct = getItem(position);

        TextView name = listItemView.findViewById(R.id.name);
        name.setText(currentProduct.getProductName());

        TextView price = listItemView.findViewById(R.id.price);
        price.setText(currentProduct.getPrice());

        final TextView quantity = listItemView.findViewById(R.id.quantity);
        quantity.setText(currentProduct.getQuantity());

        final Button sale = listItemView.findViewById(R.id.sale);

        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String q = quantity.getText().toString();
                int count = Integer.parseInt(q);
                StoreDbHelper sdh = new StoreDbHelper(getContext());

                count--;

                if (count == 0) {
                    sdh.deleteProduct(currentProduct.getProductId());
                    quantity.setText("Sold");
                    sale.setOnClickListener(null);
                    Intent i1 = new Intent (ctx, MainActivity.class);
                    ctx.startActivity(i1);
                } else {
                    sdh.updateProduct(
                            new String[]{
                                    currentProduct.getProductName(),
                                    String.valueOf(count),
                                    currentProduct.getPrice(),
                                    currentProduct.getSupplierName(),
                                    currentProduct.getSupplierPhone(),
                            }, currentProduct.getProductId());
                    quantity.setText(String.valueOf(count));
                }
            }
        });

        return listItemView;
    }
}