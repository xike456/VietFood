package com.example.lp.vietfood;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener{
    private EditText editEmail;
    private EditText editPass;
    private Button btnLogin;
    private TextView txtSkip;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = (EditText) findViewById(R.id.edit_email);
        editPass = (EditText) findViewById(R.id.edit_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        txtSkip = (TextView) findViewById(R.id.txt_skip);
        firebaseAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(this);
        txtSkip.setOnClickListener(this);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    MainActivity.user.name = user.getEmail().toString();
                    // User is signed in
                    startMainActivity();
                    Log.d("Login", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("Login", "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        if(v == btnLogin){
            if(editEmail.getText().toString()!= "" && editPass.getText().toString() !=""){
                loginFirebase(editEmail.getText().toString(), editPass.getText().toString());
            }
        }

        if(v == txtSkip){
            startMainActivity();
        }
    }

    public void loginFirebase(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Login", "signInWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Toast.makeText(Login.this, "Login fail",
                                    Toast.LENGTH_SHORT).show();
                        }

                        if(task.isSuccessful()){
                            startMainActivity();
                        }
                    }
                });
    }

    public void startMainActivity() {
        Intent i = new Intent(Login.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            firebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
