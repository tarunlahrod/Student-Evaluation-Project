package com.example.studentevaluationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class StudentEvaluationActivity extends AppCompatActivity {

    private Button view_student_evaluation_button;
    private TextView evaluation_status_for_student_TV;
    private LinearLayout studentEvaluationLayout;

    boolean evaluation_present = false;

    DatabaseReference rootRef;
    UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_evaluation);
        getSupportActionBar().setTitle("Student Evaluation");

        view_student_evaluation_button = (Button) findViewById(R.id.view_student_evaluation_button);
        evaluation_status_for_student_TV = (TextView) findViewById(R.id.evaluation_status_for_student_TV);
        studentEvaluationLayout = (LinearLayout) findViewById(R.id.studentEvaluationLayout);

        // check if the evaluation exists
        rootRef = FirebaseDatabase.getInstance().getReference().child("class").child(userProfile.getInstance().getClass_code()).child("evaluation_completed");
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                evaluation_present = (boolean) dataSnapshot.getValue();
                updateLayout();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
            startActivity(new Intent(StudentEvaluationActivity.this, StudentSubjectsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateLayout() {
        if(evaluation_present) {
            // evaluation is available
            // change the bg color
            studentEvaluationLayout.setBackgroundColor(Color.argb(100, 185, 244, 220));
            evaluation_status_for_student_TV.setText("Evaluation is available");
            evaluation_status_for_student_TV.setTextColor(Color.argb(100, 55, 143, 107));

            // on click "View evaluation button", move to next activity
            view_student_evaluation_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(StudentEvaluationActivity.this, MarkStudentEvaluationActivity.class);
                    intent.putExtra("roll_no", userProfile.getInstance().getRoll_no());
                    startActivity(intent);
                }
            });
        }
        else {
            // evaluation is not available
            // change the bg color
            studentEvaluationLayout.setBackgroundColor(Color.argb(100, 220, 171, 171));
            evaluation_status_for_student_TV.setText("Evaluation is not available");
            evaluation_status_for_student_TV.setTextColor(Color.argb(100, 223, 72, 72));

            // on click "View evaluation button", show Toast that evaluation is not ready
            view_student_evaluation_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Evaluation not ready", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}