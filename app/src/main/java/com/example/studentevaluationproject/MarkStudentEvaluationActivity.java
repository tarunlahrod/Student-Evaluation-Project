package com.example.studentevaluationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MarkStudentEvaluationActivity extends AppCompatActivity {

    DatabaseReference rootRef, currentRef;
    String roll, name;

    ArrayList<String> sub_code = new ArrayList<>(1);
    ArrayList<String> sub_score = new ArrayList<>(1);

    UserProfile userProfile;

    private TextView evaluationOutputTextView, evaluationStudentNameTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_student_evaluation);

        evaluationOutputTextView = (TextView) findViewById(R.id.evaluationOutputTextView);
        evaluationStudentNameTV = (TextView) findViewById(R.id.evaluationStudentNameTV);
        rootRef = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        roll = intent.getStringExtra("roll_no");
        getSupportActionBar().setTitle("Roll no: " + roll);

        currentRef = rootRef.child("class").child(userProfile.getInstance().getClass_code()).child("evaluation").child(roll);

        currentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    sub_code.add(snapshot.getKey().toString());
                    sub_score.add(snapshot.getValue().toString());
                }
                displayData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }

    private void displayData() {

        StringBuilder toShow = new StringBuilder();

        for(int i = 0; i < sub_code.size(); i++) {
            if(sub_code.get(i).equals("name")) {
                name = sub_score.get(i);
            }
            else {
                toShow.append(sub_code.get(i)).append(": ").append(sub_score.get(i)).append("\n");
            }
        }

        evaluationStudentNameTV.setText(name);
        evaluationOutputTextView.setText(toShow.toString());
    }
}