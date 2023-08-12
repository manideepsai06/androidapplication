package com.example.internship;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.HashMap;
import java.util.Map;


public class Register extends AppCompatActivity {
    TextInputEditText editTextEmail,editTextPassword;
    Button buttonReg;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textView;
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    public static class UserEmailIdManager {
        private static UserEmailIdManager instance;
        private final Map<String, String> emailIdToUserIdMap;
        private Map<String, String> userIdToAuthTokenMap;

        private UserEmailIdManager() {
            emailIdToUserIdMap = new HashMap<>();
            userIdToAuthTokenMap = new HashMap<>();
        }

        public UserEmailIdManager getInstance() {
            if (instance == null) {
                instance = new UserEmailIdManager();
            }
            return instance;
        }

        public String generateAuthToken(String userId) {
            // Generate and store an authentication token for the user
            String authToken = generateRandomAuthToken();
            userIdToAuthTokenMap.put(userId, authToken);
            return authToken;
        }

        public boolean authenticateUser(String userId, String authToken) {
            // Check if the provided authentication token matches the stored token for the user
            String storedAuthToken = userIdToAuthTokenMap.get(userId);
            return storedAuthToken != null && storedAuthToken.equals(authToken);
        }

        private String generateRandomAuthToken() {
            // Generate a random authentication token
            // Replace this implementation with your own token generation logic
            return "YOUR_RANDOM_AUTH_TOKEN";
        }

        public void assignUserId(String emailId, String userId) {
            emailIdToUserIdMap.put(emailId, userId);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth=FirebaseAuth.getInstance();
        editTextEmail=findViewById(R.id.email);
        editTextPassword=findViewById(R.id.password);
        buttonReg=findViewById(R.id.btn_register);
        progressBar=findViewById(R.id.ProgressBar);
        textView=findViewById(R.id.Login_now);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });

        buttonReg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email,password;
                email=String.valueOf(editTextEmail.getText());
                password=String.valueOf(editTextPassword.getText());
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Register.this,"enter email",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Register.this,"enter password",Toast.LENGTH_SHORT).show();
                    return;}
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressBar.setVisibility((View.GONE));
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent intent = new Intent(getApplicationContext(),Login.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(Register.this,"Authentication failed",Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });
    }
}