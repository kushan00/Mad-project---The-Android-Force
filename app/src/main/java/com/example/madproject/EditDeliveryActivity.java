package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TimePicker;
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
import java.util.Locale;
import java.util.Map;

public class EditDeliveryActivity extends AppCompatActivity {
    private TextInputEditText customerNameEdt,buildingNumberEdt,streetNameEdt,cityEdt,contactNumberEdt;
    private Button addTimeBtn,updateDeliveryBtn,deleteDeliveryBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String customerID;
    private DeliveryRVModal deliveryRVModal;

    int hour,minute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar ().setTitle ("The Food Express") ;
        setContentView(R.layout.activity_edit_delivery);
        firebaseDatabase=FirebaseDatabase.getInstance();
        FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid().toString();

        customerNameEdt=findViewById(R.id.idEdtCustomerName);
        buildingNumberEdt=findViewById(R.id.idEdtBuildingNumber);
        streetNameEdt=findViewById(R.id.idEdtStreet);
        cityEdt=findViewById(R.id.idEdtCity);
        contactNumberEdt=findViewById(R.id.idPhoneNumber);
        loadingPB=findViewById(R.id.idpLoading);
        addTimeBtn=findViewById(R.id.time);
        updateDeliveryBtn=findViewById(R.id.idBtnUpdateDelivery);
        deleteDeliveryBtn=findViewById(R.id.idBtnDeleteDelivery);

        deliveryRVModal=getIntent().getParcelableExtra("Delivery");
        if(deliveryRVModal!=null){

            customerNameEdt.setText(deliveryRVModal.getCustomerName());
            buildingNumberEdt.setText(deliveryRVModal.getBuildingNumber());
            streetNameEdt.setText(deliveryRVModal.getStreetName());
            cityEdt.setText(deliveryRVModal.getCity());
            contactNumberEdt.setText(deliveryRVModal.getContactNUmber());
            addTimeBtn.setText(deliveryRVModal.getTime());
            customerID=uid;

        }


        databaseReference=firebaseDatabase.getReference("Delivery").child(customerID);

        updateDeliveryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                String customerName = customerNameEdt.getText().toString();
                String buildingNumber = buildingNumberEdt.getText().toString();
                String streetName = streetNameEdt.getText().toString();
                String city = cityEdt.getText().toString();
                String contactNUmber = contactNumberEdt.getText().toString();
                String time = addTimeBtn.getText().toString();

                Map<String,Object> map = new HashMap<>();
                map.put("customerName",customerName);
                map.put("buildingNumber",buildingNumber);
                map.put("streetName",streetName);
                map.put("city",city);
                map.put("contactNUmber",contactNUmber);
                map.put("time",time);
                map.put("customerID",customerID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
                         loadingPB.setVisibility(View.GONE);
                         databaseReference.updateChildren(map);
                        startActivity(new Intent(EditDeliveryActivity.this,Disaplay_delivey_details.class));
                        Toast.makeText(EditDeliveryActivity.this, "Delivery Info Update", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull  DatabaseError error) {
                        Toast.makeText(EditDeliveryActivity.this, "Fail To Update", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        deleteDeliveryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    deleteDelivery();
            }
        });
    }

    private void deleteDelivery(){
        databaseReference.removeValue();
        Toast.makeText(this, " Delivery Info id Deleted", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditDeliveryActivity.this,Disaplay_delivey_details.class));
    }

    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour=selectedHour;
                minute=selectedMinute;
                addTimeBtn.setText(String.format(Locale.getDefault(),"%02d:%02d",hour,minute));
            }
        };
        TimePickerDialog timePickerDialog=new TimePickerDialog(this,onTimeSetListener,hour,minute,true);
        timePickerDialog.setTitle("Select Delivery Time");
        timePickerDialog.show();

    }
}