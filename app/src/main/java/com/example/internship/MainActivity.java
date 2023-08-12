package com.example.internship;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
FirebaseAuth auth;
Button button;
TextView textView;
FirebaseUser user;
    private Bundle savedInstanceState;

    @Override
    protected void
    onCreate(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        button=findViewById(R.id.Logout);
        textView =findViewById(R.id.cameron);
        textView =findViewById(R.id.userid);
        user=auth.getCurrentUser();
        if(user==null){
            Intent intent=new Intent(getApplicationContext(),Login.class);
            startActivity(intent);
            finish();
        }else {
            textView.setText(user.getEmail());
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });
        textView =findViewById(R.id.ProductId);

        textView=findViewById(R.id.Shrimpsize);
        textView =findViewById(R.id.DaysWise);
        button=findViewById(R.id.radioButton2);
        button=findViewById(R.id.radioButton3);
        button=findViewById(R.id.radioButton4);

    }
}