package com.example.studentevaluationproject;

import android.app.ProgressDialog;
import android.os.CountDownTimer;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfile {
    private static UserProfile instance = new UserProfile();

    public static UserProfile getInstance() {
        return instance;
    }

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    private String uid, roll_no, name, gender, phone_no, email, branch, semester, batch, post, class_code;

    public UserProfile() {
        this.uid = "";
        this.roll_no = "";
        this.name = "";
        this.gender = "";
        this.phone_no = "";
        this.email = "";
        this.branch = "";
        this.semester = "";
        this.batch = "";
        this.post = "";
        this.class_code = "";
    }

    public UserProfile(String u_uid, String u_roll, String u_name, String u_gender, String u_phone, String u_email, String u_branch, String u_semester, String u_batch, String u_post) {
        this.uid = u_uid;
        this.roll_no = u_roll;
        this.name = u_name;
        this.gender = u_gender;
        this.phone_no = u_phone;
        this.email = u_email;
        this.branch = u_branch;
        this.semester = u_semester;
        this.batch = u_batch;
        this.post = u_post;
        class_code = classCodeGenerator();
    }

    public void addUserProfile(String u_uid, String u_roll, String u_name, String u_gender, String u_phone, String u_email, String u_branch, String u_semester, String u_batch, String u_post) {
        this.uid = u_uid;
        this.roll_no = u_roll;
        this.name = u_name;
        this.gender = u_gender;
        this.phone_no = u_phone;
        this.email = u_email;
        this.branch = u_branch;
        this.semester = u_semester;
        this.batch = u_batch;
        this.post = u_post;
        this.class_code = classCodeGenerator();
    }
    
    public void addUserToFirebase() {

        DatabaseReference thisUser;

        if(post.equals("student")) {
            thisUser = mDatabase.child("class").child(class_code).child("student").child(roll_no);
        }
        else {
            // add the faculty to the concerned class as the class advisor

            DatabaseReference thisUser2 = mDatabase.child("class").child(class_code).child("class advisor").child(roll_no);
            thisUser2.child("name").setValue(name);
            thisUser2.child("post").setValue(post);
            thisUser2.child("roll_no").setValue(roll_no);
            thisUser2.child("email").setValue(email);
            thisUser2.child("phone").setValue(phone_no);
            thisUser2.child("gender").setValue(gender);
            thisUser2.child("branch").setValue(branch);
            thisUser2.child("semester").setValue(semester);
            thisUser2.child("batch").setValue(batch);
            thisUser2.child("class_code").setValue(class_code);

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
        thisUser.child("class_code").setValue(class_code);

        // adding all the users to the "all_users"
        thisUser = mDatabase.child("all_users").child(uid);

        thisUser.child("class_code").setValue(class_code);
        thisUser.child("post").setValue(post);
        thisUser.child("roll_no").setValue(roll_no);
    }

    private String classCodeGenerator() {
        return branch + batch;
    }

    public String getName() {
        return name;
    }

    public String getRoll_no() {
        return roll_no;
    }

    public String getBranch() {
        return branch;
    }

    public String getGender() {
        return this.gender;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public String getEmail() {
        return email;
    }

    public String getSemester() {
        return semester;
    }

    public String getBatch() {
        return batch;
    }

    public String getPost() {
        return post;
    }

    public String getClass_code() {
        return class_code;
    }
}