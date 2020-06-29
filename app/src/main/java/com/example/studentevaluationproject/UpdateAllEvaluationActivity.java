package com.example.studentevaluationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class UpdateAllEvaluationActivity extends AppCompatActivity {

    Button addEvaluationToFirebaseButton;
    EditText evaluation_copied_data_EditText;

    DatabaseReference rootRef, currentRef;
    UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_all_evaluation);

        getSupportActionBar().setTitle("All at once Evaluation");

        rootRef = FirebaseDatabase.getInstance().getReference();

        addEvaluationToFirebaseButton = (Button) findViewById(R.id.addEvaluationToFirebaseButton);
        evaluation_copied_data_EditText = (EditText) findViewById(R.id.evaluation_copied_data_EditText);

        addEvaluationToFirebaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(evaluation_copied_data_EditText.getText().toString().isEmpty()) {
                    Toast.makeText(UpdateAllEvaluationActivity.this, "Please paste the CSV data", Toast.LENGTH_SHORT).show();
                }
                else {

                    int row = 0, col = 0;
                    ArrayList<String> sub_codes = new ArrayList<>();

                    // Assuming the format of CSV data (the first column) be like:
                    // | ROLL NO | NAME | SUB_1 CODE | SUB_2 CODE | ... | SUB_N CODE |
                    // Columns are comma separated and rows are '\n' separated

                    String input_data = evaluation_copied_data_EditText.getText().toString();

                    currentRef = rootRef.child("class").child(userProfile.getInstance().getClass_code()).child("evaluation");

                    // Using StringTokenizer to tokenize the lines
                    StringTokenizer rowToken = new StringTokenizer(input_data, "\n");

                    while(rowToken.hasMoreElements()) {

                        row++;
                        col = 0;

                        // Using StringTokenizer to tokenize the columns in a row
                        StringTokenizer colToken = new StringTokenizer(rowToken.nextToken(), ",");

                        if(row == 1) {

                            while(colToken.hasMoreElements()) {

                                col++;

                                if(col == 1 || col == 2){
                                    colToken.nextToken();
                                    continue;
                                }

                                sub_codes.add(colToken.nextToken().trim());
                            }
                        }
                        else {

                            // Firebase work starts here
                            String roll_no = "", name = "";

                            while(colToken.hasMoreElements()) {

                                col++;

                                if(col == 1) {
                                    roll_no = colToken.nextToken();
                                }
                                else if(col == 2) {
                                    name = colToken.nextToken();
                                    currentRef.child(roll_no).child("name").setValue(name.trim());
                                }
                                else {
                                    currentRef.child(roll_no).child(sub_codes.get(col-3)).setValue(colToken.nextToken().trim());
                                }
                            }
                        }
                    }
                }
                evaluation_copied_data_EditText.setText("");
                Toast.makeText(UpdateAllEvaluationActivity.this, "Evaluation updated", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
