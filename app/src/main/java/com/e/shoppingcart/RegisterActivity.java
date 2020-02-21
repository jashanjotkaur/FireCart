package com.e.shoppingcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class RegisterActivity extends AppCompatActivity {

    private EditText txtEmail;
    private EditText txtPass;
    private Button btnRegister;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtEmail = findViewById(R.id.txtRegEmail);
        txtPass = findViewById(R.id.txtRegPass);
        btnRegister = findViewById(R.id.btnRegRegister);

        auth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                String pass = txtPass.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
                    Toast.makeText(RegisterActivity.this, "Invalid Input", Toast.LENGTH_SHORT).show();
                } else if(pass.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Password minimum 6", Toast.LENGTH_SHORT).show();
                } else {
                    addUser(email, pass);
                }
            }
        });
    }

    private void addUser(String email, String pass) {
        auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String toastMsg = "Registration Failed";
                        if(task.isSuccessful()) {
                            toastMsg = "Registration Success";
                        } else {
                            System.out.println(task.getException().toString());
                            System.out.println(task.getResult().toString());
                        }
                        Toast.makeText(RegisterActivity.this, toastMsg, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
