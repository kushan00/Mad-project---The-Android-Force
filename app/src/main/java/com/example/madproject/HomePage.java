package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;
            
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePage extends AppCompatActivity {

    private Button clientbtn , adminbtn,btn;
      
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        getSupportActionBar ().setTitle ("The Food Express") ;

        clientbtn = findViewById(R.id.idBTNclient);
        adminbtn = findViewById(R.id.idBTNadmin);

        clientbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        adminbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this,AdminLoginActivity.class);
                startActivity(i);
                finish();
            }
        });

   
    }
}
