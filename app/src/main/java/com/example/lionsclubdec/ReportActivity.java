package com.example.lionsclubdec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ReportActivity extends AppCompatActivity {

    CardView Daily,Lion,Monthly,Yearly;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Daily=findViewById(R.id.daily);
        Lion=findViewById(R.id.lcreport);
        Monthly=findViewById(R.id.monthly);
        Yearly=findViewById(R.id.yearly);

        Daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReportActivity.this, DailyReportActivity.class);
                startActivity(intent);
            }
        });
        Lion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReportActivity.this, MonthReportActivity.class);
                startActivity(intent);
            }
        });

        Monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReportActivity.this, MonthReportActivity.class);
                startActivity(intent);
            }
        });

        Yearly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReportActivity.this, YearlyReportActivity.class);
                startActivity(intent);
            }
        });
    }
}