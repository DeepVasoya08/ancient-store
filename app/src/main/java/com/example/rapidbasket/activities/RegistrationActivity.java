package com.example.rapidbasket.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rapidbasket.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    EditText name,email,password;
    private FirebaseAuth auth;

//    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        auth=FirebaseAuth.getInstance();

        if(auth.getCurrentUser() !=null){
            startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
        }

        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);


//        sharedPreferences = getSharedPreferences("OnBoardingScreen",MODE_PRIVATE);
//        boolean isFirstTime = sharedPreferences.getBoolean("firstTime",true);
//
//        if (isFirstTime){
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putBoolean("firstTime",false);
//            editor.commit();
//
//            Intent intent = new Intent(RegistrationActivity.this,OnBoardActivity.class);
//            startActivity(intent);
//            finish();
//        }

    }

    public void signup(View view) {

        String uName = name.getText().toString();
        String uEmail = email.getText().toString();
        String uPass = password.getText().toString();

        if (TextUtils.isEmpty(uName)){
            Toast.makeText(this,"Enter your name!",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(uEmail)){
            Toast.makeText(this,"Enter your email!",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(uPass)){
            Toast.makeText(this,"Enter your password!",Toast.LENGTH_SHORT).show();
            return;
        }

        if (uPass.length() <6){
            Toast.makeText(this,"Too short, password length must be greater than 6 characters",Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(uEmail,uPass).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegistrationActivity.this,"Registration Successfull",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(RegistrationActivity.this,"Registration Failed"+task.getException(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void signin(View view) {
        startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
    }
}