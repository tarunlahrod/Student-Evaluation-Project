package com.example.studentevaluationproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

public class MarkStudentEvaluationActivity extends AppCompatActivity {

    String roll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_student_evaluation);

        Intent intent = getIntent();
        roll = intent.getStringExtra("roll_no");
        getSupportActionBar().setTitle("Roll no: " + roll);


    }

}