package com.example.commanditdelivereat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText mEmail,mPassword;
    Button mLoginButton, mForgottenPassword;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmail=findViewById(R.id.Email);
        mPassword=findViewById(R.id.Password);
        mLoginButton=findViewById(R.id.LoginButton);
        mForgottenPassword=findViewById(R.id.PasswordForgotten);
        fAuth= FirebaseAuth.getInstance();

       mLoginButton.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               String email= mEmail.getText().toString().trim();
               String password=mPassword.getText().toString().trim();
               if (TextUtils.isEmpty(email)){
                   mEmail.setError("email is required.");
                   return;
               }
               if (TextUtils.isEmpty(password)){
                   mPassword.setError("password is empty");
                   return;
               }
               if (password.length()<6){
                   mPassword.setError("Password is weak.it's must be must than 6 characters");
                   return;
               }


               fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()){
                           Toast.makeText(LoginActivity.this,"User successfully created",Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                       }
                       else {
                           Toast.makeText(LoginActivity.this,"Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                       }
                       }

               });
           }
       });

    }
}
