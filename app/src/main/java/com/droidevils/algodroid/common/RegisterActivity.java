package com.droidevils.algodroid.common;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.droidevils.algodroid.utility.LoadingDialog;
import com.droidevils.algodroid.utility.User;
import com.droidevils.algodroid.utility.Validation;
import com.droidevils.algodroid.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Timestamp;

public class RegisterActivity extends AppCompatActivity {

    private LoadingDialog loadingDialog;
    private TextView headerText;
    private TextInputLayout fullNameLayout, emailLayout, phoneLayout, passwordLayout, cpasswordLayout;
    private Button registerBtn, goToLoginBtn;
    private FirebaseAuth mAuth;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loadingDialog = new LoadingDialog(RegisterActivity.this);
        headerText = (TextView) findViewById(R.id.header);
        fullNameLayout = (TextInputLayout) findViewById(R.id.fullname_layout);
        emailLayout = (TextInputLayout) findViewById(R.id.email_layout);
        phoneLayout = (TextInputLayout) findViewById(R.id.phone_layout);
        passwordLayout = (TextInputLayout) findViewById(R.id.password_layout);
        cpasswordLayout = (TextInputLayout) findViewById(R.id.cpassword_layout);
        registerBtn = (Button) findViewById(R.id.register_button);
        goToLoginBtn = (Button) findViewById(R.id.goto_login);

//        Firebase
        mAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("User");


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.startLoadingDialog();
                String fullName = fullNameLayout.getEditText().getText().toString().trim();
                String email = emailLayout.getEditText().getText().toString().trim();
                String phone = phoneLayout.getEditText().getText().toString().trim();
                String password = passwordLayout.getEditText().getText().toString().trim();
                String cpassword = cpasswordLayout.getEditText().getText().toString().trim();
                String createdAt = new Timestamp(System.currentTimeMillis()).toString();

                if (!Validation.validateEmpty(fullNameLayout) | !Validation.validateEmail(emailLayout)
                        | !Validation.validateEmpty(phoneLayout)
                        | !Validation.validatePassword(passwordLayout)
                        | !Validation.comparePassword(passwordLayout, cpasswordLayout)) {
                    Toast.makeText(RegisterActivity.this, "Validation Failed", Toast.LENGTH_LONG).show();
                    loadingDialog.stopLoadingDialog();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(fullName, email, phone, password, createdAt);
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            reference.child(currentUser.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        loadingDialog.stopLoadingDialog();
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        loadingDialog.stopLoadingDialog();
                                        Toast.makeText(RegisterActivity.this, "Something went wrong. Try again", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else {
                            loadingDialog.stopLoadingDialog();
                            Toast.makeText(RegisterActivity.this, "User couldn't be added. Try again", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        goToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                Pair[] pairs = new Pair[5];
//                pairs[0] = new Pair<View, String>(headerText, "header_text");
//                pairs[1] = new Pair<View, String>(emailLayout, "email_trans");
//                pairs[2] = new Pair<View, String>(passwordLayout, "password_trans");
//                pairs[3] = new Pair<View, String>(registerBtn, "button_trans");
//                pairs[4] = new Pair<View, String>(goToLoginBtn, "goto_trans");
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterActivity.this, pairs);
//
//                startActivity(intent, options.toBundle());
                startActivity(intent);
                finish();
            }
        });
    }
}