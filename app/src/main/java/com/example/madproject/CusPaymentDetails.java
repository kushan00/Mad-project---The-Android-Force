package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CusPaymentDetails extends AppCompatActivity {

    private Button updateBTN;
    private String email;
    private EditText UnameEDT,FullNameEDT,AddressEDT,PhoneEDT,BankNameEDT,AccountnumEDT,CvvEDT;
    private String RegID;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar ().setTitle ("The Food Express") ;

        setContentView(R.layout.activity_cus_payment_details);

        UnameEDT = findViewById(R.id.idEDTUnameD);
        FullNameEDT = findViewById(R.id.idEDTfullnameD);
        AddressEDT = findViewById(R.id.idEDTaddrressD);
        PhoneEDT = findViewById(R.id.idEDTphoneD);
        AccountnumEDT = findViewById(R.id.idEDTaccNumberD);
        BankNameEDT = findViewById(R.id.idEDTbanknameD);
        CvvEDT = findViewById(R.id.idEDTcvvD);
        updateBTN = findViewById(R.id.idBTNupdatePD);
        firebaseDatabase = FirebaseDatabase.getInstance();

       FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
       String uid = user.getUid().toString();

       databaseReference = FirebaseDatabase.getInstance().getReference();
       databaseReference = firebaseDatabase.getReference("Payment Details").child(uid);

       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {

               PaymentDetialsRVModal paymentDetialsRVModal = new PaymentDetialsRVModal();
               String name = snapshot.child(uid).child("username").getValue(String.class);
               String Fname = snapshot.child(uid).child("fullname").getValue(String.class);
               String Addrress = snapshot.child(uid).child("address").getValue(String.class);
               String Phone = snapshot.child(uid).child("phone").getValue(String.class);
               String Acc = snapshot.child(uid).child("accNumber").getValue(String.class);
               String Bank = snapshot.child(uid).child("bankname").getValue(String.class);
               String Cvv = snapshot.child(uid).child("cvv").getValue(String.class);

               paymentDetialsRVModal.setUsername(name);
               paymentDetialsRVModal.setFullname(Fname);
               paymentDetialsRVModal.setAddress(Addrress);
               paymentDetialsRVModal.setPhone(Phone);
               paymentDetialsRVModal.setAccNumber(Acc);
               paymentDetialsRVModal.setBankname(Bank);
               paymentDetialsRVModal.setCvv(Cvv);



           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Toast.makeText(CusPaymentDetails.this, "User is = "+uid+"data not loaded", Toast.LENGTH_LONG).show();
           }
       });

       updateBTN.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Toast.makeText(CusPaymentDetails.this, "UID is = "+uid, Toast.LENGTH_LONG).show();
           }
       });

    }





}