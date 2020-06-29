package com.example.studentevaluationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FacultyEvaluationActivity extends AppCompatActivity {

    private Button updateEvaluationAll, updateEvaluationSBS, viewEvaluation;
    private Switch evaluationCompleteSwitch;

    DatabaseReference rootRef, evaluationStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_evaluation);
        getSupportActionBar().setTitle("Class Evaluation");

        evaluationCompleteSwitch = (Switch) findViewById(R.id.evaluationCompleteSwitch);
        updateEvaluationAll = (Button) findViewById(R.id.updateEvaluationAll);
        updateEvaluationSBS = (Button) findViewById(R.id.updateEvaluationSBS);
        viewEvaluation = (Button) findViewById(R.id.viewEvaluation);

        rootRef = FirebaseDatabase.getInstance().getReference();

        evaluationStatus = rootRef.child("class").child(UserProfile.getInstance().getClass_code()).child("evaluation_completed");
        getUpdatedEvaluationSwitch();

        evaluationCompleteSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                evaluationStatus.setValue(isChecked);
            }
        });

        updateEvaluationAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FacultyEvaluationActivity.this, UpdateAllEvaluationActivity.class));
            }
        });

        updateEvaluationSBS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FacultyEvaluationActivity.this, UpdateSBSEvaluation.class));

            }
        });

        viewEvaluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FacultyEvaluationActivity.this, FacultyViewEvaluationActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_subjects, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_view_subjects) {
            startActivity(new Intent(FacultyEvaluationActivity.this, FacultySubjectsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void getUpdatedEvaluationSwitch() {
        evaluationStatus.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("switch dataSnapshot", dataSnapshot.toString());
                evaluationCompleteSwitch.setChecked( (boolean) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}