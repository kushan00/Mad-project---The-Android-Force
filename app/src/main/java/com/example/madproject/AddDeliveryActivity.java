package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import java.util.Locale;

public class AddDeliveryActivity extends AppCompatActivity {
    private TextInputEditText customerNameEdt,buildingNumberEdt,streetNameEdt,cityEdt,contactNumberEdt;
    private Button addTimeBtn,addDeliveryBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String customerID;

    int hour,minute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar ().setTitle ("The Food Express") ;
        setContentView(R.layout.activity_add_delivery);
        customerNameEdt=findViewById(R.id.idEdtCustomerName);
        buildingNumberEdt=findViewById(R.id.idEdtBuildingNumber);
        streetNameEdt=findViewById(R.id.idEdtStreet);
        cityEdt=findViewById(R.id.idEdtCity);
        contactNumberEdt=findViewById(R.id.idPhoneNumber);
        loadingPB=findViewById(R.id.idpLoading);
        addTimeBtn=findViewById(R.id.time);
        addDeliveryBtn=findViewById(R.id.idBtnAddDelivery);
        firebaseDatabase=FirebaseDatabase.getInstance();
        FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid().toString();
        databaseReference=firebaseDatabase.getReference("Delivery");

        addDeliveryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
            String customerName = customerNameEdt.getText().toString();
            String buildingNumber = buildingNumberEdt.getText().toString();
            String streetName = streetNameEdt.getText().toString();
            String city = cityEdt.getText().toString();
            String contactNUmber = contactNumberEdt.getText().toString();
            String time = addTimeBtn.getText().toString();

            customerID=uid;

                if (TextUtils.isEmpty(customerName) && TextUtils.isEmpty(buildingNumber) && TextUtils.isEmpty(streetName) && TextUtils.isEmpty(city) && TextUtils.isEmpty(contactNUmber)) {
                    Toast.makeText(AddDeliveryActivity.this, "Please enter all Details!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    DeliveryRVModal deliveryRVModal = new DeliveryRVModal(customerName, buildingNumber, streetName, city, contactNUmber, time, customerID);

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            loadingPB.setVisibility(View.GONE);
                            databaseReference.child(customerID).setValue(deliveryRVModal);
                            startActivity(new Intent(AddDeliveryActivity.this, SuccessPage.class));
                            Toast.makeText(AddDeliveryActivity.this, "Delivery Information is Added", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                            Toast.makeText(AddDeliveryActivity.this, "Error is " + error.toString(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });
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
