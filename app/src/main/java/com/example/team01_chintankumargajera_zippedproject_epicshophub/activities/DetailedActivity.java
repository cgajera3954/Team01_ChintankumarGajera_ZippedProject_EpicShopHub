package com.example.team01_chintankumargajera_zippedproject_epicshophub.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.team01_chintankumargajera_zippedproject_epicshophub.R;
import com.example.team01_chintankumargajera_zippedproject_epicshophub.models.NewProductsModel;
import com.example.team01_chintankumargajera_zippedproject_epicshophub.models.PopularProductsModel;
import com.example.team01_chintankumargajera_zippedproject_epicshophub.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {


    ImageView detailedImg;
    TextView rating, name, description, price, quantity;
    Button addToCart, buyNow;

    ImageView addItems, removeItems;

    Toolbar toolbar;

    int totalQuantity = 1;
    int totalPrice = 0;


    NewProductsModel newProductsModel = null;
    //Popular Products


    PopularProductsModel popularProductsModel = null;
    ShowAllModel showAllModel = null;
    private FirebaseFirestore firestore;

    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);


        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final Object obj = getIntent().getSerializableExtra("detailed");


        if (obj instanceof NewProductsModel) {

            newProductsModel = (NewProductsModel) obj;

        } else if (obj instanceof PopularProductsModel) {

            popularProductsModel = (PopularProductsModel) obj;

        } else if (obj instanceof ShowAllModel) {

            showAllModel = (ShowAllModel) obj;

        }


        toolbar = findViewById(R.id.detailed_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        quantity = findViewById(R.id.quantity);
        detailedImg = findViewById(R.id.detailed_img);
        name = findViewById(R.id.detailed_name);
        rating = findViewById(R.id.rating);
        description = findViewById(R.id.detailed_desc);
        price = findViewById(R.id.detailed_price);
        addToCart = findViewById(R.id.add_to_cart);
        buyNow = findViewById(R.id.buy_now);
        addItems = findViewById(R.id.add_item);
        removeItems = findViewById(R.id.remove_item);


        // New Products

        if (newProductsModel != null) {
            // Load and display the image using Glide
            Glide.with(getApplicationContext()).load(newProductsModel.getImg_url()).into(detailedImg);

            // Set text for TextViews
            name.setText(newProductsModel.getName());
            rating.setText(newProductsModel.getRating()); // Assuming "getRating()" returns a float value
            description.setText(newProductsModel.getDescription());
            price.setText(String.valueOf(newProductsModel.getPrice()));
            name.setText(newProductsModel.getName());

            totalPrice = newProductsModel.getPrice() * totalQuantity;
        }

        // Popular Products

        if (popularProductsModel != null) {
            // Load and display the image using Glide
            Glide.with(getApplicationContext()).load(popularProductsModel.getImg_url()).into(detailedImg);

            // Set text for TextViews
            name.setText(popularProductsModel.getName());
            rating.setText(popularProductsModel.getRating()); // Assuming "getRating()" returns a float value
            description.setText(popularProductsModel.getDescription());
            price.setText(String.valueOf(popularProductsModel.getPrice()));
            name.setText(popularProductsModel.getName());

            totalPrice = popularProductsModel.getPrice() * totalQuantity;

        }

        // Show All Products

        if (showAllModel != null) {
            // Load and display the image using Glide
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailedImg);

            // Set text for TextViews
            name.setText(showAllModel.getName());
            rating.setText(showAllModel.getRating()); // Assuming "getRating()" returns a float value
            description.setText(showAllModel.getDescription());
            price.setText(String.valueOf(showAllModel.getPrice()));
            name.setText(showAllModel.getName());

            totalPrice = showAllModel.getPrice() * totalQuantity;

        }


        addToCart.setOnClickListener(view -> {
            addToCart();
        });

        addItems.setOnClickListener(view -> {
            if (totalQuantity < 10) {
                totalQuantity++;
                quantity.setText(String.valueOf(totalQuantity));

                if (newProductsModel != null) {
                    totalPrice = newProductsModel.getPrice() * totalQuantity;
                }
                if (popularProductsModel != null) {
                    totalPrice = popularProductsModel.getPrice() * totalQuantity;
                }

                if (showAllModel != null) {
                    totalPrice = showAllModel.getPrice() * totalQuantity;

                }
            }
        });

        removeItems.setOnClickListener(view -> {
            if (totalQuantity > 1) {
                totalQuantity--;
                quantity.setText(String.valueOf(totalQuantity));
            }
        });

        buyNow.setOnClickListener(view->{
            startActivity(new Intent(DetailedActivity.this,AddressActivity.class));
        });
    }

    public void addToCart() {
        String saveCurrentTime, saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String, Object> cartMap = new HashMap<>();

        cartMap.put("productName", name.getText().toString());
        cartMap.put("productPrice", price.getText().toString());
        cartMap.put("currentTime", saveCurrentTime);
        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("totalQuantity", quantity.getText().toString());
        cartMap.put("totalPrice", totalPrice);

        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid()).collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(DetailedActivity.this, "Added to a cart", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}