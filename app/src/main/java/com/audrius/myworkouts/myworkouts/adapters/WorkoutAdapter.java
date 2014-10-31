package com.audrius.myworkouts.myworkouts.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.audrius.myworkouts.myworkouts.R;
import com.audrius.myworkouts.myworkouts.models.Exercise;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class WorkoutAdapter extends BaseExpandableListAdapter {
    public Activity activity;
    public ArrayList<Exercise> exercise;
    public ArrayList<Object> set;
    public ArrayList<String> tempChild;
    public LayoutInflater minflater;

    public void setInflater(LayoutInflater mInflater, Activity act) {
        this.minflater = mInflater;
        activity = act;
    }

    public WorkoutAdapter(ArrayList<Exercise> exercise, ArrayList<Object> set) {
        this.exercise = exercise;
        this.set = set;
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return exercise.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return ((ArrayList<String>) set.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i2) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i2) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = minflater.inflate(R.layout.grouprow, null);
        }
        ((CheckedTextView) convertView).setText(exercise.get(groupPosition).toString());
        ((CheckedTextView) convertView).setChecked(isExpanded);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        tempChild = (ArrayList<String>) set.get(groupPosition);

        if (convertView == null) {
            convertView = minflater.inflate(R.layout.childrow, null);
        }
        TextView text = (TextView) convertView.findViewById(R.id.textView2);
        text.setText(tempChild.get(childPosition));
        return convertView;
    }
    @Override
    public boolean isChildSelectable(int i, int i2) {
        return false;
    }

}
