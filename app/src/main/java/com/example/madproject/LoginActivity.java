package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText userNameEdt , pwdEdt;
    private Button Loginbtn;
    private ProgressBar loadingPB;
    private TextView RegisterTV;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar ().setTitle ("The Food Express") ;

        setContentView(R.layout.activity_login);
        userNameEdt = findViewById(R.id.idEdtUserName);
        pwdEdt = findViewById(R.id.idEdtPwd);
        Loginbtn = findViewById(R.id.idBtnLogin);
        loadingPB = findViewById(R.id.idPBLoding);
        RegisterTV = findViewById(R.id.idTVRegister);
        mAuth = FirebaseAuth.getInstance();

        RegisterTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(i);


            }
        });

        Loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = userNameEdt.getText().toString();
                String pwd = pwdEdt.getText().toString();
                if(TextUtils.isEmpty(userName) && TextUtils.isEmpty(pwd)) {
                    Toast.makeText(LoginActivity.this, "Please enter Details!", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                  mAuth.signInWithEmailAndPassword(userName,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {
                          if(task.isSuccessful()) {
                              loadingPB.setVisibility(View.GONE);
                              Toast.makeText(LoginActivity.this, "Login Successfull!!", Toast.LENGTH_SHORT).show();
                              Intent i = new Intent(LoginActivity.this, MainActivity.class);
                              startActivity(i);
                              finish();

                          }else{
                              loadingPB.setVisibility(View.GONE);
                              Toast.makeText(LoginActivity.this, "Failed to Login", Toast.LENGTH_SHORT).show();
                          }
                      }
                  });
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            Intent i = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
            this.finish();
        }
    }
}