package com.example.studentevaluationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewAttendanceActivity extends AppCompatActivity {

    private ListView attendance_ListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);
        getSupportActionBar().setTitle("Attendance Record");

        attendance_ListView = (ListView) findViewById(R.id.attendance_ListView);

        ArrayList<StudentAttendanceRow> attendanceRowList = new ArrayList<>();
        for(int i = 0; i < 15; i++) {
            attendanceRowList.add(new StudentAttendanceRow("Tarun Lahrod", "02911502717", "69", "96"));
            attendanceRowList.add(new StudentAttendanceRow("John Doe", "02811502717", "96", "69"));
        }

        AttendanceListAdapter attendanceListAdapter = new AttendanceListAdapter(this, R.layout.attendance_adapter_view_layout, attendanceRowList);
        attendance_ListView.setAdapter(attendanceListAdapter);
    }
}