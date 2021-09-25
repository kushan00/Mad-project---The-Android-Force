package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
     
import android.content.Intent;
import android.os.Bundle;
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

public class AddPaymentActivity extends AppCompatActivity {

    private TextInputEditText  UnameEDT,FullNameEDT,AddressEDT,PhoneEDT,BankNameEDT,AccountnumEDT,CvvEDT;
    private Button SavePaymentBTN;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String RegID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar ().setTitle ("The Food Express") ;

        setContentView(R.layout.activity_add_payment);
        UnameEDT = findViewById(R.id.idEDTUname);
        FullNameEDT = findViewById(R.id.idEDTfullName);
        AddressEDT = findViewById(R.id.idEDTAddress);
        PhoneEDT = findViewById(R.id.idEDTPhone);
        BankNameEDT = findViewById(R.id.idEDTbankname);
        AccountnumEDT = findViewById(R.id.idEDTAccountNum);
        CvvEDT = findViewById(R.id.idEDTCvv);
        SavePaymentBTN = findViewById(R.id.idBtnSavePayment);
        loadingPB = findViewById(R.id.idPBloading);
        firebaseDatabase = firebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Payment Details");

        SavePaymentBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                String Uname = UnameEDT.getText().toString();
                String Fname = FullNameEDT.getText().toString();
                String Address = AddressEDT.getText().toString();
                String Phone = PhoneEDT.getText().toString();
                String Bank = BankNameEDT.getText().toString();
                String AccNumber = AccountnumEDT.getText().toString();
                String cvv = CvvEDT.getText().toString();
                RegID = Uname;

                PaymentDetialsRVModal PaymentDetialsRVModal = new PaymentDetialsRVModal(Uname,Fname,Address,Phone,Bank,AccNumber,cvv,RegID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        databaseReference.child(RegID).setValue(PaymentDetialsRVModal);
                        Toast.makeText(AddPaymentActivity.this, "Details Saved Successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddPaymentActivity.this,PaymentDetails.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddPaymentActivity.this, "Error is "+error.toString(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

    }
}
