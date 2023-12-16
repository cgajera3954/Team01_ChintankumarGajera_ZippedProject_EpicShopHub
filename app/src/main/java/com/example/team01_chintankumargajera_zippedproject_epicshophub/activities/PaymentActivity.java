package com.example.team01_chintankumargajera_zippedproject_epicshophub.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team01_chintankumargajera_zippedproject_epicshophub.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;

public class PaymentActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView subTotal, discount, shipping, totalAmount;
    Button payButton;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        toolbar = findViewById(R.id.payment_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        subTotal = findViewById(R.id.sub_total);
        discount = findViewById(R.id.discount);
        shipping = findViewById(R.id.shipping);
        totalAmount = findViewById(R.id.total_amt);
        payButton = findViewById(R.id.pay_btn);

        double amount = 0.0;
        amount = getIntent().getDoubleExtra("amount", 0.0);

        // Calculate shipping discount (assuming 5% of subtotal)
        double shippingDiscount = 0.05 * amount;

        // Format shipping discount to show two digits after the decimal point
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedShippingDiscount = decimalFormat.format(shippingDiscount);

        // Calculate shipping amount
        double shippingAmount = 0.05 * amount;

        // Format shipping Amount to show two digits after the decimal point
        String formattedShippingAmount = decimalFormat.format(shippingAmount);


        // Calculate final amount (subtotal - shipping discount + shipping amount)
        double finalAmount = amount - shippingDiscount + shippingAmount;

        // Set values to TextViews
        subTotal.setText("$" + amount);
        discount.setText("$" + formattedShippingDiscount);
        shipping.setText("$" + formattedShippingAmount);
        totalAmount.setText("$" + finalAmount);

        payButton.setOnClickListener(view -> {
            clearCart();
            Toast.makeText(this,"Order Placed Successfully",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PaymentActivity.this, MainActivity.class));
            finish();
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void clearCart() {
        FirebaseFirestore.getInstance().collection("AddToCart").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("User")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                            // Delete each document in the "User" collection
                            doc.getReference().delete();
                        }
                    }
                });
    }
}