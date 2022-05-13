package com.example.lionsclubdec;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClubDetailActivity extends AppCompatActivity {
    RecyclerView recview;
    //clubAdapter adapter;
    Spinner club;
    ArrayAdapter<String> clubList;
    AutoCompleteTextView CL;
    EditText TA,SA,SB,note;
    TextView clubname,clubcode,FUM,TFU,cp,cn,ca,cps,cpmob,cpmail,cs,css,csa,csmob,csmail,csc;
    Button submit,check;
    FirebaseAuth fAuth;
    DatabaseReference LDR,TR,tr1,tr2,tr3,tr4,tr5,tr6,tr7,tr8,tr9,tr10,tr111,tr12,tr13,tr14;
    AutoCompleteTextView code;
    String remain,pid;
    LinearLayout l1,l2;

    String userID;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_detail);
        pid=getIntent().getStringExtra("cid");
        clubname= findViewById(R.id.textView11);
        clubcode=findViewById(R.id.textView12);
        l1=findViewById(R.id.cpl);
        l2=findViewById(R.id.csl);
        FUM=findViewById(R.id.textView15);
        TFU=findViewById(R.id.textView17);
        cp=findViewById(R.id.textView19);
        cn=findViewById(R.id.textView20);
        ca=findViewById(R.id.textView21);
        cps=findViewById(R.id.textView22);
        cpmob=findViewById(R.id.textView23);
        cpmail=findViewById(R.id.textView26);
        cs=findViewById(R.id.textView24);
        css=findViewById(R.id.textView28);
        csa=findViewById(R.id.textView27);
        csmob=findViewById(R.id.textView29);
        csmail=findViewById(R.id.textView30);
        csc=findViewById(R.id.textView31);
        TR= FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Club_List").child(pid).child("Club");
        TR.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String data= snapshot.getValue().toString();
                    clubname.setText(data);
                    Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
                }
                else{
                    clubname.setText("error");
                }


            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
                clubname.setText("error");

            }
        });
        LDR= FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Club_List").child(pid).child("Code");
        LDR.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String data= snapshot.getValue().toString();
                    clubcode.setText(data);
                    Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
                }
                else{
                    clubcode.setText("error");
                }


            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
                clubcode.setText("error");

            }
        });

        tr1= FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("club_detail").child(pid).child("CP");
        tr1.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String data= snapshot.getValue().toString();
                    cp.setText(data);
                    Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
                    l1.setVisibility(View.VISIBLE);
                }
                else{
                    l1.setVisibility(View.GONE);
                    cp.setText("error");
                }


            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
                cp.setText("error");

            }
        });
        tr2= FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("club_detail").child(pid).child("CPCode");
        tr2.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String data= snapshot.getValue().toString();
                    cn.setText(data);
                    Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
                }
                else{
                    cn.setText("error");
                }


            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
                cn.setText("error");

            }
        });
        tr3= FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("club_detail").child(pid).child("CPAddress");
        tr3.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String data= snapshot.getValue().toString();
                    ca.setText(data);
                    Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
                }
                else{
                   ca.setText("error");
                }


            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
                ca.setText("error");

            }
        });
        tr4= FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("club_detail").child(pid).child("CPEmail");
        tr4.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String data= snapshot.getValue().toString();
                    cpmail.setText(data);
                    Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
                }
                else{
                    cpmail.setText("error");
                }


            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
                cpmail.setText("error");

            }
        });
        tr5= FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("club_detail").child(pid).child("CPMobile");
        tr5.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String data= snapshot.getValue().toString();
                    cpmob.setText(data);
                    Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
                }
                else{
                    cpmob.setText("error");
                }


            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
                cpmob.setText("error");

            }
        });
        tr6= FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("club_detail").child(pid).child("CPStatus");
        tr6.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String data= snapshot.getValue().toString();
                    cps.setText(data);
                    Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
                }
                else{
                    cps.setText("error");
                }


            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
               cps.setText("error");

            }
        });
        tr7= FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("club_detail").child(pid).child("CS");
        tr7.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    l1.setVisibility(View.VISIBLE);
                    String data= snapshot.getValue().toString();
                    cs.setText(data);
                    Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();

                }
                else{
                    l1.setVisibility(View.GONE);
                    cs.setText("error");
                }


            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
                cs.setText("error");

            }
        });
        tr8= FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("club_detail").child(pid).child("CSAddress");
        tr8.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String data= snapshot.getValue().toString();
                    csa.setText(data);
                    Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
                }
                else{
                    csa.setText("error");
                }


            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
                csa.setText("error");

            }
        });

        tr9= FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("club_detail").child(pid).child("CSCode");
        tr9.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String data= snapshot.getValue().toString();
                    csc.setText(data);
                    Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
                }
                else{
                    csc.setText("error");
                }


            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
                csc.setText("error");

            }
        });
        tr10= FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("club_detail").child(pid).child("CSEmail");
        tr10.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String data= snapshot.getValue().toString();
                    csmail.setText(data);
                    Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
                }
                else{
                    csmail.setText("error");
                }


            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
                csmail.setText("error");

            }
        });
        tr111= FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("club_detail").child(pid).child("CSMobile");
        tr111.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String data= snapshot.getValue().toString();
                    csmob.setText(data);
                    Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
                }
                else{
                    csmob.setText("error");
                }


            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
                csmob.setText("error");

            }
        });
        tr12= FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("club_detail").child(pid).child("CSStatus");
        tr12.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String data= snapshot.getValue().toString();
                    css.setText(data);
                    Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
                }
                else{
                    css.setText("error");
                }


            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
                css.setText("error");

            }
        });

        tr13= FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Club_List").child(pid).child("Family Unit Members");
        tr13.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String data= snapshot.getValue().toString();
                    FUM.setText(data);
                    Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
                }
                else{
                    FUM.setText("error");
                }


            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
                FUM.setText("error");

            }
        });
        tr14= FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Club_List").child(pid).child("Total Family Units");
        tr14.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String data= snapshot.getValue().toString();
                    TFU.setText(data);
                    Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
                }
                else{
                    TFU.setText("error");
                }


            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
                TFU.setText("error");

            }
        });
    }
}