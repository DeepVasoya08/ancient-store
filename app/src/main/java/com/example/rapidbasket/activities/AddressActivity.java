package com.example.rapidbasket.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.rapidbasket.R;

import com.example.rapidbasket.adapters.AddressAdapter;
import com.example.rapidbasket.models.AddressModel;
import com.example.rapidbasket.models.AllProductsModel;
import com.example.rapidbasket.models.CartModel;
import com.example.rapidbasket.models.PopularProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity implements AddressAdapter.SelectedAddress {

    Button address,payment;
    Toolbar toolbar;
    RecyclerView recyclerView;
    private AddressAdapter addressAdapter;
    private List<AddressModel> list;
    String mAddr="";

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        toolbar=findViewById(R.id.address_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        get data from details activity
        Object object=getIntent().getSerializableExtra("item");

        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        recyclerView=findViewById(R.id.address_recycler);
        address =findViewById(R.id.add_address_btn);
        payment=findViewById(R.id.payment_btn);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        list=new ArrayList<>();
        addressAdapter=new AddressAdapter(getApplicationContext(),list,this);

        recyclerView.setAdapter(addressAdapter);

        firestore.collection("AddressList").document(auth.getCurrentUser().getUid()).collection("Address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot doc: task.getResult().getDocuments()){
                        AddressModel addressModel=doc.toObject(AddressModel.class);
                        list.add(addressModel);
                        addressAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double amount=0.0;
                if(object instanceof PopularProductModel){
                    PopularProductModel popularProductModel=(PopularProductModel) object;
                    amount=popularProductModel.getPrice();
                }
                if(object instanceof AllProductsModel){
                    AllProductsModel allProductsModel=(AllProductsModel) object;
                    amount=allProductsModel.getPrice();
                }
                Intent intent=new Intent(AddressActivity.this,PaymentActivity.class);
                intent.putExtra("amount",amount);
                startActivity(intent);
            }
        });


        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddressActivity.this,AddAddressActivity.class));
            }
        });
    }

    @Override
    public void setAddress(String address) {

        mAddr=address;

    }
}