package com.example.studentevaluationproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;

public class UpdateAttendanceActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText total_lecture_count, attendance_copied_csv_data_EditText;
    private Button addAttendanceToFirebaseButton, pick_date_button;

    private String total_lecture_count_value, csv_data, selectedDateString;

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
        pick_date_button = (Button) findViewById(R.id.pick_date_button);

        selectedDateString = pick_date_button.getText().toString();
        Log.i("selectedDateString", selectedDateString);

        // Open DatePickerFragment when "Pick Date" Button is clicked
        pick_date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Date Picker");
            }
        });

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

                    // add the last update date in the attendance directory
                    currentRef.child("last_update").setValue(selectedDateString);

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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        selectedDateString = format.format(calendar.getTime());
        pick_date_button.setText(selectedDateString);
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

        if(selectedDateString.equals("Pick Date")) {
            generate_toast("Select last update date");
            return false;
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