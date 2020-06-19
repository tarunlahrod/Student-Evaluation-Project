package com.example.studentevaluationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignedInMainMenuActivity extends AppCompatActivity {

    private Button signOutButton, addDetailsButton, showLogsButton;
    private TextView textView;
    private ConstraintLayout constraintLayout;

    private ProgressDialog progressDialog;

    FirebaseUser user;
    DatabaseReference rootRef, retrieveRef, uidRef;

    String u_name, u_email, u_gender, u_phone, u_branch, u_batch, u_semester, u_roll_no, u_post, u_class_code;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed_in_main_menu);
        getSupportActionBar().setTitle("User Menu");

        signOutButton = (Button) findViewById(R.id.signOutButton);
        addDetailsButton = (Button) findViewById(R.id.addDetailsButton);
        showLogsButton = (Button) findViewById(R.id.showLogsButton);
        textView = (TextView) findViewById(R.id.textView);
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);

        user = FirebaseAuth.getInstance().getCurrentUser();
        u_name = user.getDisplayName();
        u_email = user.getEmail();
        uid = user.getUid();

        String msg = "Welcome " + u_name + "!\n(" + u_email + ")";
        textView.setText(msg);

        Snackbar snackbar = Snackbar.make(constraintLayout, "Signed in as " + u_name, Snackbar.LENGTH_SHORT);
        snackbar.show();



        // check if the current user exists in the database
        rootRef = FirebaseDatabase.getInstance().getReference();
        uidRef = rootRef.child("all_users").child(uid);

        new CountDownTimer(3000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.i("timer ticking down", "tick!");
            }

            @Override
            public void onFinish() {

            }
        }.start();

        uidRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.i("key of dataSnapshot", String.valueOf(dataSnapshot.getChildrenCount()));

                if(!dataSnapshot.exists()) {
                    // go to add user activity
                    Intent intent = new Intent(getApplicationContext(), AddUserDetailsActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(SignedInMainMenuActivity.this, "User already present", Toast.LENGTH_SHORT).show();
                    updateUserProfileInstance();
                    showUserProfileData();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // Sign Out button onClickListener
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(SignedInMainMenuActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Signed Out", Toast.LENGTH_SHORT).show();
            }
        });

        // Add details button onClickListener
        addDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignedInMainMenuActivity.this, AddUserDetailsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        showLogsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUserProfileData();
            }
        });
    }

    public void showUserProfileData() {
        UserProfile user = UserProfile.getInstance();

        Log.i("name", user.getName());
        Log.i("roll", user.getRoll_no());
        Log.i("phone", user.getPhone_no());
        Log.i("email", user.getEmail());
        Log.i("gender", user.getGender());
        Log.i("batch", user.getBatch());
        Log.i("branch", user.getBranch());
        Log.i("semester", user.getSemester());
    }

    public void updateUserProfileInstance() {

        UserProfile user = UserProfile.getInstance();

        if(user.getSemester().isEmpty()){

            Log.i("data from", "firebase");

            retrieveRef = rootRef.child("all_users").child(uid);
            Log.i("getting close to hell", retrieveRef.toString());

            retrieveRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Log.i("dataSnapshot retriever", dataSnapshot.getValue().toString());

                    u_roll_no = dataSnapshot.child("roll_no").getValue().toString();
                    u_post = dataSnapshot.child("post").getValue().toString();
                    u_class_code = dataSnapshot.child("class_code").getValue().toString();

                    Log.i("roll", u_roll_no);
                    Log.i("post", u_post);
                    Log.i("class code", u_class_code);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



//            ProgressBar progressBar;
//            progressBar = new ProgressBar(SignedInMainMenuActivity.this);
//
//             wait till all the three retrieved values are not null
//            while( (u_roll_no == null) || (u_post == null) || (u_class_code == null) ) {
//                Log.i("tag", "waiting for the data to download");
//                progressBar.setVisibility(View.VISIBLE);
//            }
//            progressBar.setVisibility(View.INVISIBLE);

            Log.i("I am", "here");
            System.out.println(u_post);

            // retrieving the rest of the user data
            if(u_post.equals("student")) {
                retrieveRef = rootRef.child("class").child(u_class_code).child("student").child(u_roll_no);
            }
            else {
                retrieveRef = rootRef.child("faculty").child(u_roll_no);
            }
            retrieveRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    u_name = dataSnapshot.child("name").getValue().toString();
                    u_batch = dataSnapshot.child("batch").getValue().toString();
                    u_phone = dataSnapshot.child("phone").getValue().toString();
                    u_gender = dataSnapshot.child("gender").getValue().toString();
                    u_email = dataSnapshot.child("email").getValue().toString();
                    u_branch = dataSnapshot.child("branch").getValue().toString();
                    u_semester = dataSnapshot.child("semester").getValue().toString();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            user.addUserProfile(uid, u_roll_no, u_name, u_gender, u_phone, u_email, u_branch, u_semester, u_batch, u_post);
        }
        else {

            Log.i("data from", "User instance");

        }

    }
}