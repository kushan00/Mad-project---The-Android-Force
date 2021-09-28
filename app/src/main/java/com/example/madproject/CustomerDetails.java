package com.example.madproject;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CustomerDetails extends AppCompatActivity implements CustomerRVAdapter.CustomerClickInterface {

    private RecyclerView customerRV;
    private ProgressBar loadingPB;
    private FloatingActionButton addFAB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<CustomerRVModel> customerRVModelArrayList;
    private RelativeLayout BsheetRL;
    private CustomerRVAdapter customerRVAdapter;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle("The Food Express");

        setContentView(R.layout.customer_details);
        customerRV = findViewById(R.id.idTVCustomerName);
        loadingPB = findViewById(R.id.idPBLoding);
        addFAB = findViewById(R.id.idAddFAB);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Customer Details");
        customerRVModelArrayList = new ArrayList<>();
        BsheetRL = findViewById(R.id.idRLBsheet);
        mAuth = FirebaseAuth.getInstance();
        customerRVAdapter = new CustomerRVAdapter(customerRVModelArrayList, this,this);
        customerRV.setLayoutManager(new LinearLayoutManager(this));
        customerRV.setAdapter(customerRVAdapter);
        addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerDetails.this, AddCustomerActivity.class));

            }
        });
        getAllDetails();
    }

    private void getAllDetails() {
        customerRVModelArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                customerRVModelArrayList.add(snapshot.getValue(CustomerRVModel.class));
                customerRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                customerRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//                loadingPB.setVisibility(View.GONE);
                customerRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                customerRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onCustomerClick(int position) {

        displayBottomsheet(customerRVModelArrayList.get(position));

    }

    private void displayBottomsheet(CustomerRVModel customerRVModel) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_cus_dialog, BsheetRL);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView customerId = layout.findViewById(R.id.idTVCustomerId);
        TextView customerName = layout.findViewById(R.id.idTVCustomerName);
        TextView NIC = layout.findViewById(R.id.idTVCusNic);
        Button editBTN = layout.findViewById(R.id.idBTNeditCdetails);


        customerId.setText(customerRVModel.getCustomerID());
        customerName.setText(customerRVModel.getCustomerName());
        NIC.setText(customerRVModel.getCustomerNic());

        editBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CustomerDetails.this, EditAccountActivity.class);
                i.putExtra("Customer Details", customerRVModel);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.idLogout:
                Toast.makeText(this, "User Logged out", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent i = new Intent(CustomerDetails.this, LoginActivity.class);
                startActivity(i);
                this.finish();
                return true;
            case R.id.idMenuD:
                mAuth.signOut();
                Intent ip = new Intent(CustomerDetails.this, CustomerFood.class);
                startActivity(ip);
                this.finish();
                return true;
            case R.id.idPaymentD:
                Intent pd = new Intent(CustomerDetails.this,CusPaymentDetails.class);
                startActivity(pd);
                this.finish();
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}

