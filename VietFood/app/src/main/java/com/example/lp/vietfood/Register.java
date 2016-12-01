package com.example.lp.vietfood;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity  implements View.OnClickListener{
    private EditText editEmail;
    private EditText editPass;
    private Button btnReg;
    FirebaseAuth firebaseAuth;

    public ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        editEmail = (EditText) findViewById(R.id.edit_email_reg);
        editPass = (EditText) findViewById(R.id.edit_password_reg);
        btnReg = (Button) findViewById(R.id.btn_register);
        btnReg.setOnClickListener(this);
    }


    public void registerNewAccount(String email, String pass) {
        progressDialog.setMessage("Registering user...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Register", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Toast.makeText(Register.this, "Register fail",
                                    Toast.LENGTH_SHORT).show();
                            progressDialog.hide();
                        }
                        else{
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            MainActivity.user.name = user.getEmail();
                            MainActivity.user.id = user.getUid();
                            MainActivity.user.login = true;
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
                            ref.child(user.getUid()).setValue(MainActivity.user);
                            startMainActivity();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v == btnReg) {
            if (!editEmail.getText().toString().equals("") && !editPass.getText().toString().equals("")) {
                registerNewAccount(editEmail.getText().toString(), editPass.getText().toString());
            }
            else{
                Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void startMainActivity() {
        Intent i = new Intent(Register.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
