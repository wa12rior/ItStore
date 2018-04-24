package com.gmail.wa12rior.itstore;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.wa12rior.itstore.data.StoreDbHelper;

public class DetailsActivity extends AppCompatActivity {

    private String mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar myChildToolbar = findViewById(R.id.app_bar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        final Bundle extras = getIntent().getExtras();
        mId = extras.getString("id");

        if (extras != null) {

            TextView name = findViewById(R.id.name);
            name.setText(extras.getString("product_name"));

            TextView price = findViewById(R.id.price);
            price.setText(extras.getString("price"));

            TextView quantity = findViewById(R.id.quantity_number);
            quantity.setText(extras.getString("quantity"));

            TextView supName = findViewById(R.id.supplier_name);
            supName.setText(extras.getString("supplier_name"));

            TextView supPhone = findViewById(R.id.supplier_phone);
            supPhone.setText(extras.getString("supplier_phone"));
        }

        Button increment = findViewById(R.id.increment);
        Button decrement = findViewById(R.id.decrement);
        Button order = findViewById(R.id.order);

        changeQuantity(1, increment, Integer.parseInt(extras.getString("quantity")));
        changeQuantity(-1, decrement, Integer.parseInt(extras.getString("quantity")));

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + extras.getString("supplier_phone")));
                startActivity(intent);

            }
        });
    }

    private void changeQuantity(final int step, Button btn, final int maxQuantity) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView quantityView = findViewById(R.id.quantity_number);
                int quantity = Integer.parseInt(quantityView.getText().toString());

                quantity += step;

                if (quantity >= 0 && quantity <= maxQuantity) {
                    quantityView.setText(String.valueOf(quantity));
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                StoreDbHelper sdh = new StoreDbHelper(getApplicationContext());
                                sdh.deleteProduct(mId);
                                Intent starterIntent = new Intent(getApplicationContext(), MainActivity.class);
                                finish();
                                Toast.makeText(getApplicationContext(), "Product deleted successfully", Toast.LENGTH_LONG).show();
                                startActivity(starterIntent);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
                return true;

            case R.id.action_edit:
                Intent editIntent = new Intent(this, EditProductActivity.class);
                editIntent.putExtra("product_id", mId);
                finish();
                startActivity(editIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }


}
