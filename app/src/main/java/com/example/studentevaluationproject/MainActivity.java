package com.example.studentevaluationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Button signInButton;
    private ImageView logoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        signInButton = (Button) findViewById(R.id.signInButton);
        logoImageView = (ImageView) findViewById(R.id.logoImageView);

        signInButton.setVisibility(View.GONE);

        openingAnimation();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // this code will be called after delay
                startActivity(new Intent(MainActivity.this, GoogleSignInActivity.class));
                finish();
            }
        }, 1000);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GoogleSignInActivity.class);
                startActivity(intent);
            }
        });
    }

    private void openingAnimation() {
        logoImageView.setAlpha(0.1f);
        logoImageView.animate().alpha(1.0f).setDuration(800);
    }
}