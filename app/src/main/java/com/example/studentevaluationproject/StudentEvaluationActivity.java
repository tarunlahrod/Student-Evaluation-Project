package com.example.studentevaluationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class StudentEvaluationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_evaluation);
        getSupportActionBar().setTitle("Student Evaluation");
    }
}