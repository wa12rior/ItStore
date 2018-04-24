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

public class AddProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit);

        final Intent starterIntent = new Intent(this, MainActivity.class);
        Toolbar myChildToolbar = findViewById(R.id.app_bar);
        setSupportActionBar(myChildToolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Button increment = findViewById(R.id.increment);
        Button decrement = findViewById(R.id.decrement);
        changeQuantity(-1 , decrement);
        changeQuantity(1 , increment);

        Button save = findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView q = findViewById(R.id.quantity);
                String quantity = q.getText().toString();
                int error = 0;

                String[] details = new String[5];

                details[0] = checkField(R.id.product_name);
                details[1] = quantity;
                details[2] = checkField(R.id.price);
                details[3] = checkField(R.id.supplier_name);
                details[4] = checkField(R.id.supplier_phone);

                for(String detail : details) {
                    if (detail.equals("")) {
                        Toast.makeText(getApplicationContext(), "Fill up all empty fields", Toast.LENGTH_LONG).show();
                        error++;
                        break;
                    }
                }

                if (error == 0) {
                    StoreDbHelper sdh = new StoreDbHelper(getApplicationContext());

                    sdh.insertProduct(details);
                    sdh.displayDatabaseInfo();
                    Toast.makeText(getApplicationContext(), "Product successfully added", Toast.LENGTH_SHORT).show();
                }

                finish();
                startActivity(starterIntent);
            }
        });
    }

    private String checkField(int fieldId) {
        EditText et = findViewById(fieldId);

        String field = et.getText().toString().trim();

        if (field.length() > 0) {
            return field;
        }

        return "";
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
