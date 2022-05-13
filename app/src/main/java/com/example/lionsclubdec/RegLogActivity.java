package com.example.lionsclubdec;


import static android.content.ContentValues.TAG;
import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Executor;


public class RegLogActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CODE = 0555;
    TextView next,forget;
    ImageButton con;

    EditText nameEdit,mobileEdit,mailEdit,passEdit,rePassEdit;
    String name,mobile,mail,pass,rePass;

    String userID;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
  int a,b,c,d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_log);

        con =findViewById(R.id.imageButton2);
        con.setOnClickListener(this);
        mailEdit=findViewById(R.id.editTextTextPersonName);
        passEdit=findViewById(R.id.editTextTextPassword);
        firebaseAuth = FirebaseAuth.getInstance();

        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate(BIOMETRIC_STRONG | DEVICE_CREDENTIAL)) {
            case BiometricManager.BIOMETRIC_SUCCESS:

                Toast.makeText(RegLogActivity.this, "Finger Print Match",Toast.LENGTH_LONG).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(RegLogActivity.this, "Finger Sensor not Available in this Device",Toast.LENGTH_LONG).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(RegLogActivity.this, "Finger Sensor is missing or busy",Toast.LENGTH_LONG).show();
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
        biometricPrompt = new BiometricPrompt(RegLogActivity.this,
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
                Intent intent =new Intent(RegLogActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
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
                .setTitle("Biometric login for Lion's APP")
                .setSubtitle("Put Your Thumb")
                .setNegativeButtonText("Use account password")
                .build();

        // Prompt appears when user clicks "Log in".
        // Consider integrating with the keystore to unlock cryptographic operations,
        // if needed by your app.

        con.setOnClickListener(view -> {
            pass= passEdit.getText().toString().trim();
            mail=mailEdit.getText().toString().trim();

            if((mailEdit.getText().toString().isEmpty())){
                Toast.makeText(RegLogActivity.this, "Please enter Mail ",Toast.LENGTH_LONG).show();
                return;
            }
            if((passEdit.getText().toString().length()<8)){
                Toast.makeText(RegLogActivity.this, "Password Must be greater than 8 character: ",Toast.LENGTH_LONG).show();
                return;
            }
            progressDialog = new ProgressDialog(RegLogActivity.this);
            progressDialog.setTitle("Authentication");
            progressDialog.setTitle("Checking User");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();


            firebaseAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        biometricPrompt.authenticate(promptInfo);
                       // Intent intent =new Intent(RegLogActivity.this,MainActivity.class);
                      //  startActivity(intent);
                      //  finish();

                    }
                    else{
                        // If sign in fails, display a message to the user.
                        progressDialog.dismiss();
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(RegLogActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();


                    }


                }
            });

        });

    }


    public void onClick(View v) {



    }
}