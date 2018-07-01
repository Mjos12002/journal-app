package com.joseph.sevendaysofcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    String stEmail, stPassword, stServerResponse, stUsername;
    EditText etEmail, etPassword;
    TextView tvServerResponse;
    Button btnLogin;
    Intent registerActivityIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        if(firebaseAuth.getCurrentUser() != null){

        }
    }

    public void openRegisterActivity(View v){
        registerActivityIntent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(registerActivityIntent);
    }

    public void login(View v){
        stEmail = etEmail.getText().toString();
        stPassword = etPassword.getText().toString();
        if(TextUtils.isEmpty(stEmail) || TextUtils.isEmpty(stPassword)){
            Toast.makeText(this, "Empty values not allowed", Toast.LENGTH_SHORT).show();
            return;
        }
        tvServerResponse.setText("Wait while signing in ...");
        firebaseAuth.signInWithEmailAndPassword(stEmail, stPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        tvServerResponse.setText("");
                        if(task.isSuccessful()){

                            firebaseUser = firebaseAuth.getCurrentUser();
                            stUsername = firebaseUser.getDisplayName();
                            Intent allDiariesActivityIntent = new Intent(getApplicationContext(), AddDiariesActivity.class);
                            allDiariesActivityIntent.putExtra("username", stUsername);
                            startActivity(allDiariesActivityIntent);
                        }else{
                            tvServerResponse.setText("Unknow user, try again");
                        }
                    }
                });
    }
    public void init(){
        etEmail = (EditText)findViewById(R.id.et_login_email);
        etPassword = (EditText)findViewById(R.id.et_login_password);
        tvServerResponse = (TextView)findViewById(R.id.tvServerResponse);
        firebaseAuth = FirebaseAuth.getInstance();
    }

}
