package com.example.studentevaluationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FacultySubjectsActivity extends AppCompatActivity {

    ArrayList<String> subjectList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

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

        user_details_message = "Class: " + userProfile.getBranch() + " (" + userProfile.getSemester() + " semester)";


        if(subjectList.size() == 0) {
            messageTextView.setText(user_details_message + "\n\nPress '+' to add a subject");
        }
        else {
            messageTextView.setText(user_details_message);
        }

//        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, testingList);
//        subjectListView.setAdapter(arrayAdapter);
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
        }
        return super.onOptionsItemSelected(item);
    }
}