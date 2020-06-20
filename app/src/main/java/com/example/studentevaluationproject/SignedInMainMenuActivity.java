package com.example.studentevaluationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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

    private Button signOutButton, addDetailsButton, showLogsButton;
    private TextView textView;
    private ConstraintLayout constraintLayout;

    FirebaseUser user;
    DatabaseReference rootRef, retrieveRef, uidRef;

    String u_name, u_email, u_gender, u_phone, u_branch, u_batch, u_semester, u_roll_no, u_post, u_class_code;
    private String uid;

    UserProfile userProfile = UserProfile.getInstance();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile, menu);
        return super.onCreateOptionsMenu(menu);
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
        }
        else {
            Log.i("Retrieving data from", "User instance");
        }
    }

    public void removeSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserProfileDatabase", MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }
}