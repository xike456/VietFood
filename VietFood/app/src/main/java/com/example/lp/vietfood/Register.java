package com.example.lp.vietfood;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity  implements View.OnClickListener{
    private EditText editEmail;
    private EditText editPass;
    private Button btnReg;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        editEmail = (EditText) findViewById(R.id.edit_email_reg);
        editPass = (EditText) findViewById(R.id.edit_password_reg);
        btnReg = (Button) findViewById(R.id.btn_register);
        btnReg.setOnClickListener(this);
    }


    public void registerNewAccount(String email, String pass) {
        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Register", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Toast.makeText(Register.this, "Register fail",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            startMainActivity();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v == btnReg) {
            if (editEmail.getText().toString() != null && editPass.getText().toString() != null) {
                registerNewAccount(editEmail.getText().toString(), editPass.getText().toString());
            }
        }
    }

    public void startMainActivity() {
        Intent i = new Intent(Register.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
