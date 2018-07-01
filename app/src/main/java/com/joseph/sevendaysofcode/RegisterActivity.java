package com.joseph.sevendaysofcode;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    EditText etEmail, etPassword, etName;
    String stEmail, stPassword, stName;
    TextView tvServerResponse;
    String stServerResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
        initControls();

    }

    public void register(View v){
        tvServerResponse.setText("Wait while registering ...");
        stEmail = etEmail.getText().toString();
        stPassword = etPassword.getText().toString();
        stName = etName.getText().toString();
        Toast.makeText(this, stEmail + " " + stPassword, Toast.LENGTH_SHORT).show();
        firebaseAuth.createUserWithEmailAndPassword(stEmail, stPassword)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(stName)
                                    .build();
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            firebaseUser.updateProfile(userProfileChangeRequest)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                tvServerResponse.setText("Registration done successfully ");
                                            }else{
                                                Log.d("Update error", "Profile not updated successfully");
                                            }
                                        }
                                    });
                        }else
                            tvServerResponse.setText("An error occured");

                    }
                });
    }

    public void initControls(){
        etEmail = (EditText)findViewById(R.id.et_register_email);
        etPassword = (EditText)findViewById(R.id.et_register_password);
        etName = (EditText)findViewById(R.id.et_register_name);
        tvServerResponse = (TextView)findViewById(R.id.tvServerResponse);

    }

}
