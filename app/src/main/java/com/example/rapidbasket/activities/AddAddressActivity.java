package com.example.rapidbasket.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rapidbasket.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {

    EditText name,address,city,pincode,phoneNo;
    Toolbar toolbar;
    Button add_address_btn;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        toolbar=findViewById(R.id.add_address_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        auth=FirebaseAuth.getInstance();
        firestore =FirebaseFirestore.getInstance();

        name=findViewById(R.id.ad_name);
        address=findViewById(R.id.ad_address);
        city=findViewById(R.id.ad_city);
        phoneNo=findViewById(R.id.ad_phone);
        pincode=findViewById(R.id.ad_code);
        add_address_btn=findViewById(R.id.ad_add_address);

        add_address_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname=name.getText().toString().trim();
                String ucity=city.getText().toString().trim();
                String uaddr=address.getText().toString().trim();
                String upincode=pincode.getText().toString().trim();
                String uphone=phoneNo.getText().toString().trim();

                String final_addr="";

                if(!uname.isEmpty() && !ucity.isEmpty() && !uaddr.isEmpty() && !upincode.isEmpty() && !uphone.isEmpty()){
                    final_addr+=uname+", ";
                    final_addr+=ucity+", ";
                    final_addr+=uaddr+", ";
                    final_addr+=upincode+", ";
                    final_addr+=uphone;

                    Map<String,String> map = new HashMap<>();
                    map.put("Address",final_addr);

                    firestore.collection("AddressList").document(auth.getCurrentUser().getUid()).collection("Address").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(AddAddressActivity.this,"Address added",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AddAddressActivity.this,DetailsActivity.class));
                                finish();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(AddAddressActivity.this,"please fill all fields",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}