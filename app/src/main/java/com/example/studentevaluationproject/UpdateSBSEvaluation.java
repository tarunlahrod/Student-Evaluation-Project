package com.example.studentevaluationproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UpdateSBSEvaluation extends AppCompatActivity {

    Button addEvaluationToFirebaseButton;
    EditText evaluation_copied_data_EditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_s_b_s_evaluation);
        getSupportActionBar().setTitle("SBS Evaluation");

        addEvaluationToFirebaseButton = (Button) findViewById(R.id.addEvaluationToFirebaseButton);
        evaluation_copied_data_EditText = (EditText) findViewById(R.id.evaluation_copied_data_EditText);

        addEvaluationToFirebaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "Input:\n";
                msg = msg + evaluation_copied_data_EditText.getText().toString();
                Toast.makeText(UpdateSBSEvaluation.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}