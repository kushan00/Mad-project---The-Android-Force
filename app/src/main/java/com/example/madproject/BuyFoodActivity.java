package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BuyFoodActivity extends AppCompatActivity {


        private TextInputEditText typeEDT,quantityEDT,additionalEDT;
        private Button buytBTN;
        private ProgressBar loadingPB;
        private FirebaseDatabase firebaseDatabase;
        private DatabaseReference databaseReference;
        private String RegID;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            getSupportActionBar ().setTitle ("The Food Express") ;

            setContentView(R.layout.activity_buy_food);
            typeEDT = findViewById(R.id.idEDTfoodtype);
            quantityEDT = findViewById(R.id.idEDTQuantity);
            additionalEDT = findViewById(R.id.idEDTaddthings);
            buytBTN = findViewById(R.id.idBtnbuyfoods);
            loadingPB = findViewById(R.id.idPBloading);
            firebaseDatabase = firebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("Buy Foods");

            FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid().toString();

            buytBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadingPB.setVisibility(View.VISIBLE);
                    String Uname = typeEDT.getText().toString();
                    String Fname = quantityEDT.getText().toString();
                    String Address = additionalEDT.getText().toString();
                    RegID = uid;

                    if (TextUtils.isEmpty(Uname) && TextUtils.isEmpty(Fname) && TextUtils.isEmpty(Address) ) {
                        Toast.makeText(BuyFoodActivity.this, "Please enter all Details!", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        BuySelectedFood BuySelectedFood = new BuySelectedFood(Uname, Fname, Address, RegID);

                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                loadingPB.setVisibility(View.GONE);
                                databaseReference.child(RegID).setValue(BuySelectedFood);
                                Toast.makeText(BuyFoodActivity.this, "Order Created Successfully!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(BuyFoodActivity.this, AddPaymentActivity.class));

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(BuyFoodActivity.this, "Error is " + error.toString(), Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                }
            });

        }
    }
