package com.e.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        final HashMap<ModelProduct, ArrayList<SizeQty>> inCart =
                (HashMap<ModelProduct, ArrayList<SizeQty>>) getIntent().getSerializableExtra("Data");

        Iterator itr = inCart.entrySet().iterator();
        String toTxtView = "";
        while (itr.hasNext()) {
            Map.Entry mapElement = (Map.Entry) itr.next();
            ModelProduct prod = (ModelProduct) mapElement.getKey();
            ArrayList<SizeQty> lst = (ArrayList<SizeQty>) mapElement.getValue();

            for (int i = 0; i < lst.size(); i++) {
                toTxtView += prod.name + " (" + lst.get(i).size + ") - " + lst.get(i).qty + " items = $" + (prod.cost * lst.get(i).qty) + "\n";
            }
        }

        TextView txtShow = findViewById(R.id.txtviewPaymentItems);
        txtShow.setText(toTxtView);

        Button cash = findViewById(R.id.btnPaymentCash);
        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("PAID BY CASH");
                startActivity(new Intent(PaymentActivity.this, ThankYouActivity.class));
            }
        });

        Button credit = findViewById(R.id.btnPaymentCredit);
        credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("PAID BY CREDIT");
                startActivity(new Intent(PaymentActivity.this, ThankYouActivity.class));
            }
        });
    }
}
