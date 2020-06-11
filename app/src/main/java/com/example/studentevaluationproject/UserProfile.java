package com.example.studentevaluationproject;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfile {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    private String roll_no, name, gender, phone_no, email, branch, semester, classAdvisorOf, batch;
    private boolean isStudent, postSelected;

    public UserProfile() {
        this.roll_no = "";
        this.name = "";
        this.gender = "";
        this.phone_no = "";
        this.email = "";
        this.branch = "";
        this.semester = "";
        this.classAdvisorOf = "";
        this.batch = "";
        this.isStudent = true;
        this.postSelected = false;
    }

    public void addStudent(String u_roll, String u_name, String u_gender, String u_phone, String u_email, String u_branch, String u_batch, String u_semester) {
        this.roll_no = u_roll;
        this.name = u_name;
        this.gender = u_gender;
        this.phone_no = u_phone;
        this.email = u_email;
        this.branch = u_branch;
        this.semester = u_semester;
        this.batch = u_batch;
        this.isStudent = true;
        this.postSelected = true;
    }

    public void addFaculty(String u_roll, String u_name, String u_gender, String u_phone, String u_email, String u_classAdvisorOf) {
        this.roll_no = u_roll;
        this.name = u_name;
        this.gender = u_gender;
        this.phone_no = u_phone;
        this.email = u_email;
        this.classAdvisorOf = u_classAdvisorOf;
        this.isStudent = false;
        this.postSelected = true;
    }

    public void addStudentToFirebase() {
         DatabaseReference thisUser = mDatabase.child("student").child(roll_no);

         thisUser.child("name").setValue(name);
         thisUser.child("roll_no").setValue(roll_no);
         thisUser.child("email").child(email);
         thisUser.child("phone").child(phone_no);
         thisUser.child("gender").child(gender);
         thisUser.child("branch").child(branch);
         thisUser.child("semester").child(semester);
         thisUser.child("batch").child(batch);
         thisUser.child("class_code").child(classCodeGenerator());
    }

    public void addFacultyToFirebase() {
        DatabaseReference thisUser = mDatabase.child("faculty").child(roll_no);

        thisUser.child("name").setValue(name);
        thisUser.child("roll_no").setValue(roll_no);
        thisUser.child("email").child(email);
        thisUser.child("phone").child(phone_no);
        thisUser.child("gender").child(gender);
        thisUser.child("classAdvisorOf").child(classAdvisorOf);
    }

    private String classCodeGenerator() {
        String code = branch + batch;
        return code;
    }

    public boolean isPostSelected() {
        return postSelected;
    }
}