package com.example.studentevaluationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.StringTokenizer;

public class UpdateAttendanceActivity extends AppCompatActivity {

    private EditText total_lecture_count, attendance_copied_csv_data_EditText;
    private Button addAttendanceToFirebaseButton;

    private String total_lecture_count_value, csv_data;

    DatabaseReference rootRef, currentRef;
    UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_attendance);
        getSupportActionBar().setTitle("Update Attendance");

        total_lecture_count = (EditText) findViewById(R.id.total_lecture_count);
        attendance_copied_csv_data_EditText = (EditText) findViewById(R.id.attendance_copied_csv_data_EditText);
        addAttendanceToFirebaseButton = (Button) findViewById(R.id.addAttendanceToFirebaseButton);

        rootRef = FirebaseDatabase.getInstance().getReference();

        addAttendanceToFirebaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // check for input data validation
                if(validate_input_data()) {

                    // add attendance to firebase
                    currentRef = rootRef.child("class").child(userProfile.getInstance().getClass_code()).child("attendance");

                    // add the total number of lectures as a separate value in attendance directory
                    currentRef.child("total_lecture_count").setValue(total_lecture_count_value);

                    // using StringTokenizer to tokenize lines (or rows)
                    StringTokenizer rowToken = new StringTokenizer(csv_data, "\n");

                    int row = 0, col = 0;

                    while(rowToken.hasMoreElements()) {

                        row++;
                        col = 0;

                        // using StringTokenizer to tokenize values (or cols)
                        StringTokenizer colToken = new StringTokenizer(rowToken.nextToken(), ",");

                        // skip the entries of the first row (cuz they're just headers)
                        if(row == 1) {

                            while(colToken.hasMoreElements()) {
                                col++;
                                colToken.nextToken();
                            }
                        }
                        else {
                            // for rest of the rows
                            String name = "", roll = "", attendance = "";

                            while(colToken.hasMoreElements()) {

                                col++;

                                if(col == 1) {
                                    roll = colToken.nextToken().trim();
                                }
                                else if(col == 2) {
                                    name = colToken.nextToken().trim();
                                }
                                else if(col == 3) {
                                    attendance = colToken.nextToken().trim();

                                    // add this student's attendance to firebase
                                    currentRef.child(roll).child("name").setValue(name);
                                    currentRef.child(roll).child("attendance").setValue(attendance);
                                }
                            }
                        }
                    }
                    attendance_copied_csv_data_EditText.setText("");
                    generate_toast("Attendance updated successfully");
                    finish();
                }


            }
        });
    }

    public void generate_toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public boolean validate_input_data() {

        // check total number of lectures
        if(total_lecture_count.getText().toString().isEmpty()) {
            generate_toast("Enter total lecture count");
            return false;
        }
        else {
            total_lecture_count_value = total_lecture_count.getText().toString();

            if(Integer.parseInt(total_lecture_count_value) < 1) {
                generate_toast("Total lecture count must be greater than 0");
                return false;
            }
        }

        if(attendance_copied_csv_data_EditText.getText().toString().isEmpty()) {
            generate_toast("Enter the CSV Attendance data");
            return false;
        }
        else {
            csv_data = attendance_copied_csv_data_EditText.getText().toString();
        }

        return true;
    }
}