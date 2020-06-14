package com.example.studentevaluationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class AddUserDetailsActivity extends AppCompatActivity {

    UserProfile user = new UserProfile();

    ScrollView containerScrollView;
    EditText rollEditText, phoneEditText;
    Spinner studentBatchSpinner, studentBranchSpinner, studentSemesterSpinner, classAdvBranchSpinner, classAdvSemesterSpinner;
    RadioGroup postRadioGroup;
    RadioButton radioButton;
    Button saveDetailsButton;
    TextView classAdvTextView, optionTextView;
    LinearLayout studentLayout, classAdvLayout;

    ArrayAdapter<CharSequence> batchAdapter, semesterAdapter, branchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_details);
        getSupportActionBar().setTitle("User Details");

        containerScrollView = (ScrollView) findViewById(R.id.containerScrollView);
        optionTextView = (TextView) findViewById(R.id.optionTextView);
        rollEditText = (EditText) findViewById(R.id.rollEditText);
        phoneEditText = (EditText) findViewById(R.id.phoneEditText);
        postRadioGroup = (RadioGroup) findViewById(R.id.postRadioGroup);
        studentBatchSpinner = (Spinner) findViewById(R.id.studentBatchSpinner);
        studentBranchSpinner = (Spinner) findViewById(R.id.studentBranchSpinner);
        studentSemesterSpinner = (Spinner) findViewById(R.id.studentSemesterSpinner);
        classAdvBranchSpinner = (Spinner) findViewById(R.id.classAdvBranchSpinner);
        classAdvSemesterSpinner = (Spinner) findViewById(R.id.classAdvSemesterSpinner);
        studentLayout = (LinearLayout) findViewById(R.id.studentLayout);
        classAdvLayout = (LinearLayout) findViewById(R.id.classAdvLayout);
        classAdvTextView = (TextView) findViewById(R.id.classAdvTextView);
        saveDetailsButton = (Button) findViewById(R.id.saveDetailsButton);

        // adding the resource arrays to the adapters
        batchAdapter = ArrayAdapter.createFromResource(this, R.array.batch_values, android.R.layout.simple_spinner_item);
        branchAdapter = ArrayAdapter.createFromResource(this, R.array.branch_values, android.R.layout.simple_spinner_item);
        semesterAdapter = ArrayAdapter.createFromResource(this, R.array.semesters_values, android.R.layout.simple_spinner_item);

        // adding the dropdown view to the adapters
        batchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // adding the adapters to the spinners
        studentBatchSpinner.setAdapter(batchAdapter);
        studentBranchSpinner.setAdapter(branchAdapter);
        studentSemesterSpinner.setAdapter(semesterAdapter);
        classAdvBranchSpinner.setAdapter(branchAdapter);
        classAdvSemesterSpinner.setAdapter(semesterAdapter);

        // remove faculty elements initially
        classAdvTextView.setVisibility(View.GONE);
        classAdvLayout.setVisibility(View.GONE);
    }

    public void onRadioButtonClicked(View view) {
        int radioId = postRadioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(radioId);
        String post = radioButton.getText().toString();

        optionTextView.setText(post);

        if(post.equals("student")) {
            // remove faculty elements
            classAdvTextView.setVisibility(View.GONE);
            classAdvLayout.setVisibility(View.GONE);

            // add student elements
            studentLayout.setVisibility(View.VISIBLE);
        }
        else {
            // remove student elements
            studentLayout.setVisibility(View.GONE);

            // add faculty elements
            classAdvTextView.setVisibility(View.VISIBLE);
            classAdvLayout.setVisibility(View.VISIBLE);

        }
    }
}