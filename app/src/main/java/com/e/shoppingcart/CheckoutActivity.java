package com.e.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {

    final ArrayList<Integer> removed = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        final HashMap<ModelProduct, ArrayList<SizeQty>> inCart =
                (HashMap<ModelProduct, ArrayList<SizeQty>>) getIntent().getSerializableExtra("Data");

        final LinearLayout layout = findViewById(R.id.layoutCheckout);
        final LayoutInflater inflater = LayoutInflater.from(this);

        Iterator itr = inCart.entrySet().iterator();
        int ctr = 0;
        while (itr.hasNext()) {
            Map.Entry mapElement = (Map.Entry)itr.next();
            ModelProduct prod = (ModelProduct) mapElement.getKey();
            ArrayList<SizeQty> lst = (ArrayList<SizeQty>) mapElement.getValue();

            for(int i = 0; i < lst.size(); i++) {
                View view = inflater.inflate(R.layout.checkoout_item, layout, false);
                TextView txtName = view.findViewById(R.id.txtviewChkoutName);
                txtName.setText(prod.name + " (" + lst.get(i).size + ")");
                TextView cost = view.findViewById(R.id.txtviewChkoutCost);
                cost.setText(prod.cost.toString());
                TextView qty = view.findViewById(R.id.txtviewChkoutQty);
                qty.setText(lst.get(i).qty.toString());
                Button btnRemove = view.findViewById(R.id.btnChkoutRemove);
                btnRemove.setOnClickListener(new onClickListener(ctr));

                layout.addView(view);
                ctr++;
            }
        }

        View view = inflater.inflate(R.layout.checkout_item_payment, layout, false);
        Button btnPayment = view.findViewById(R.id.btnMoveToPayment);
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<ModelProduct, ArrayList<SizeQty>> inCartTwo = new HashMap<ModelProduct, ArrayList<SizeQty>>();
                Iterator itr = inCart.entrySet().iterator();
                int ctr = 0;

                while (itr.hasNext()) {
                    Map.Entry mapElement = (Map.Entry)itr.next();
                    ModelProduct prod = (ModelProduct) mapElement.getKey();
                    ArrayList<SizeQty> lst = (ArrayList<SizeQty>) mapElement.getValue();

                    for(int i = 0; i < lst.size(); i++) {
                        if(removed.contains(ctr) == false) {
                            if(inCartTwo.get(prod) == null) {
                                inCartTwo.put(prod, new ArrayList<SizeQty>());
                                inCartTwo.get(prod).add(lst.get(i));
                            } else {
                                inCartTwo.get(prod).add(lst.get(i));
                            }
                        }
                        ctr++;
                    }
                }

                Intent intent = new Intent(CheckoutActivity.this, PaymentActivity.class);
                intent.putExtra("Data", (Serializable) inCartTwo);
                startActivity(intent);
            }
        });
        layout.addView(view);

    }

    class onClickListener implements View.OnClickListener {

        int id;

        public onClickListener(int id) {
            this.id = id;
        }

        @Override
        public void onClick(View v) {
            removed.add(id);
            Button btnRemove = v.findViewById(R.id.btnChkoutRemove);
            btnRemove.setBackgroundColor(Color.RED);
        }
    }
}
