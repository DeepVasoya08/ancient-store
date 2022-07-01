package com.example.rapidbasket.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.rapidbasket.R;
import com.example.rapidbasket.adapters.AllProductsAdapter;
import com.example.rapidbasket.models.AllProductsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AllProductsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AllProductsAdapter allProductsAdapter;
    List<AllProductsModel> allProductsModelList;

    Toolbar toolbar;

    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);

        toolbar=findViewById(R.id.all_prod_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String type=getIntent().getStringExtra("type");

        firestore=FirebaseFirestore.getInstance();

        recyclerView=findViewById(R.id.all_products_rec);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        allProductsModelList=new ArrayList<>();
        allProductsAdapter=new AllProductsAdapter(this,allProductsModelList);
        recyclerView.setAdapter(allProductsAdapter);


        if(type==null || type.isEmpty()){
            firestore.collection("AllProducts2").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot doc: task.getResult().getDocuments()){
                            AllProductsModel allProductsModel=doc.toObject(AllProductsModel.class);
                            allProductsModelList.add(allProductsModel);
                            allProductsAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
        if(type!=null && type.equalsIgnoreCase("men")){
            firestore.collection("AllProducts2").whereEqualTo("type","men").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot doc: task.getResult().getDocuments()){
                            AllProductsModel allProductsModel=doc.toObject(AllProductsModel.class);
                            allProductsModelList.add(allProductsModel);
                            allProductsAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
        if(type!=null && type.equalsIgnoreCase("women")){
            firestore.collection("AllProducts2").whereEqualTo("type","women").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot doc: task.getResult().getDocuments()){
                            AllProductsModel allProductsModel=doc.toObject(AllProductsModel.class);
                            allProductsModelList.add(allProductsModel);
                            allProductsAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
        if(type!=null && type.equalsIgnoreCase("kids")){
            firestore.collection("AllProducts2").whereEqualTo("type","kids").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot doc: task.getResult().getDocuments()){
                            AllProductsModel allProductsModel=doc.toObject(AllProductsModel.class);
                            allProductsModelList.add(allProductsModel);
                            allProductsAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
        if(type!=null && type.equalsIgnoreCase("camera")){
            firestore.collection("AllProducts2").whereEqualTo("type","camera").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot doc: task.getResult().getDocuments()){
                            AllProductsModel allProductsModel=doc.toObject(AllProductsModel.class);
                            allProductsModelList.add(allProductsModel);
                            allProductsAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
        if(type!=null && type.equalsIgnoreCase("shoes")){
            firestore.collection("AllProducts2").whereEqualTo("type","shoes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot doc: task.getResult().getDocuments()){
                            AllProductsModel allProductsModel=doc.toObject(AllProductsModel.class);
                            allProductsModelList.add(allProductsModel);
                            allProductsAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
        if(type!=null && type.equalsIgnoreCase("watch")){
            firestore.collection("AllProducts2").whereEqualTo("type","watch").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot doc: task.getResult().getDocuments()){
                            AllProductsModel allProductsModel=doc.toObject(AllProductsModel.class);
                            allProductsModelList.add(allProductsModel);
                            allProductsAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }
}