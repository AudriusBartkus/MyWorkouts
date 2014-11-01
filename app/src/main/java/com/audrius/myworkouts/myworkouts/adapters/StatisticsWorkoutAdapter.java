package com.audrius.myworkouts.myworkouts.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.audrius.myworkouts.myworkouts.R;
import com.audrius.myworkouts.myworkouts.models.Exercise;
import com.audrius.myworkouts.myworkouts.models.Set;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class StatisticsWorkoutAdapter extends BaseExpandableListAdapter {
    public Activity activity;
    public ArrayList<Exercise> exercise;
    public ArrayList<Object> set;
    public ArrayList<Set> tempChild;
    public LayoutInflater minflater;
    public Context context;

    public void setInflater(LayoutInflater mInflater, Activity act) {
        this.minflater = mInflater;
        activity = act;
    }

    public StatisticsWorkoutAdapter(ArrayList<Exercise> exercise, ArrayList<Object> set, Context context) {
        this.exercise = exercise;
        this.context = context;
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = minflater.inflate(R.layout.grouprow, null);
        }
        ((CheckedTextView) convertView).setText(exercise.get(groupPosition).toString());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        tempChild = (ArrayList<Set>) set.get(groupPosition);
        String childText = tempChild.get(childPosition).toString();
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = minflater.inflate(R.layout.statistics_childrow, null);
            childViewHolder = new ChildViewHolder();
            childViewHolder.mChildText = (TextView)convertView.findViewById(R.id.textView2);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.mChildText.setText(childText);

        return convertView;
    }
    @Override
    public boolean isChildSelectable(int i, int i2) {
        return false;
    }

    public final class ChildViewHolder{
        TextView mChildText;
    }

}
