package com.example.videoservice;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.videoservice.auth.Auth;
import com.example.videoservice.auth.activities.LoginActivity;
import com.example.videoservice.auth.activities.SignupActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static final String AUTH_STATUS = "AUTH_STATUS";

    public static FirebaseAuth mAuth;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Button loginBtn, signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);

        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupWithNavController(navigationView, navController);

        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        findViewById(R.id.userIconMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAuthDialog();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void updateUI(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            TextView profileName = findViewById(R.id.profileNameHeader);
            profileName.setText(mAuth.getCurrentUser().getEmail());
        }
    }

    private void createAuthDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View authPopupView = getLayoutInflater().inflate(R.layout.auth_popup, null);

        loginBtn = authPopupView.findViewById(R.id.login);
        signupBtn = authPopupView.findViewById(R.id.signup);

        dialogBuilder.setView(authPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                mAuthHandler.launch(intent);
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                mAuthHandler.launch(intent);
            }
        });
    }

    ActivityResultLauncher<Intent> mAuthHandler =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        String authStatus = intent.getStringExtra(AUTH_STATUS);
                        switch (authStatus) {
                            case "success":
                                Toast.makeText(MainActivity.this,
                                        "Welcome " + Objects.requireNonNull(mAuth.getCurrentUser()).getEmail(),
                                        Toast.LENGTH_SHORT).show();
                                updateUI();
                                break;
                            case "failure":
                                Toast.makeText(MainActivity.this, "Authentication error!",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(MainActivity.this, "Unknown auth status!",
                                        Toast.LENGTH_SHORT).show();


                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Authentication error!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
}