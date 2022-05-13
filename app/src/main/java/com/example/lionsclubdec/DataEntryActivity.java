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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Executor;

public class DataEntryActivity extends AppCompatActivity {
    private static final int REQUEST_CODE =1000 ;
    Spinner club;
    ArrayAdapter<String> clubList;
    AutoCompleteTextView CL;
    EditText TA,SA,SB,note;
    TextView due,remaining;
    Button submit,check;
    String[] arrOfStr;
    FirebaseAuth fAuth;
    DatabaseReference LDR;
    DatabaseReference LMR;
    DatabaseReference LYR;
    DatabaseReference TR,total1,totalY,totalM,putDue,clubadd,clubdue,CA,CD;

    AutoCompleteTextView code;
    String remain;
    String club_code;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference root = db.getReference().child("Users");
    String t_amount,s_amount;
    String userID,TotalAdd,TotalDue;;
    ProgressDialog progressDialog;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);
        CL = findViewById(R.id.editTextTextPersonName);
        CL.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

        String[] cl = getResources().getStringArray(R.array.LCCL);
        clubList = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,cl);
        CL.setThreshold(1);
        CL.setAdapter(clubList);
        TA=findViewById(R.id.editTextNumberDecimal2);
        SA=findViewById(R.id.editTextNumberDecimal3);

        note=findViewById(R.id.editTextTextPersonName3);

        due=findViewById(R.id.textView5);
        remaining=findViewById(R.id.textView9);
        clubadd= FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Total_report").child("Total").child("Total Deposit");
        clubadd.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    TotalAdd= snapshot.getValue().toString();


                }
                else{
                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                }


            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();

            }
        });
       clubdue= FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Total_report").child("Total").child("Total Due");
        clubdue.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    TotalDue= snapshot.getValue().toString();


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
        due.setText("no data");
        CL.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                due.setText("Please enter CLub");
                TA.setText("0");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                due.setText("Checking");
                TA.setText("0");
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

                TR=FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Club_Total_report").child(arrOfStr[1]).child("Total Due");
                TR.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            String data= snapshot.getValue().toString();
                            due.setText(data);
                            TA.setText(data);

                        }
                        else{
                            due.setText("no data");
                            TA.setText("0");
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

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String t_amount = TA.getText().toString();
                    String s_amount =  SA.getText().toString();
                String s_a;
                String t_a;
                if(t_amount.trim().equals("") ){
                    t_a =  "0";
                }
                else{
                    t_a=t_amount;
                }
                    if(s_amount.trim().equals("") ){
                        s_a =  "0";
                    }
                    else{
                        s_a=s_amount;
                    }
                double a = Double.parseDouble(t_a);
                double b = Double.parseDouble(s_a);
                double sum = a-b;
                remain=String.valueOf(sum);
                remaining.setText(remain);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        SA.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                t_amount = TA.getText().toString();
                s_amount =  SA.getText().toString();
                String s_a;
                String t_a;
                if(t_amount.trim().equals("") ){
                    t_a =  "0";
                }
                else{
                    t_a=t_amount;
                }
                if(s_amount.trim().equals("") ){
                    s_a =  "0";
                }
                else{
                    s_a=s_amount;
                }
                double a = Double.parseDouble(t_a);
                double b = Double.parseDouble(s_a);
                double sum = a-b;
                remain=String.valueOf(sum);
                remaining.setText(remain);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate(BIOMETRIC_STRONG | DEVICE_CREDENTIAL)) {
            case BiometricManager.BIOMETRIC_SUCCESS:

                Toast.makeText(DataEntryActivity.this, "Finger Print Match",Toast.LENGTH_LONG).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(DataEntryActivity.this, "Finger Sensor not Available in this Device",Toast.LENGTH_LONG).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(DataEntryActivity.this, "Finger Sensor is missing or busy",Toast.LENGTH_LONG).show();
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
        biometricPrompt = new BiometricPrompt(DataEntryActivity.this,
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
                progressDialog = new ProgressDialog(DataEntryActivity.this);
                progressDialog.setTitle("Writing Data");
                progressDialog.setTitle("Please Wait");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                LDR= FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Report Daily");
                LMR= FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Report Monthly");
                LYR= FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Report Yearly");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());
                SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                String currentDateandTime2 = sdf2.format(new Date());
                SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy", Locale.getDefault());
                String currentDateandTime3 = sdf3.format(new Date());
                SimpleDateFormat sdf4 = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                String currentDateandTime4 = sdf4.format(new Date());
                SimpleDateFormat sdf5 = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                String currentDateandTime5 = sdf5.format(new Date());
                SimpleDateFormat sdf6 = new SimpleDateFormat("MM", Locale.getDefault());
                String currentDateandTime6 = sdf6.format(new Date());
                t_amount = TA.getText().toString();
                s_amount =  SA.getText().toString();
                club_code = CL.getText().toString();
                arrOfStr = club_code.split("-", 2);



                // HashMap<String , String> userMap = new HashMap<>();

                //   userMap.put("ID" , currentDateandTime);
                //   userMap.put("Time" , currentDateandTime2);
                //    userMap.put("Total_D" , t_amount);
                writeDailyReport(currentDateandTime,currentDateandTime2,s_amount,currentDateandTime4);
                writeYMReport(currentDateandTime,currentDateandTime2,s_amount,currentDateandTime3,currentDateandTime5,currentDateandTime6);
                double a = Double.parseDouble(t_amount);
                double b = Double.parseDouble(s_amount);
                double sum = a-b;
                if(sum<0){
                    double sum1=(-1)*sum;
                    putDue=FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
                    putDue.child("Club_Total_report").child(arrOfStr[1]).child("Total Due").setValue(String.valueOf(sum1));
                }
                else if(sum>0){
                    double sum1=sum;
                    putDue=FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
                    putDue.child("Club_Total_report").child(arrOfStr[1]).child("Total Due").setValue(String.valueOf(sum1));
                }
                else{
                    double sum1=sum;
                    putDue=FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
                    putDue.child("Club_Total_report").child(arrOfStr[1]).child("Total Due").setValue(String.valueOf(sum1));
                }

                double ab = Double.parseDouble(TotalAdd);
                double ba = Double.parseDouble(TotalDue);

                double add= ab+b;

                if(sum<0){
                    double sum1=sum;
                    double due = ba+sum1;
                    putDue=FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
                    putDue.child("Club_Total_report").child(arrOfStr[1]).child("Total Due").setValue(String.valueOf(sum1));
                    CA=FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
                    CA.child("Total_report").child("Total").child("Total Deposit").setValue(String.valueOf(add));
                    CD=FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
                    CD.child("Total_report").child("Total").child("Total Due").setValue(String.valueOf(due));

                }
                else if(sum>0){
                    double sum1=sum;
                    double due = ba+sum1;
                    putDue=FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
                    putDue.child("Club_Total_report").child(arrOfStr[1]).child("Total Due").setValue(String.valueOf(sum1));
                    CA=FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
                    CA.child("Total_report").child("Total").child("Total Deposit").setValue(String.valueOf(add));
                    CD=FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
                    CD.child("Total_report").child("Total").child("Total Due").setValue(String.valueOf(due));

                }
                else{
                    double sum1=sum;
                    putDue=FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
                    putDue.child("Club_Total_report").child(arrOfStr[1]).child("Total Due").setValue(String.valueOf(sum1));
                    CA=FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
                    CA.child("Total_report").child("Total").child("Total Deposit").setValue(String.valueOf(add));

                }
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();
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



        submit=findViewById(R.id.button2);
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                biometricPrompt.authenticate(promptInfo);



            }
        });
    }



    public void writeDailyReport(String ID, String Time, String Total_D,String ID2) {

        userENtryDaily user = new userENtryDaily(ID, Time,Total_D);

        total1=FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        total1.child("Club_daily_report").child(arrOfStr[1]).child(ID2).child(ID).setValue(user);  progressDialog.dismiss();

    }
    public void writeYMReport(String ID, String Time, String Total_D,String year,String Date,String month) {
        userEntryYEARmonth user2 = new userEntryYEARmonth(Date,ID, Time,Total_D);

        totalY=FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        totalY.child("Club_Yearly_report").child(arrOfStr[1]).child(year).child(ID).setValue(user2);

        totalM=FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        totalM.child("Club_monthly_report").child(arrOfStr[1]).child(year).child(month).child(ID).setValue(user2);
        progressDialog.dismiss();
    }


}