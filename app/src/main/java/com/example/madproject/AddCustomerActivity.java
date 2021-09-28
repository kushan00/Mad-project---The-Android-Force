package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddCustomerActivity extends AppCompatActivity {

    private TextInputEditText fullNameEdt,customerNameEdt, customerAddressEdt, customerPhoneEdt, customerEmailEdt, customerNicEdt;
    private Button addCustomerBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String customerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_customer);
        fullNameEdt = findViewById(R.id.idEdtFullName);
        customerNameEdt = findViewById(R.id.idEdtCustomerName);
        customerAddressEdt = findViewById(R.id.idEdtAddress);
        customerPhoneEdt = findViewById(R.id.idEdtPhone);
        customerEmailEdt = findViewById(R.id.idEdtEmail);
        customerNicEdt = findViewById(R.id.idEdtNic);
        addCustomerBtn = findViewById(R.id.idBtnAddCustomer);
        loadingPB = findViewById(R.id.idPBaddloading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Customer Details");
        FirebaseUser userid = FirebaseAuth.getInstance().getCurrentUser();
        String uid = userid.getUid().toString();

        addCustomerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               loadingPB.setVisibility(View.VISIBLE);
                String fullName = fullNameEdt.getText().toString();
                String customerName = customerNameEdt.getText().toString();
                String customerAddress = customerAddressEdt.getText().toString();
                String customerPhone = customerPhoneEdt.getText().toString();
                String customerEmail = customerEmailEdt.getText().toString();
                String customerNic = customerNicEdt.getText().toString();
                customerID = uid;

                CustomerRVModel customerRVModel = new CustomerRVModel(fullName,customerName,customerAddress,
                        customerPhone,customerEmail,customerNic,customerID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        databaseReference.child(customerID).setValue(customerRVModel);
                        Toast.makeText(AddCustomerActivity.this, "Customer Added..", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddCustomerActivity.this,CustomerDetails.class));
                        
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddCustomerActivity.this, "Error is" + error.toString(), Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });

    }
}