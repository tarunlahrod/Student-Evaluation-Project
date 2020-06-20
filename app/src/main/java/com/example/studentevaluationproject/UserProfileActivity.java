package com.example.studentevaluationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class UserProfileActivity extends AppCompatActivity {

    private TextView nameTV, postTV, rollTV, phoneTV, genderTV, batchTV, branchTV, semesterTV, emailTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        getSupportActionBar().setTitle("User Profile");

        nameTV = (TextView) findViewById(R.id.nameTV);
        rollTV = (TextView) findViewById(R.id.rollTV);
        genderTV = (TextView) findViewById(R.id.genderTV);
        postTV = (TextView) findViewById(R.id.postTV);
        branchTV = (TextView) findViewById(R.id.branchTV);
        batchTV = (TextView) findViewById(R.id.batchTV);
        semesterTV = (TextView) findViewById(R.id.semesterTV);
        emailTV = (TextView) findViewById(R.id.emailTV);
        phoneTV = (TextView) findViewById(R.id.phoneTV);

        showDetails();
    }

    private void showDetails() {
        UserProfile userProfile = UserProfile.getInstance();

        nameTV.setText(userProfile.getName());
        rollTV.setText(userProfile.getRoll_no());
        genderTV.setText(userProfile.getGender());
        postTV.setText(userProfile.getPost());
        branchTV.setText(userProfile.getBranch());
        batchTV.setText(userProfile.getBatch());
        phoneTV.setText(userProfile.getPhone_no());
        emailTV.setText(userProfile.getEmail());
        semesterTV.setText(userProfile.getSemester());
    }

}