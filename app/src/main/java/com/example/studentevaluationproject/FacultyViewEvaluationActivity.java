package com.example.studentevaluationproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FacultyViewEvaluationActivity extends AppCompatActivity {

    ListView studentListView;
    ArrayList<String> studentList = new ArrayList<>();
    ArrayList<String> nameList = new ArrayList<>();

    ArrayAdapter<String> arrayAdapter;

    DatabaseReference rootRef, studentRef;
    UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_view_evaluation);
        getSupportActionBar().setTitle("View Evaluation");

        studentListView = (ListView) findViewById(R.id.studentListView);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, studentList);
        studentListView.setAdapter(arrayAdapter);

        rootRef = FirebaseDatabase.getInstance().getReference();
        studentRef = rootRef.child("class").child(userProfile.getInstance().getClass_code()).child("evaluation");

        studentRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                studentList.add(dataSnapshot.getKey());
                nameList.add(dataSnapshot.child("name").getValue(String.class));
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        studentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), nameList.get(position) + "'s marks shows up", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FacultyViewEvaluationActivity.this, MarkStudentEvaluationActivity.class);
                intent.putExtra("roll_no", parent.getItemIdAtPosition(position));
                startActivity(intent);
            }
        });
    }
}