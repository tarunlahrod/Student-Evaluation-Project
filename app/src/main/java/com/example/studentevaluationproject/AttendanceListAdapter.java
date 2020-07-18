package com.example.studentevaluationproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AttendanceListAdapter extends ArrayAdapter<StudentAttendanceRow> {

    private Context mContext;
    private int mResource;

    public AttendanceListAdapter(Context context, int resource, ArrayList<StudentAttendanceRow> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // get attendance row information
        String name = getItem(position).getName();
        String roll = getItem(position).getRoll();
        String attendance = getItem(position).getAttendance();
        String percentage = getItem(position).getPercentage();

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);

        TextView nameTV = (TextView) convertView.findViewById(R.id.name_attendance_adapter_view_layout);
        TextView rollTV = (TextView) convertView.findViewById(R.id.roll_attendance_adapter_view_layout);
        TextView attendanceTV = (TextView) convertView.findViewById(R.id.attendance_attendance_adapter_view_layout);
        TextView percentageTV = (TextView) convertView.findViewById(R.id.percentage_attendance_adapter_view_layout);

        nameTV.setText(name);
        rollTV.setText(roll);
        attendanceTV.setText(attendance);
        percentageTV.setText(percentage);

        return convertView;
    }
}
