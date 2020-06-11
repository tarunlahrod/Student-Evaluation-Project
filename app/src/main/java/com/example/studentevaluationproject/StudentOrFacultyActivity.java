package com.example.studentevaluationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class StudentOrFacultyActivity extends AppCompatActivity {

    UserProfile user;

    // UI elements for pop up window
    RadioGroup postRadioGroup;
    RadioButton radioButton;
    TextView optionTextView;
    Button continueButton;

    String thisUserPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_or_faculty);
        getSupportActionBar().setTitle("User Details");

        Intent intent = getIntent();
        thisUserPost = intent.getStringExtra("postValue");

        postRadioGroup = (RadioGroup) findViewById(R.id.postRadioGroup);
        optionTextView = (TextView) findViewById(R.id.optionTextView);
        continueButton = (Button) findViewById(R.id.continueButton);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = postRadioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(radioId);

                thisUserPost = radioButton.getText().toString();
                Toast.makeText(getApplicationContext(), "Selected " + radioButton.getText().toString(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    public void onRadioButtonClicked(View view) {
        int radioId = postRadioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(radioId);
        optionTextView.setText(radioButton.getText().toString());
    }
}