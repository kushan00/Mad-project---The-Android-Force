package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.BatchUpdateException;
import java.util.ArrayList;

public class Disaplay_delivey_details extends AppCompatActivity implements DeliveryRVAdapter.DeliveryClickInterface{

    private RecyclerView deliveryRV;
    private ProgressBar loadingPB;
    private FloatingActionButton addFAB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<DeliveryRVModal> deliveryRVModalArrayList;
    private RelativeLayout bottomSheetRL;
    private DeliveryRVAdapter deliveryRVAdapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar ().setTitle ("The Food Express") ;

        setContentView(R.layout.activity_disaplay_delivey_details);
        deliveryRV = findViewById(R.id.idRVDelivery);
        loadingPB = findViewById(R.id.idpLoading);
        addFAB = findViewById(R.id.idAddFAB);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Delivery");
        deliveryRVModalArrayList = new ArrayList<>();
        bottomSheetRL = findViewById(R.id.idRLBSheet);
        mAuth = FirebaseAuth.getInstance();
        deliveryRVAdapter = new DeliveryRVAdapter(deliveryRVModalArrayList, this, this);
        deliveryRV.setLayoutManager(new LinearLayoutManager(this));
        deliveryRV.setAdapter(deliveryRVAdapter);
        addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Disaplay_delivey_details.this, AddDeliveryActivity.class));
            }
        });
        getAllDelivery();
    }
    private void getAllDelivery () {
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull  DataSnapshot snapshot, @Nullable String previousChildName) {

                loadingPB.setVisibility(View.GONE);
                deliveryRVModalArrayList.add(snapshot.getValue(DeliveryRVModal.class));
                deliveryRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable  String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                deliveryRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull  DataSnapshot snapshot) {
                loadingPB.setVisibility(View.GONE);
                deliveryRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull  DataSnapshot snapshot, @Nullable  String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                deliveryRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }


    @Override
    public void onDeliveryClick(int position) {

        displayBottomSheet(deliveryRVModalArrayList.get(position));
    }
    private void displayBottomSheet(DeliveryRVModal deliveryRVModal){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialog_disa,bottomSheetRL);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView customerNameTV = layout.findViewById(R.id.idTVCustomer);
        TextView buildingNumberTV = layout.findViewById(R.id.idTVBuilding);
        TextView streetNameTV = layout.findViewById(R.id.idTVStreet);
        TextView cityTV = layout.findViewById(R.id.idTVCity);
        TextView contactNumber = layout.findViewById(R.id.idTVContactNUmber);
        Button editBtn = layout.findViewById(R.id.idBtnEdit);


        customerNameTV.setText(deliveryRVModal.getCustomerName());
        buildingNumberTV.setText(deliveryRVModal.getBuildingNumber());
        streetNameTV.setText(deliveryRVModal.getStreetName());
        cityTV.setText(deliveryRVModal.getCity());
        contactNumber.setText(deliveryRVModal.getContactNUmber());

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(Disaplay_delivey_details.this,EditDeliveryActivity.class);
                i.putExtra("Delivery",deliveryRVModal);
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
            case R.id.idLogout:
                Toast.makeText(this, "User Logged out", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent i = new Intent(Disaplay_delivey_details.this,LoginActivity.class);
                startActivity(i);
                this.finish();
                return  true;
            case R.id.idPaymentD:
                Intent pd = new Intent(Disaplay_delivey_details.this,CusPaymentDetails.class);
                startActivity(pd);
                this.finish();
                return  true;
            case R.id.idMenuD:
                Intent d = new Intent(Disaplay_delivey_details.this,CustomerFood.class);
                startActivity(d);
                this.finish();
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}