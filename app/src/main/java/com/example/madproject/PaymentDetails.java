package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PaymentDetails extends AppCompatActivity implements PaymentRVAdapter.PaymentCLickInterface {
//pay
    private RecyclerView paymentRV;
    private ProgressBar loadingPB;
    private FloatingActionButton addFAB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<PaymentDetialsRVModal> paymentDetialsRVModalArrayList;
    private RelativeLayout BottemsheetRL;
    private PaymentRVAdapter paymentRVAdapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar ().setTitle ("The Food Express") ;

        setContentView(R.layout.activity_payment_details);
        paymentRV = findViewById(R.id.idRVPayment);
        loadingPB = findViewById(R.id.idPBLoding);
        addFAB = findViewById(R.id.idAddFAB);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Payment Details");
        paymentDetialsRVModalArrayList = new ArrayList<>();
        BottemsheetRL = findViewById(R.id.idRLBottomsheet);
        mAuth = FirebaseAuth.getInstance();
        paymentRVAdapter = new PaymentRVAdapter(paymentDetialsRVModalArrayList,this,this);
        paymentRV.setLayoutManager(new LinearLayoutManager(this));
        paymentRV.setAdapter(paymentRVAdapter);
        addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaymentDetails.this,AddPaymentActivity.class));

            }
        });
        getAllPayments();
    }

    private void getAllPayments(){
        paymentDetialsRVModalArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //loadingPB.setVisibility(View.GONE);
                paymentDetialsRVModalArrayList.add(snapshot.getValue(PaymentDetialsRVModal.class));
                paymentRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //loadingPB.setVisibility(View.GONE);
                paymentRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                //loadingPB.setVisibility(View.GONE);
                paymentRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //loadingPB.setVisibility(View.GONE);
                paymentRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onPaymentClick(int position) {
        displayBottomsheet(paymentDetialsRVModalArrayList.get(position));
    }

    private void displayBottomsheet(PaymentDetialsRVModal paymentDetialsRVModal){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialog,BottemsheetRL);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView registerid = layout.findViewById(R.id.idTVregisterID);
        TextView fullname = layout.findViewById(R.id.idTVFullName);
        TextView accountnum = layout.findViewById(R.id.idTVaccountNum);
        Button editBTN = layout.findViewById(R.id.idBTNeditPdetails);


        registerid.setText(paymentDetialsRVModal.getFullname());
        fullname.setText(paymentDetialsRVModal.getPhone());
        accountnum.setText(paymentDetialsRVModal.getAccNumber());

        editBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PaymentDetails.this,EditPaymentActivity.class);
                i.putExtra("Payment Details",paymentDetialsRVModal);
                startActivity(i);
            }
        });


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
            case R.id.idAdminLogOut:
                Toast.makeText(this, "User Logged out", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent i = new Intent(PaymentDetails.this,LoginActivity.class);
                startActivity(i);
                this.finish();
                return  true;
            case R.id.idPaymentD:
                Intent pd = new Intent(PaymentDetails.this,PaymentDetails.class);
                startActivity(pd);
                this.finish();
                return  true;
            case R.id.idMenuD:
                Intent d = new Intent(PaymentDetails.this,CustomerFood.class);
                startActivity(d);
                this.finish();
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
