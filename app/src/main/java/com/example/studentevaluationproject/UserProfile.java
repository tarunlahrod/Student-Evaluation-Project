package com.example.studentevaluationproject;

import android.app.ProgressDialog;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfile {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    private String roll_no, name, gender, phone_no, email, branch, semester, batch, post, classCode, classAdvisorOf;

    public UserProfile() {
        this.roll_no = "";
        this.name = "";
        this.gender = "";
        this.phone_no = "";
        this.email = "";
        this.branch = "";
        this.semester = "";
        this.batch = "";
        this.post = "";
    }

    public UserProfile(String u_roll, String u_name, String u_gender, String u_phone, String u_email, String u_branch, String u_semester, String u_batch, String u_post) {
        this.roll_no = u_roll;
        this.name = u_name;
        this.gender = u_gender;
        this.phone_no = u_phone;
        this.email = u_email;
        this.branch = u_branch;
        this.semester = u_semester;
        this.batch = u_batch;
        this.post = u_post;
        classCode = classCodeGenerator();
    }

    public void addUserToFirebase() {

        DatabaseReference thisUser;

        if(post.equals("student")) {
            thisUser = mDatabase.child("class").child(classCode).child("student").child(roll_no);
        }
        else {
            // add the faculty to the concerned class as the class advisor
            DatabaseReference thisUser2 = mDatabase.child("class").child(classCode).child("class advisor").child(roll_no);
            thisUser2.child("name").setValue(name);
            thisUser2.child("post").setValue(post);
            thisUser2.child("roll_no").setValue(roll_no);
            thisUser2.child("email").setValue(email);
            thisUser2.child("phone").setValue(phone_no);
            thisUser2.child("gender").setValue(gender);
            thisUser2.child("branch").setValue(branch);
            thisUser2.child("semester").setValue(semester);
            thisUser2.child("batch").setValue(batch);
            thisUser2.child("class_code").setValue(classCode);

            // add the faculty to the faculty
            thisUser = mDatabase.child("faculty").child(roll_no);
        }
        thisUser.child("name").setValue(name);
        thisUser.child("post").setValue(post);
        thisUser.child("roll_no").setValue(roll_no);
        thisUser.child("email").setValue(email);
        thisUser.child("phone").setValue(phone_no);
        thisUser.child("gender").setValue(gender);
        thisUser.child("branch").setValue(branch);
        thisUser.child("semester").setValue(semester);
        thisUser.child("batch").setValue(batch);
        thisUser.child("class_code").setValue(classCode);
    }

    private String classCodeGenerator() {
        String code = branch + batch;
        return code;
    }
}