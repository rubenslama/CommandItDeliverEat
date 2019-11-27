package com.example.commanditdelivereat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class register_form extends AppCompatActivity {
    EditText mFirstnme,mLastname,mEmail,mPassword;
    Button mRegisterButton,mLoginButton;
    FirebaseAuth fAuth;
    //variable of each widget on this screen.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);
        mFirstnme=findViewById(R.id.Firstname);
        mLastname=findViewById(R.id.Lastname);
        mEmail=findViewById(R.id.Email);
        mPassword=findViewById(R.id.Password);
        mRegisterButton=findViewById(R.id.RegisterButton);
        mLoginButton=findViewById(R.id.LoginButton);
        fAuth=FirebaseAuth.getInstance();

//User already registered
        if (fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MapPermissionAndLogout.class));
        }

//Conditions to fill Email and Password.
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
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



                //register the user with firebase
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(register_form.this,"User successfully created",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MapPermissionAndLogout.class));
                        }else {
                            Toast.makeText(register_form.this,"Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

            mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
           });

    }
}
