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

import java.util.HashMap;
import java.util.Map;

public class EditPaymentActivity extends AppCompatActivity {

    private TextInputEditText UnameEDT,FullNameEDT,AddressEDT,PhoneEDT,BankNameEDT,AccountnumEDT,CvvEDT;
    private Button UpdatePaymentBTN , DeletePaymentBTN;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String RegID;
    private PaymentDetialsRVModal paymentDetialsRVModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar ().setTitle ("The Food Express") ;

        setContentView(R.layout.activity_edit_payment);
        UnameEDT = findViewById(R.id.idEDTUname);
        FullNameEDT = findViewById(R.id.idEDTfullName);
        AddressEDT = findViewById(R.id.idEDTAddress);
        PhoneEDT = findViewById(R.id.idEDTPhone);
        BankNameEDT = findViewById(R.id.idEDTbankname);
        AccountnumEDT = findViewById(R.id.idEDTAccountNum);
        CvvEDT = findViewById(R.id.idEDTCvv);
        FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid().toString();
        UpdatePaymentBTN = findViewById(R.id.idBtnUpdatePayment);
        DeletePaymentBTN = findViewById(R.id.idBtnDeletePayment);
        loadingPB = findViewById(R.id.idPBloading);
        firebaseDatabase = FirebaseDatabase.getInstance();

        paymentDetialsRVModal = getIntent().getParcelableExtra("Payment Details");
        if(paymentDetialsRVModal!=null){
            UnameEDT.setText(paymentDetialsRVModal.getUsername());
            FullNameEDT.setText(paymentDetialsRVModal.getFullname());
            AddressEDT.setText(paymentDetialsRVModal.getAddress());
            PhoneEDT.setText(paymentDetialsRVModal.getPhone());
            BankNameEDT.setText(paymentDetialsRVModal.getBankname());
            AccountnumEDT.setText(paymentDetialsRVModal.getAccNumber());
            CvvEDT.setText(paymentDetialsRVModal.getCvv());
            RegID = uid;
        }

        databaseReference = firebaseDatabase.getReference("Payment Details").child(RegID);

        UpdatePaymentBTN.setOnClickListener(new View.OnClickListener() {
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

                Map<String,Object> map = new HashMap<>();
                map.put("username",Uname);
                map.put("fullname",Fname);
                map.put("address",Address);
                map.put("phone",Phone);
                map.put("bankname",Bank);
                map.put("accNumber",AccNumber);
                map.put("cvv",cvv);
                map.put("regID",RegID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.updateChildren(map);
                        loadingPB.setVisibility(View.GONE);
                        startActivity(new Intent(EditPaymentActivity.this,PaymentDetails.class));
                        Toast.makeText(EditPaymentActivity.this, "Payment Details Updated", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditPaymentActivity.this, "Failed to updated!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        DeletePaymentBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePayment();
            }
        });
    }
    private void deletePayment(){
        databaseReference.removeValue();
        Toast.makeText(this, "Payment Details Deleted!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditPaymentActivity.this,PaymentDetails.class));
        finish();
    }
}
