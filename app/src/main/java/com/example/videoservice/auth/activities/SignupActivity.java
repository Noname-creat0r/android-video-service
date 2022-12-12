package com.example.videoservice.auth.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.videoservice.MainActivity;
import com.example.videoservice.R;
import com.example.videoservice.auth.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_dialog);

        MainActivity.mAuth = FirebaseAuth.getInstance();
        Button signupBtn = this.findViewById(R.id.signup);
        EditText emailEdit = this.findViewById(R.id.emailEdit);
        EditText passwordEdit = this.findViewById(R.id.passwordEdit);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpUser(emailEdit.getText().toString(),
                        passwordEdit.getText().toString());
            }
        });
    }

    private void signUpUser(String email, String password) {
        // Validation
        Intent data = new Intent();

        MainActivity.mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()){
                        Log.d("AUTH", "createUserWithEmail:success");
                        FirebaseUser user = MainActivity.mAuth.getCurrentUser();

                        Toast.makeText(SignupActivity.this, user.getEmail(),
                                Toast.LENGTH_SHORT).show();

                        data.putExtra(MainActivity.AUTH_STATUS, "success");
                        setResult(RESULT_OK, data);
                    } else {
                        Log.d("AUTH", "createUserWithEmail:failed "
                                + task.getException());

                        Toast.makeText(SignupActivity.this, "Authentication failed",
                                Toast.LENGTH_SHORT).show();

                        data.putExtra(MainActivity.AUTH_STATUS, "failure");
                        setResult(RESULT_OK, data);
                    }

                    finish();
                });
    }
}