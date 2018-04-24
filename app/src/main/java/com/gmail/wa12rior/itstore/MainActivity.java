package com.gmail.wa12rior.itstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

import com.gmail.wa12rior.itstore.data.StoreDbHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent detailIntent = new Intent(this, DetailsActivity.class);

        Toolbar myToolbar = findViewById(R.id.app_bar);
        setSupportActionBar(myToolbar);

        StoreDbHelper sdh = new StoreDbHelper(this);
        ArrayList<String[]> databaseProducts = sdh.getDatabaseProducts();

        if (databaseProducts.size() == 0) {
            LinearLayout ll = findViewById(R.id.wrapper);
            TextView tx = new TextView(this);

            TableRow.LayoutParams paramsExample = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1.0f);

            tx.setTextColor(getResources().getColor(R.color.colorPrimary));
            tx.setTextSize(32);
            tx.setPadding(20,20,20,20);
            tx.setText(R.string.default_db);
            tx.setLayoutParams(paramsExample);
            ll.addView(tx);

        } else {
            final ArrayList<Product> products = new ArrayList<>();

            for(String[] product : databaseProducts) {
                products.add(new Product(product[0], product[1], product[2], product[3], product[4], product[5]));
            }

            ProductAdapter adapter = new ProductAdapter(this, products);
            final ListView listView = findViewById(R.id.list);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Product product = products.get(i);
                    TextView q = findViewById(R.id.quantity);

                    detailIntent.putExtra("id", product.getProductId());
                    detailIntent.putExtra("product_name", product.getProductName());
                    detailIntent.putExtra("price", product.getPrice());
                    detailIntent.putExtra("quantity",  product.getQuantity());
                    detailIntent.putExtra("supplier_name", product.getSupplierName());
                    detailIntent.putExtra("supplier_phone", product.getSupplierPhone());
                    startActivity(detailIntent);
                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {

            // Create new AddProductActivity Intent

            Intent intent = new Intent(this, AddProductActivity.class);
            startActivity(intent);
            return true;
        } else {

            return super.onOptionsItemSelected(item);
        }
    }

}
