package com.example.projectex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
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

public class RegisterActivity extends AppCompatActivity {

    EditText editTextTextEmailAddress, editTextTextPassword;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private EditText etEm, etPwd;
    private Button btnRegStu, btnRegAdmin, btnSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        etEm = findViewById(R.id.editTextTextEmailAddress);
        etPwd = findViewById(R.id.editTextTextPassword);
        btnRegAdmin = findViewById(R.id.btn_admin_register);
        btnRegStu = findViewById(R.id.btn_student_register);
        btnSignIn = findViewById(R.id.btn_signIn);
        btnSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnRegAdmin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                String email = etEm.getText().toString();
                String pwd = etPwd.getText().toString();
                firebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            User user = new User();
                            user.setIdToken(firebaseUser.getUid());
                            user.setEmail(firebaseUser.getEmail());
                            user.setStatus("Admin");
                            user.setPwd(pwd);

                            databaseReference.child("UserAccount").child(firebaseUser.getUid()).setValue(user);
                            etEm.setText(null);
                            etPwd.setText(null);
                            Toast.makeText(RegisterActivity.this, "Sign Up Successful As Admin", Toast.LENGTH_SHORT).show();
                        }else{
                            etEm.setText(null);
                            etPwd.setText(null);
                            Toast.makeText(RegisterActivity.this, "Incorrect/Existing Email. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });

        btnRegStu.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                String email = etEm.getText().toString();
                String pwd = etPwd.getText().toString();
                firebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            User user = new User();
                            user.setIdToken(firebaseUser.getUid());
                            user.setEmail(firebaseUser.getEmail());
                            user.setStatus("Student");
                            user.setPwd(pwd);

                            databaseReference.child("UserAccount").child(firebaseUser.getUid()).setValue(user);
                            etEm.setText(null);
                            etPwd.setText(null);
                            Toast.makeText(RegisterActivity.this, "Sign Up Successful As Student", Toast.LENGTH_SHORT).show();
                        }else{
                            etEm.setText(null);
                            etPwd.setText(null);
                            Toast.makeText(RegisterActivity.this, "Incorrect/Existing Email. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}