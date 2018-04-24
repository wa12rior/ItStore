package com.gmail.wa12rior.itstore;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.wa12rior.itstore.data.StoreDbHelper;

public class EditProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit);

        final Intent starterIntent = new Intent(this, MainActivity.class);
        Toolbar myChildToolbar = findViewById(R.id.app_bar);
        setSupportActionBar(myChildToolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        final String s = getIntent().getStringExtra("product_id");
        final StoreDbHelper sdh = new StoreDbHelper(getApplicationContext());
        String[] results = sdh.findProduct(s);

        final EditText name = findViewById(R.id.product_name);
        final EditText price = findViewById(R.id.price);
        final EditText supName = findViewById(R.id.supplier_name);
        final EditText supPhone = findViewById(R.id.supplier_phone);
        final TextView quantity = findViewById(R.id.quantity);

        name.setText(results[0]);
        quantity.setText(results[1]);
        price.setText(results[2]);
        supName.setText(results[3]);
        supPhone.setText(results[4]);

        Button increment = findViewById(R.id.increment);
        Button decrement = findViewById(R.id.decrement);
        Button save = findViewById(R.id.save);

        changeQuantity(-1 , decrement);
        changeQuantity(1 , increment);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int error = 0;
                String[] updates = {
                        name.getText().toString(),
                        quantity.getText().toString(),
                        price.getText().toString(),
                        supName.getText().toString(),
                        supPhone.getText().toString()
                };

                for(String update : updates) {
                    if (update.equals("")) {
                        Toast.makeText(getApplicationContext(), "Fill up all empty fields", Toast.LENGTH_LONG).show();
                        error++;
                        break;
                    }
                }

                if (error == 0) {
                    sdh.updateProduct(updates, s);
                    Toast.makeText(getApplicationContext(), "Product updated successfully", Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(starterIntent);
                }
            }
        });
    }

    private void changeQuantity(final int step, Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView quantityView = findViewById(R.id.quantity);
                int quantity = Integer.parseInt(quantityView.getText().toString());

                quantity += step;

                if (quantity >= 0) {
                    quantityView.setText(String.valueOf(quantity));
                }
            }
        });
    }
}
