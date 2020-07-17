package com.example.studentevaluationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

    private Button signOutButton, addDetailsButton, evaluationButton, attendanceButton;
    private TextView textView;
    private ConstraintLayout constraintLayout;

    boolean userPresent = false;

    FirebaseUser user;
    DatabaseReference rootRef, uidRef;

    String u_name, u_email, u_class_code, u_post, u_roll, u_batch, u_semester, u_branch, u_phone, u_gender;
    private String uid;

    UserProfile userProfile = UserProfile.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed_in_main_menu);
        getSupportActionBar().setTitle("Main Menu");

        signOutButton = (Button) findViewById(R.id.signOutButton);
        evaluationButton = (Button) findViewById(R.id.evaluationButton);
        attendanceButton = (Button) findViewById(R.id.attendanceButton);
        textView = (TextView) findViewById(R.id.textView);
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);

        // removed Add details button from the Main Menu, though all the implementation is still intact in this code
//        addDetailsButton = (Button) findViewById(R.id.addDetailsButton);

        updateButtonStatus();

        user = FirebaseAuth.getInstance().getCurrentUser();
        u_name = user.getDisplayName();
        u_email = user.getEmail();
        uid = user.getUid();

        String msg = "Welcome " + u_name + "!\n(" + u_email + ")";
        textView.setText(msg);

        Snackbar snackbar = Snackbar.make(constraintLayout, "Signed in as " + u_name, Snackbar.LENGTH_SHORT);
        snackbar.show();

        // Check if user just added to firebase, then save its data to Shared Preferences
        if(userProfile.getUserJustAdded()) {
            saveDataToSharedPrefs();
        }

        rootRef = FirebaseDatabase.getInstance().getReference();
        uidRef = rootRef.child("all_users").child(uid);

        // check if the current user exists in the database
        rootRef = FirebaseDatabase.getInstance().getReference();
        uidRef = rootRef.child("all_users").child(uid);

        uidRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

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
                    userPresent = true;
                    updateButtonStatus();
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
                removeSharedPreferences();

                Intent intent = new Intent(SignedInMainMenuActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Signed Out", Toast.LENGTH_SHORT).show();
            }
        });

//        // Add details button onClickListener
//        addDetailsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SignedInMainMenuActivity.this, AddUserDetailsActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        // Evaluations button onClickListener
        evaluationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(userProfile.getPost().equals("student")) {
                    startActivity(new Intent(SignedInMainMenuActivity.this, StudentEvaluationActivity.class));
                }
                else {
                    startActivity(new Intent(SignedInMainMenuActivity.this, FacultyEvaluationActivity.class));
                }

            }
        });

        // Attendance button onClickListener
        attendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userProfile.getPost().equals("student")) {

                }
                else {
                    startActivity(new Intent(SignedInMainMenuActivity.this, UpdateAttendanceActivity.class));
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.action_profile) {
            startActivity(new Intent(SignedInMainMenuActivity.this, UserProfileActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void showUserProfileData() {

        userProfile = UserProfile.getInstance();

        Log.i("name", userProfile.getName());
        Log.i("post", userProfile.getPost());
        Log.i("roll", userProfile.getRoll_no());
        Log.i("phone", userProfile.getPhone_no());
        Log.i("email", userProfile.getEmail());
        Log.i("gender", userProfile.getGender());
        Log.i("batch", userProfile.getBatch());
        Log.i("branch", userProfile.getBranch());
        Log.i("semester", userProfile.getSemester());
    }

    public void saveDataToSharedPrefs() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserProfileDatabase", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        userProfile = UserProfile.getInstance();

        editor.putString("user_name", userProfile.getName());
        editor.putString("user_roll", userProfile.getRoll_no());
        editor.putString("user_phone", userProfile.getPhone_no());
        editor.putString("user_email", userProfile.getEmail());
        editor.putString("user_batch", userProfile.getBatch());
        editor.putString("user_branch", userProfile.getBranch());
        editor.putString("user_semester", userProfile.getSemester());
        editor.putString("user_gender", userProfile.getGender());
        editor.putString("user_post", userProfile.getPost());
        editor.putString("user_class_code", userProfile.getClass_code());
        editor.putString("user_uid", uid);

        editor.apply();
        // set userJustAdded to false
        userProfile.setUserJustAdded(false);

        Log.i("saveDataToSharedPrefs", "Data saved.");
    }

    public void updateUserProfileInstance() {

        if(userProfile.getSemester().isEmpty()) {

            Log.i("Retrieving data from", "shared preferences");

            SharedPreferences sP = getSharedPreferences("UserProfileDatabase", MODE_PRIVATE);

            userProfile.setName(sP.getString("user_name", ""));
            userProfile.setRoll_no(sP.getString("user_roll", ""));
            userProfile.setPhone_no(sP.getString("user_phone", ""));
            userProfile.setEmail(sP.getString("user_email", ""));
            userProfile.setBatch(sP.getString("user_batch", ""));
            userProfile.setBranch(sP.getString("user_branch", ""));
            userProfile.setSemester(sP.getString("user_semester", ""));
            userProfile.setGender(sP.getString("user_gender", ""));
            userProfile.setPost(sP.getString("user_post", ""));
            userProfile.setClass_code(sP.getString("user_class_code", ""));
            userProfile.setUid(sP.getString("user_uid", ""));

            Log.i("SP testing", sP.getString("user_name", "not working"));
            Log.i("Load data from SP", "Data loaded from Shared Preferences");

            if(userProfile.getSemester().isEmpty()) {

//                // Now go and retrieve data from the firebase database, Meh!
//                Log.i("Retrieving data from", "Firebase Database");
//
//                DatabaseReference mDB;
//                if(u_post.equals("student")) {
//                    mDB = rootRef.child("class").child(u_class_code).child("student").child(u_roll);
//                    mDB.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            u_phone = dataSnapshot.child("phone").getValue().toString();
//                            u_gender = dataSnapshot.child("gender").getValue().toString();
//                            u_batch = dataSnapshot.child("batch").getValue().toString();
//                            u_branch = dataSnapshot.child("branch").getValue().toString();
//                            u_semester = dataSnapshot.child("semester").getValue().toString();
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//                    userProfile.addUserProfile(uid, u_roll, u_name, u_gender, u_phone, u_email, u_branch, u_semester, u_batch, u_post);
//                    saveDataToSharedPrefs();
//                    Log.i("Retrieved data from", "Firebase Database");
//                }
            }
        }
        else {
            Log.i("Retrieving data from", "User instance");
        }
    }

    public void removeSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserProfileDatabase", MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

    public void updateButtonStatus() {
        if(!userPresent) {
//            addDetailsButton.setEnabled(false);
            evaluationButton.setEnabled(false);
            attendanceButton.setEnabled(false);
        }
        else {
//            addDetailsButton.setEnabled(true);
            evaluationButton.setEnabled(true);
            attendanceButton.setEnabled(true);
        }
    }
}