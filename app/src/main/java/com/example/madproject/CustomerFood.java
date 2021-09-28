package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerFood extends AppCompatActivity {

    RecyclerView reView;
    cusadapter cusadapter;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_food);

        mAuth = FirebaseAuth.getInstance();

        reView=(RecyclerView)findViewById(R.id.reView);
        reView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<cusmodel> options =
                new FirebaseRecyclerOptions.Builder<cusmodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("foods"), cusmodel.class)
                        .build();


        cusadapter = new cusadapter(options);

        reView.setAdapter(cusadapter);




    }

    @Override
    protected void onStart() {
        super.onStart();
        cusadapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cusadapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.idLogout:
                Toast.makeText(this, "User Logged out", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent i = new Intent(CustomerFood.this,LoginActivity.class);
                startActivity(i);
                this.finish();
                return  true;
            case R.id.idPaymentD:
                Intent pd = new Intent(CustomerFood.this,PaymentDetails.class);
                startActivity(pd);
                this.finish();
                return  true;
            case R.id.idDeliveryD:
                Intent d = new Intent(CustomerFood.this,Disaplay_delivey_details.class);
                startActivity(d);
                this.finish();
                return  true;
            case R.id.idCustomerD:
                Intent dp = new Intent(CustomerFood.this,CustomerDetails.class);
                startActivity(dp);
                this.finish();
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}