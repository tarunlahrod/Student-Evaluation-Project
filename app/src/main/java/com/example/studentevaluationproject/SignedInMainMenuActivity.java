package com.example.studentevaluationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignedInMainMenuActivity extends AppCompatActivity {

    private Button signOutButton, addDetailsButton;
    private TextView textView;

    FirebaseUser user;

    private String u_name, u_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed_in_main_menu);

        signOutButton = (Button) findViewById(R.id.signOutButton);
        addDetailsButton = (Button) findViewById(R.id.addDetailsButton);
        textView = (TextView) findViewById(R.id.textView);


        user = FirebaseAuth.getInstance().getCurrentUser();
        u_name = user.getDisplayName();
        u_email = user.getEmail();

        String msg = "Welcome " + u_name + "!\n (" + u_email + ")";

        textView.setText(msg);

        Toast.makeText(this, "Signed in as " + u_name, Toast.LENGTH_LONG).show();



        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(SignedInMainMenuActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Signed Out", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        addDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignedInMainMenuActivity.this, AddUserDetailsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}