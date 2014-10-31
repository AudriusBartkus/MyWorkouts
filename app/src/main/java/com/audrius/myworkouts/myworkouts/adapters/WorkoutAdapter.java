package com.audrius.myworkouts.myworkouts.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.audrius.myworkouts.myworkouts.R;
import com.audrius.myworkouts.myworkouts.models.Exercise;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("unchecked")
public class WorkoutAdapter extends BaseExpandableListAdapter {
    public Activity activity;
    public ArrayList<Exercise> exercise;
    public ArrayList<Object> set;
    public ArrayList<String> tempChild;
    public LayoutInflater minflater;
    public HashMap<Integer, boolean[]> mChildCheckStates;

    public void setInflater(LayoutInflater mInflater, Activity act) {
        this.minflater = mInflater;
        activity = act;
    }

    public WorkoutAdapter(ArrayList<Exercise> exercise, ArrayList<Object> set) {
        this.exercise = exercise;
        this.set = set;
        mChildCheckStates = new HashMap<Integer, boolean[]>();
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
        ((CheckedTextView) convertView).setChecked(isExpanded);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        tempChild = (ArrayList<String>) set.get(groupPosition);
        final int mGroupPosition = groupPosition;
        String childText = tempChild.get(childPosition);
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = minflater.inflate(R.layout.childrow, null);
            childViewHolder = new ChildViewHolder();
            childViewHolder.mCheckBox = (CheckBox) convertView.findViewById(R.id.checkBox);
            childViewHolder.mChildText = (TextView)convertView.findViewById(R.id.textView2);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.mChildText.setText(childText);
        childViewHolder.mCheckBox.setOnCheckedChangeListener(null);
        if (mChildCheckStates.containsKey(mGroupPosition)) {
            boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
            childViewHolder.mCheckBox.setChecked(getChecked[childPosition]);
        } else {
            boolean getChecked[] = new boolean[getChildrenCount(mGroupPosition)];
            mChildCheckStates.put(mGroupPosition, getChecked);
            childViewHolder.mCheckBox.setChecked(false);
        }
        childViewHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
                    getChecked[childPosition] = true;
                    mChildCheckStates.put(mGroupPosition, getChecked);
                } else {
                    boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
                    getChecked[childPosition] = false;
                    mChildCheckStates.put(mGroupPosition, getChecked);
                }
            }
        });
        return convertView;
    }
    @Override
    public boolean isChildSelectable(int i, int i2) {
        return false;
    }

    public final class ChildViewHolder {
        TextView mChildText;
        CheckBox mCheckBox;
    }

}
