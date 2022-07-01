package com.example.rapidbasket.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {

    EditText email,password;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth=FirebaseAuth.getInstance();
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
    }

    public void signin(View view) {
        String uEmail = email.getText().toString();
        String uPass = password.getText().toString();

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
        auth.signInWithEmailAndPassword(uEmail,uPass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"Logged In",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(LoginActivity.this,"Login Failed"+task.getException(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void signup(View view) {
        startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
    }
}