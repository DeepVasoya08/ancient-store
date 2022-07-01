package com.example.rapidbasket.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rapidbasket.R;
import com.example.rapidbasket.models.AllProductsModel;
import com.example.rapidbasket.models.PopularProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailsActivity extends AppCompatActivity {

    ImageView detailsImg, add_item,remove_item;
    TextView name,rating,desc,price,quantity;
    Button add_to_cart,buy_now;

    Toolbar toolbar;
    int totalQuantity=1;
    int totalPrice=0;

    PopularProductModel popularProductModel=null;

    AllProductsModel allProductsModel=null;

    FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();

        toolbar=findViewById(R.id.prod_details_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        final Object object=getIntent().getSerializableExtra("details");

        if(object instanceof PopularProductModel){
            popularProductModel = (PopularProductModel) object;
        }
        else if(object instanceof AllProductsModel){
            allProductsModel = (AllProductsModel) object;
        }

        detailsImg=findViewById(R.id.details_img);
        quantity=findViewById(R.id.quantity);
        add_item=findViewById(R.id.add_item);
        remove_item=findViewById(R.id.remove_item);

        name=findViewById(R.id.details_name);
        rating=findViewById(R.id.details_rating);
        desc=findViewById(R.id.details_desc);
        price=findViewById(R.id.details_price);

        add_to_cart=findViewById(R.id.add_to_cart);
        buy_now=findViewById(R.id.buy_now_1);

//        popular products
        if(popularProductModel != null){
            Glide.with(getApplicationContext()).load(popularProductModel.getImg_url()).into(detailsImg);
            name.setText(popularProductModel.getName());
            rating.setText(popularProductModel.getRating());
            desc.setText(popularProductModel.getDescription());
            price.setText(String.valueOf(popularProductModel.getPrice()));

            totalPrice=popularProductModel.getPrice()*totalQuantity;
        }

//      for all products
        if(allProductsModel != null){
            Glide.with(getApplicationContext()).load(allProductsModel.getImg_url()).into(detailsImg);
            name.setText(allProductsModel.getName());
            rating.setText(allProductsModel.getRating());
            desc.setText(allProductsModel.getDescription());
            price.setText(String.valueOf(allProductsModel.getPrice()));

            totalPrice=allProductsModel.getPrice()*totalQuantity;
        }

        buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DetailsActivity.this,AddressActivity.class);
                if(popularProductModel!=null){
                    intent.putExtra("item",popularProductModel);
                }
                if(allProductsModel!=null){
                    intent.putExtra("item",allProductsModel);
                }
                startActivity(intent);
            }
        });

        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addtoCart();
            }
        });

        add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalQuantity<10){
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                }
                if(popularProductModel != null){
                    totalPrice=popularProductModel.getPrice()*totalQuantity;
                }
                if(allProductsModel !=null){
                    totalPrice=allProductsModel.getPrice()*totalQuantity;
                }
            }
        });

        remove_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalQuantity>1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                }
            }
        });

    }

    private void addtoCart() {
        String saveCurrentTime,saveCurrentDate;

        Calendar calForDate=Calendar.getInstance();

        SimpleDateFormat curDate = new SimpleDateFormat("MM dd,yyy");
        saveCurrentDate=curDate.format(calForDate.getTime());

        SimpleDateFormat curTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=curTime.format(calForDate.getTime());

        final HashMap<String,Object> cartMap = new HashMap<>();
        cartMap.put("productName",name.getText().toString());
        cartMap.put("productPrice",price.getText().toString());
        cartMap.put("currentTime",saveCurrentTime);
        cartMap.put("currentDate",saveCurrentDate);
        cartMap.put("total_quantity",quantity.getText().toString());
        cartMap.put("total_price",totalPrice);

        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid()).collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(DetailsActivity.this,"Added to Cart",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}