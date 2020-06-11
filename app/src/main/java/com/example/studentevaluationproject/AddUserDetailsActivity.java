package com.example.studentevaluationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class AddUserDetailsActivity extends AppCompatActivity {

    UserProfile user = new UserProfile();

    final String thisUserPost = "random string";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_details);

        if(!user.isPostSelected()) {

            Intent intent = new Intent(AddUserDetailsActivity.this, StudentOrFacultyActivity.class);
            intent.putExtra("postValue", thisUserPost);
            startActivity(intent);

        }



        Toast.makeText(this, "Inside user details activity", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!thisUserPost.equals("random string")) {
            Toast.makeText(this, "Post: " + thisUserPost, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Jugaad unsuccessful", Toast.LENGTH_SHORT).show();
        }
    }
}