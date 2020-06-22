package com.example.studentevaluationproject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Subject_class {
    private String subject_name, subject_code, subject_short_name;
    private int subject_credits;

    private boolean subjectExists;

    public Subject_class() {

    }

    public Subject_class(String s_name, String s_code, int s_credits, String s_short_name) {
        this.subject_name = s_name;
        this.subject_code = s_code;
        this.subject_credits = s_credits;
        this.subject_short_name = s_short_name;
    }

    public void addSubjectToFirebase() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference thisSub = rootRef.child("class").child(UserProfile.getInstance().getClass_code()).child("subjects").child(subject_code);

        thisSub.child("name").setValue(subject_name);
        thisSub.child("code").setValue(subject_code);
        thisSub.child("credits").setValue(subject_credits);
        thisSub.child("short_name").setValue(subject_short_name);
    }

    public boolean checkFirebaseExists() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference thisSub = rootRef.child("class").child(UserProfile.getInstance().getClass_code()).child("subjects").child(subject_code);

        thisSub.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                subjectExists = dataSnapshot.exists();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        String msg;
        if(subjectExists)
            msg = "true";
        else
            msg = "false";

        Log.i("subjectExists", msg);
        return subjectExists;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getSubject_code() {
        return subject_code;
    }

    public void setSubject_code(String subject_code) {
        this.subject_code = subject_code;
    }

    public int getSubject_credits() {
        return subject_credits;
    }

    public void setSubject_credits(int subject_credits) {
        this.subject_credits = subject_credits;
    }

    public String getSubject_short_name() {
        return subject_short_name;
    }

    public void setSubject_short_name(String subject_short_name) {
        this.subject_short_name = subject_short_name;
    }
}
