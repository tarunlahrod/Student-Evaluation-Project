package com.example.studentevaluationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddSubjectsActivity extends AppCompatActivity {

    private Button addSubjectButton;
    private EditText subjectNameEditText, subjectCodeEditText, subjectCreditsEditText, subjectShortNameEditText;

    private String sub_name, sub_code, sub_short_name;
    private int sub_credits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subjects);
        getSupportActionBar().setTitle("Add Subject");

        addSubjectButton = (Button) findViewById(R.id.addSubjectButton);
        subjectNameEditText = (EditText) findViewById(R.id.subjectNameEditText);
        subjectCodeEditText = (EditText) findViewById(R.id.subjectCodeEditText);
        subjectCreditsEditText = (EditText) findViewById(R.id.subjectCreditsEditText);
        subjectShortNameEditText = (EditText) findViewById(R.id.subjectShortNameEditText);

        addSubjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(allDetailsFilledCorrectly()) {
                    Subject_class new_subject = new Subject_class(sub_name, sub_code, sub_credits, sub_short_name);

                    if(new_subject.checkFirebaseExists()) {
                        generateToast("Subject with same code already exists");
                    }
                    else {
                        new_subject.addSubjectToFirebase();
                        Intent intent = new Intent(getApplicationContext(), FacultySubjectsActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }

    private boolean allDetailsFilledCorrectly() {

        // Subject name validation
        if(subjectNameEditText.getText().toString().isEmpty()) {
            generateToast("Enter subject name");
            return false;
        }
        else {
            sub_name = subjectNameEditText.getText().toString();
        }

        // Subject code validation
        if(subjectCodeEditText.getText().toString().isEmpty()) {
            generateToast("Enter subject code");
            return false;
        }
        else {
            sub_code = subjectCodeEditText.getText().toString();
        }

        // Subject credits validation
        if(subjectCreditsEditText.getText().toString().isEmpty()) {
            generateToast("Enter credits");
            return  false;
        }
        String credit_value = subjectCreditsEditText.getText().toString();
        int value = Integer.parseInt(credit_value);
        if(value < 1 || value > 4) {
            generateToast("Enter credit value between 1 to 4");
            return false;
        }
        else {
            sub_credits = value;
        }

        // Subject short name validation
        if(subjectShortNameEditText.getText().toString().isEmpty()) {
            generateToast("Enter short name");
            return false;
        }
        else {
            sub_short_name = subjectShortNameEditText.getText().toString();
        }

        // All details are correct, return true
        return true;
    }

    private void generateToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}