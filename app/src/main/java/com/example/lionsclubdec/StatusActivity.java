package com.example.lionsclubdec;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StatusActivity extends AppCompatActivity {
    DatabaseReference LDR,LMR;
    TextView t1,t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        t1=findViewById(R.id.textView45);
        t2=findViewById(R.id.textView47);
        LDR= FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Total_report").child("Total").child("Total Deposit");
        LDR.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String data= snapshot.getValue().toString();
                    t1.setText(data);

                }
                else{
                    t1.setText("no data");
                }


            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
                t1.setText("no data");

            }
        });
        LMR= FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Total_report").child("Total").child("Total Due");
        LMR.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String data= snapshot.getValue().toString();
                    t2.setText(data);

                }
                else{
                    t2.setText("no data");
                }


            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
                t2.setText("no data");

            }
        });
    }
}