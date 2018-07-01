package com.joseph.sevendaysofcode;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    EditText etEmail, etPassword, etName;
    String stEmail, stPassword, stName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControls();
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }

    public void initControls(){
        etEmail = (EditText)findViewById(R.id.et_email);
        etPassword = (EditText)findViewById(R.id.et_login_password);
        etName = (EditText)findViewById(R.id.et_name);
    }

    public void register(View v){
        stEmail = etEmail.getText().toString();
        stPassword = etPassword.getText().toString();
        stName = etName.getText().toString();
        Toast.makeText(this, stEmail + " " + stPassword, Toast.LENGTH_SHORT).show();
        firebaseAuth.createUserWithEmailAndPassword(stEmail, stPassword)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                            UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                                .setDisplayName(stName)
                                .build();
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            firebaseUser.updateProfile(userProfileChangeRequest)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Log.d("update", "Profile updated successfully");
                                            }else{
                                                Log.d("Update error", "Profile not updated successfully");
                                            }
                                        }
                                    });
                        }else
                            Log.d("error", task.toString());

                    }
                });
    }
    public void login(View v){
        stEmail = etEmail.getText().toString();
        stPassword = etPassword.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(stEmail, stPassword)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            String username = firebaseUser.getDisplayName();
                            Log.d("success", username + " " + firebaseUser.getEmail());
                        }else{
                            Log.d("Error", task.getException().toString());
                            Toast.makeText(MainActivity.this, "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}
