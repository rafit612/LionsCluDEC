package com.example.lionsclubdec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    CardView status, Calculate, Report,
            ClubList, ClubMember,Aboutus,Logout,MemberAddon,ClubAddon,spending;
    FirebaseAuth fAuth;
    DatabaseReference LDR,LMR,LYR;

    String userID;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            //fAuth = FirebaseAuth.getInstance();
          //  userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();


        status=findViewById(R.id.status);
        Calculate=findViewById(R.id.cal);
        Report=findViewById(R.id.report);
        ClubAddon=findViewById(R.id.club_add);
        ClubList=findViewById(R.id.club_lis);
        ClubMember=findViewById(R.id.member_list);
        spending=findViewById(R.id.club_spending);
        Aboutus=findViewById(R.id.aboutus);
        Logout=findViewById(R.id.logout);
        MemberAddon=findViewById(R.id.member_add);

        spending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SpendingActivity.class);
                startActivity(intent);
            }
        });

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StatusActivity.class);
                startActivity(intent);

            }
        });
        Calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, DataEntryActivity.class);
                startActivity(intent);
            }
        });
        ClubList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ClubActivity.class);
                startActivity(intent);
            }
        });
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(MainActivity.this,RegLogActivity.class);
                    startActivity(intent);
                    finish();

                } catch (Exception exception) {
                    exception.printStackTrace();
                }finally {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(MainActivity.this,RegLogActivity.class);
                    startActivity(intent);
                    finish();

                }
            }

        });
        Report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ReportActivity.class);
                startActivity(intent);
            }
        });
    }
}