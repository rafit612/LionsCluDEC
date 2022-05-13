package com.example.lionsclubdec;

import static android.content.ContentValues.TAG;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executor;

public class SpendingActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1000 ;
    AutoCompleteTextView CL;
    EditText TA;
    TextView due,totalDue1,totalDue;
    ArrayAdapter<String> clubList;
    Button submit;
    String dueRec,club_code,remain,remain2;

    String[] arrOfStr;
    FirebaseAuth fAuth;
    DatabaseReference LDR;
    DatabaseReference LMR,CMR,clubdue;
    String userID,TotalDue,TotalDue1;;
    ProgressDialog progressDialog;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending);
        CL = findViewById(R.id.clubfind);
        due = findViewById(R.id.textView49);
        TA = findViewById(R.id.editTextNumberDecimal4);
        totalDue=findViewById(R.id.textView52);
        submit= findViewById(R.id.button3);
       LMR = FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
       CMR=FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        clubdue= FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Total_report").child("Total").child("Total Due");
        clubdue.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    TotalDue1= snapshot.getValue().toString();


                }
                else{
                    Toast.makeText(getApplicationContext(), "Error",Toast.LENGTH_SHORT).show();
                }


            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());


            }
        });
        CL.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        String[] cl = getResources().getStringArray(R.array.LCCL);
        clubList = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,cl);
        CL.setThreshold(1);
        CL.setAdapter(clubList);

        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate(BIOMETRIC_STRONG | DEVICE_CREDENTIAL)) {
            case BiometricManager.BIOMETRIC_SUCCESS:

                Toast.makeText(SpendingActivity.this, "Finger Print Match",Toast.LENGTH_LONG).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(SpendingActivity.this, "Finger Sensor not Available in this Device",Toast.LENGTH_LONG).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(SpendingActivity.this, "Finger Sensor is missing or busy",Toast.LENGTH_LONG).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                // Prompts the user to create credentials that your app accepts.
                final Intent enrollIntent = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
                enrollIntent.putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BIOMETRIC_STRONG | DEVICE_CREDENTIAL);
                startActivityForResult(enrollIntent, REQUEST_CODE);
                break;
        }


        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(SpendingActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                        "Authentication error: " + errString, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                progressDialog = new ProgressDialog(SpendingActivity.this);
                progressDialog.setTitle("Writing Data");
                progressDialog.setTitle("Please Wait");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                double ab = Double.parseDouble(TotalDue1);
                double ba = Double.parseDouble(TA.getText().toString());
                double sum = ab+ba;
                remain2=String.valueOf(sum);
                LMR.child("Club_Total_report").child(arrOfStr[1]).child("Total Due").setValue(remain);
                CMR.child("Total_report").child("Total").child("Total Due").setValue(remain2);

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded and data write \uD83D\uDC4D\uD83C\uDFFB", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Promt For Confirmation")
                .setSubtitle("if you are not sure,go back")
                .setNegativeButtonText("Cancel")
                .build();


        CL.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                due.setText("0");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                due.setText("0");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                club_code = CL.getText().toString();

                if(club_code.contains("-")){
                    arrOfStr = club_code.split("-", 2);
                }
                else {

                    return;
                }

                //arrOfStr = club_code.split("-", 2);

                // Toast.makeText(getApplicationContext(),arrOfStr[1],Toast.LENGTH_LONG).show();

                LDR= FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Club_Total_report").child(arrOfStr[1]).child("Total Due");
                LDR.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            String data= snapshot.getValue().toString();
                            due.setText(data);
                            dueRec=data;


                        }
                        else{
                            due.setText("no Due Found");
                            dueRec="0";

                        }


                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w(TAG, "loadPost:onCancelled", error.toException());
                        due.setText("no data");

                    }
                });

            }
        });
        TA.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(arrOfStr[1].length()>0 ){
                    String t_amount = TA.getText().toString();
                    String rec_amount = dueRec;
                    String t_a;
                    if(t_amount.trim().equals("") ){
                        t_a =  "0";
                    }
                    else{
                        t_a=t_amount;
                    }
                    double a = Double.parseDouble(t_a);
                    double b = Double.parseDouble(rec_amount);
                    double sum = a+b;
                    remain=String.valueOf(sum);
                    totalDue.setText(remain);
                }

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String t_amount = TA.getText().toString();
                String rec_amount = dueRec;
                String t_a;
                if(t_amount.trim().equals("") ){
                    t_a =  "0";
                }
                else{
                    t_a=t_amount;
                }
                double a = Double.parseDouble(t_a);
                double b = Double.parseDouble(rec_amount);
                double sum = a+b;
                remain=String.valueOf(sum);
                totalDue.setText(remain);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(arrOfStr[1].length()>0 ){
                    String t_amount = TA.getText().toString();
                    String rec_amount = dueRec;
                    String t_a;
                    if(t_amount.trim().equals("") ){
                        t_a =  "0";
                    }
                    else{
                        t_a=t_amount;
                    }
                    double a = Double.parseDouble(t_a);
                    double b = Double.parseDouble(rec_amount);
                    double sum = a+b;
                    remain=String.valueOf(sum);
                    totalDue.setText(remain);
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biometricPrompt.authenticate(promptInfo);
            }
        });




    }
}