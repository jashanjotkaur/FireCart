package com.e.shoppingcart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ProductActivity extends AppCompatActivity {

    private DatabaseReference dbRef;

    private Bitmap bmp;
    private ImageView img;
    private HashMap<ModelProduct, List<SizeQty>> inCart = new HashMap<ModelProduct, List<SizeQty>>();

    class SizeQty {
        public Integer qty;
        public String size;

        public SizeQty(Integer qty, String size) {
            this.qty = qty;
            this.size = size;
        }

        @Override
        public String toString() {
            return "Size: " + size + ", Qty: " + qty;
        }
    }

    private class GetImageTask extends AsyncTask<Void, Void, Void> {
        ModelProduct prod;
        TextView txtName;
        TextView txtCost;
        ImageView img;
        Spinner spinner;
        EditText txtQty;
        Button btnAddToCart;
        Button btnCheckout;

        public GetImageTask(ModelProduct prod, TextView txtName, TextView txtCost, ImageView img, Spinner spinner,
                            EditText txtQty, Button btnAddToCart, Button btnCheckout) {
            this.txtName = txtName;
            this.txtCost = txtCost;
            this.img = img;
            this.prod = prod;
            this.spinner = spinner;
            this.txtQty = txtQty;
            this.btnAddToCart = btnAddToCart;
            this.btnCheckout = btnCheckout;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL(prod.imgUrl);
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(Void result) {
            txtName.setText(prod.name);
            txtCost.setText(new Integer(prod.cost).toString());
            img.setImageBitmap(bmp);

            List<String> list = new ArrayList<String>();
            list.add("Small");
            list.add("Medium");
            list.add("Large");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ProductActivity.this, android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);

            btnAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String qty = txtQty.getText().toString();
                    String size = spinner.getSelectedItem().toString();

                    if(TextUtils.isEmpty(qty)) {
                        Toast.makeText(ProductActivity.this, "Invalid Quantity", Toast.LENGTH_SHORT).show();
                    } else {
                        SizeQty obj = new SizeQty(Integer.parseInt(qty), size);
                        if(inCart.get(prod) == null) {
                            //inCart.put(prod, Integer.parseInt(qty));
                            inCart.put(prod, new ArrayList<SizeQty>());
                            inCart.get(prod).add(obj);
                        } else {
                            inCart.get(prod).add(obj);
                        }
                    }
                }
            });

            btnCheckout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Iterator itr = inCart.entrySet().iterator();
                    while (itr.hasNext()) {
                        Map.Entry mapElement = (Map.Entry)itr.next();
                        System.out.println(mapElement.getKey() + " :::: ");
                        List<SizeQty> lst = (List<SizeQty>) mapElement.getValue();
                        for(int i =0; i < lst.size(); i++) {
                            System.out.println(lst.get(i));
                        }
                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        dbRef = FirebaseDatabase.getInstance().getReference().child("Product");

        final LinearLayout layout = findViewById(R.id.layoutProdImages);
        final LayoutInflater inflater = LayoutInflater.from(this);

        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ModelProduct prod = dataSnapshot.getValue(ModelProduct.class);
                System.out.println(prod);

                View view = inflater.inflate(R.layout.product_item, layout, false);
                TextView txtName = view.findViewById(R.id.txtviewProdName);
                TextView cost = view.findViewById(R.id.txtviewProdCost);
                ImageView img = view.findViewById(R.id.imgviewProdTest);
                Spinner spinner = view.findViewById(R.id.spinnerProdSize);
                EditText txtQty = view.findViewById(R.id.txtProdQty);
                Button btnAddToCart = view.findViewById(R.id.btnProdToCart);
                Button btnCheckout = view.findViewById(R.id.btnProdCheckout);
                new GetImageTask(prod, txtName, cost, img, spinner, txtQty, btnAddToCart, btnCheckout).execute();
                layout.addView(view);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
