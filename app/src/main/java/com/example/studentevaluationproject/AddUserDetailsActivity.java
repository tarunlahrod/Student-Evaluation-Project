package com.example.studentevaluationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class AddUserDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ScrollView containerScrollView;
    EditText rollEditText, phoneEditText, batchEditText, semesterEditText;
    Spinner branchSpinner;
    RadioGroup postRadioGroup, genderRadioGroup;
    RadioButton postRadioButton, genderRadioButton;
    Button saveDetailsButton;
    TextView classAdvTextView, optionTextView;

    ArrayAdapter<CharSequence> branchAdapter;

    // entered values
    String user_name, user_email, user_roll, user_phone, user_gender, user_batch, user_branch, user_semester, user_post;

    String branchValue;

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
        genderRadioGroup = (RadioGroup) findViewById(R.id.genderRadioGroup);
        batchEditText = (EditText) findViewById(R.id.batchEditText);
        branchSpinner = (Spinner) findViewById(R.id.branchSpinner);
        semesterEditText = (EditText) findViewById(R.id.semesterEditText);
        classAdvTextView = (TextView) findViewById(R.id.classAdvTextView);
        saveDetailsButton = (Button) findViewById(R.id.saveDetailsButton);

        int radioId = postRadioGroup.getCheckedRadioButtonId();
        postRadioButton = (RadioButton) findViewById(radioId);
        user_post = postRadioButton.getText().toString();

        // adding the resource arrays to the adapter
        branchAdapter = ArrayAdapter.createFromResource(this, R.array.branch_values, android.R.layout.simple_spinner_item);
        branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branchSpinner.setAdapter(branchAdapter);
        branchSpinner.setOnItemSelectedListener(this);

        saveDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(allDetailsAreFilledCorrectly()) {

                    // add user details to the user profile object
                    FirebaseUser firebaseUser;
                    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    user_name = firebaseUser.getDisplayName();
                    user_email = firebaseUser.getEmail();

                    UserProfile userProfile = new UserProfile(user_roll, user_name, user_gender, user_phone, user_email,user_branch, user_semester, user_batch, user_post);
                    userProfile.addUserToFirebase();
                    Toast.makeText(getApplicationContext(), "Details updated", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(AddUserDetailsActivity.this, SignedInMainMenuActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        int radioId = postRadioGroup.getCheckedRadioButtonId();
        postRadioButton = (RadioButton) findViewById(radioId);
        user_post = postRadioButton.getText().toString();

        optionTextView.setText(user_post);

        if(user_post.equals("student")) {
            classAdvTextView.setText("I am a student of");
        }
        else {
            classAdvTextView.setText("I am Class Advisor of");
        }
    }

    public void generateToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public boolean allDetailsAreFilledCorrectly() {

        if(rollEditText.getText().toString().isEmpty()) {
            generateToast("Enter roll no");
            return false;
        }
        else {
            user_roll = rollEditText.getText().toString();
        }

        if(phoneEditText.getText().toString().length() != 10) {
            generateToast("Enter phone number correctly");
            return false;
        }
        else {
            user_phone = phoneEditText.getText().toString();
        }

        // getting user's gender
        int genderRadioId = genderRadioGroup.getCheckedRadioButtonId();
        genderRadioButton = findViewById(genderRadioId);
        user_gender = genderRadioButton.getText().toString();


        String batch = batchEditText.getText().toString();
        if(batch.isEmpty()) {
            generateToast("Enter a batch");
            return false;
        }

        int batchYear = Integer.parseInt(batch);
        if(batchYear < 2016 || batchYear > 2020) {
            generateToast("Enter a valid batch");
            return false;
        }
        else {
            user_batch = batch;
        }

        String semester = semesterEditText.getText().toString();
        int semesterValue = Integer.parseInt(semester);
        if(semesterValue < 1 || semesterValue > 8) {
            generateToast("Enter a valid semester");
            return false;
        }
        else {
            user_semester = semester;
        }

        if(branchValue.equals("-- Branch --")) {
            generateToast("Select a branch");
            return false;
        }
        else {
            user_branch = branchValue;
        }

        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        branchValue = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}