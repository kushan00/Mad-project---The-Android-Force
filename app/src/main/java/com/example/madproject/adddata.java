package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class adddata extends AppCompatActivity
{

    EditText name ,description ,price ,furl;
    Button submit , back;
    private ProgressBar loadingPB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddata);


        name=(EditText)findViewById(R.id.add_name);
        description=(EditText)findViewById(R.id.add_description);
        price=(EditText)findViewById(R.id.add_price);
        furl=(EditText)findViewById(R.id.add_furl);
        back=(Button)findViewById(R.id.add_back);
        loadingPB = findViewById(R.id.idPBLoading1);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));

                finish();

            }
        });


    submit=(Button)findViewById(R.id.add_submit);

    submit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            processinsert();


        }
    });



//usw object map
    }

    private void processinsert()
    {
        String n=name.getText().toString();
        String m = description.getText().toString();
       String p = price.getText().toString();
        String f = furl.getText().toString();

        Map<String,Object> map = new HashMap<>();
        map.put("name",name.getText().toString());
        map.put("description",description.getText().toString());
        map.put("price",price.getText().toString());
        map.put("furl",furl.getText().toString());

        if (TextUtils.isEmpty(n) && TextUtils.isEmpty(m) && TextUtils.isEmpty(p) && TextUtils.isEmpty(f) ) {
            Toast.makeText(adddata.this, "Please enter all Details!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            FirebaseDatabase.getInstance().getReference().child("foods").push()
                    .setValue(map)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            name.setText("");
                            description.setText("");
                            price.setText("");
                            furl.setText("");

                            Toast.makeText(getApplicationContext(), "Inserted Successfully", Toast.LENGTH_LONG).show();


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(getApplicationContext(), "Could not insert", Toast.LENGTH_LONG).show();

                        }

                    });

        }
    }

}
