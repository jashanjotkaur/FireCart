package com.e.shoppingcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText txtEmail;
    private EditText txtPass;
    private Button btnLogin;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = findViewById(R.id.txtLoginEmail);
        txtPass = findViewById(R.id.txtLoginPass);
        btnLogin = findViewById(R.id.btnLoginLogin);

        auth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                String pass = txtPass.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
                    Toast.makeText(LoginActivity.this, "Invalid Input", Toast.LENGTH_SHORT).show();
                } else {
                    signIn(email, pass);
                }
            }
        });
    }

    private void signIn(String email, String pass) {
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(LoginActivity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String toastMsg = "Login Failed";
                        if(task.isSuccessful()) {
                            toastMsg = "Login Success";
                            // Move to new Activity
                        } else {

                        }
                        Toast.makeText(LoginActivity.this, toastMsg, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
