package com.example.studentevaluationproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewAttendanceActivity extends AppCompatActivity {

    private ListView attendance_ListView;
    private TextView class_TextView;

    private int total_lectures;
    String total_lectures_string, branch, semester;

    ArrayList<StudentAttendanceRow> attendanceRowList = new ArrayList<>();

    DatabaseReference rootRef, currentRef;
    UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);
        getSupportActionBar().setTitle("Attendance Record");

        attendance_ListView = (ListView) findViewById(R.id.attendance_ListView);
        class_TextView = (TextView) findViewById(R.id.class_TextView);

        branch = userProfile.getInstance().getBranch();
        semester = userProfile.getInstance().getSemester();
        class_TextView.setText(branch + " (Semester " + semester + ")");

        final AttendanceListAdapter attendanceListAdapter = new AttendanceListAdapter(this, R.layout.attendance_adapter_view_layout, attendanceRowList);
        attendance_ListView.setAdapter(attendanceListAdapter);

        rootRef = FirebaseDatabase.getInstance().getReference();
        currentRef = rootRef.child("class").child(userProfile.getInstance().getClass_code()).child("attendance");

        // Get the value of Total number of lectures
        currentRef.child("total_lecture_count").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                total_lectures_string = dataSnapshot.getValue(String.class);
                total_lectures = Integer.parseInt(total_lectures_string);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Get the student attendance and add it to list view
        currentRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String name, roll, attendance;
                roll = dataSnapshot.getKey();
                Log.i("key", roll);

                if(!roll.equals("total_lecture_count")) {
                    name = dataSnapshot.child("name").getValue(String.class);
                    attendance = dataSnapshot.child("attendance").getValue(String.class);

                    Log.i("name", name);
                    Log.i("attendance", attendance);

                    int percentage = Integer.parseInt(attendance) * 100;
                    percentage /= total_lectures;

                    attendanceRowList.add(new StudentAttendanceRow(name, roll, attendance + "/" + total_lectures_string, String.valueOf(percentage) + "%"));
                    attendanceListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                attendanceListAdapter.notifyDataSetChanged();
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
}