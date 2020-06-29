package com.example.studentevaluationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.StringTokenizer;

public class UpdateAllEvaluationActivity extends AppCompatActivity {

    Button addEvaluationToFirebaseButton;
    EditText evaluation_copied_data_EditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_all_evaluation);

        getSupportActionBar().setTitle("All at once Evaluation");

        addEvaluationToFirebaseButton = (Button) findViewById(R.id.addEvaluationToFirebaseButton);
        evaluation_copied_data_EditText = (EditText) findViewById(R.id.evaluation_copied_data_EditText);

        addEvaluationToFirebaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg = evaluation_copied_data_EditText.getText().toString();
                StringTokenizer strTok = new StringTokenizer(msg, "\n");

                int line = 0, word = 0;

                while(strTok.hasMoreElements()) {

                    line++;
                    String thisLine = strTok.nextToken();
                    StringTokenizer wordTok = new StringTokenizer(thisLine, " ");

                    while(wordTok.hasMoreElements()) {
                        word++;
                        String output = "[Line: " + line + ", word: " + word + "] ";
                        output = output + wordTok.nextToken();

                        Log.i("Tokens", output);
                    }
                    word = 0;
                }
            }
        });
    }
}