package com.example.studentevaluationproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static java.util.Collections.sort;

public class FacultySubjectsActivity extends AppCompatActivity {

    ArrayList<String> subjectList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    DatabaseReference rootRef, subRef;

    String user_details_message;

    UserProfile userProfile = UserProfile.getInstance();

    private TextView messageTextView;
    private ListView subjectListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_subjects);
        getSupportActionBar().setTitle("Subjects");

        messageTextView = (TextView) findViewById(R.id.messageTextView);
        subjectListView = (ListView) findViewById(R.id.subjectListView);

        user_details_message = "Class: " + userProfile.getBranch() + "\n(" + userProfile.getSemester() + " semester)";
        messageTextView.setText(user_details_message);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, subjectList);
        subjectListView.setAdapter(arrayAdapter);

        rootRef = FirebaseDatabase.getInstance().getReference();
        subRef = rootRef.child("class").child(userProfile.getClass_code()).child("subjects");

        subRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.i("datasnapshot", dataSnapshot.toString());
                String value = dataSnapshot.child("name").getValue(String.class);
                subjectList.add(value);
                sort(subjectList);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_subject, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_add_subject) {
            startActivity(new Intent(FacultySubjectsActivity.this, AddSubjectsActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}