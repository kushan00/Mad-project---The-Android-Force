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

import java.util.HashMap;
import java.util.Map;

public class EditAccountActivity extends AppCompatActivity {

    private TextInputEditText fullNameEdt, customerNameEdt, customerAddressEdt, customerPhoneEdt, customerEmailEdt, customerNicEdt;
    private Button updateCustomerBtn, deleteCustomerBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String customerID;
    private CustomerRVModel customerRVModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_account);
        firebaseDatabase = FirebaseDatabase.getInstance();
        fullNameEdt = findViewById(R.id.idEdtFullName);
        customerNameEdt = findViewById(R.id.idEdtCustomerName);
        customerAddressEdt = findViewById(R.id.idEdtAddress);
        customerPhoneEdt = findViewById(R.id.idEdtPhone);
        customerEmailEdt = findViewById(R.id.idEdtEmail);
        customerNicEdt = findViewById(R.id.idEdtNic);
        updateCustomerBtn = findViewById(R.id.idBtnUpdateCustomer);
        deleteCustomerBtn = findViewById(R.id.idBtnDeleteCustomer);
        loadingPB = findViewById(R.id.idPBLoding);
        FirebaseUser userid = FirebaseAuth.getInstance().getCurrentUser();
        String uid = userid.getUid().toString();


        customerRVModel = getIntent().getParcelableExtra("Customer Details");
        if (customerRVModel != null) {
            fullNameEdt.setText(customerRVModel.getFullName());
            customerNameEdt.setText(customerRVModel.getCustomerName());
            customerAddressEdt.setText(customerRVModel.getCustomerAddress());
            customerPhoneEdt.setText(customerRVModel.getCustomerPhone());
            customerEmailEdt.setText(customerRVModel.getCustomerEmail());
            customerNicEdt.setText(customerRVModel.getCustomerNic());
            customerID = uid;
        }

        databaseReference = firebaseDatabase.getReference("Customer Details").child(uid);
        updateCustomerBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                //loadingPB.setVisibility(View.VISIBLE);
                String fullName = fullNameEdt.getText().toString();
                String customerName = customerNameEdt.getText().toString();
                String customerAddress = customerAddressEdt.getText().toString();
                String customerPhone = customerPhoneEdt.getText().toString();
                String customerEmail = customerEmailEdt.getText().toString();
                String customerNic = customerNicEdt.getText().toString();


                Map<String, Object> map = new HashMap<>();
                map.put("fullName", fullName);
                map.put("customerName", customerName);
                map.put("customerAddress", customerAddress);
                map.put("customerPhone", customerPhone);
                map.put("customerEmail", customerEmail);
                map.put("customerNic", customerNic);
                map.put("customerID", uid);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //loadingPB.setVisibility(View.GONE);
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditAccountActivity.this, "Customer Details Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditAccountActivity.this, CustomerDetails.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditAccountActivity.this, "Failed to update customer details!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        deleteCustomerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCustomer();
            }
        });
    }
    private void deleteCustomer(){
        databaseReference.removeValue();
        Toast.makeText(this, "Customer Details Deleted!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditAccountActivity.this, CustomerDetails.class));
}
}