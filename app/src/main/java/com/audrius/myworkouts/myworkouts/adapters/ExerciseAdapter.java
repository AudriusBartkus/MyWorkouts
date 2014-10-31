package com.audrius.myworkouts.myworkouts.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.audrius.myworkouts.myworkouts.R;
import com.audrius.myworkouts.myworkouts.models.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ExerciseAdapter extends BaseAdapter{
    private final Activity activity;
    private final List<Exercise> list;


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    public ExerciseAdapter(Activity activity, ArrayList<Exercise> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder view;

        if (rowView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            rowView = inflater.inflate(R.layout.list_exercises, null);

            view = new ViewHolder();
            view.exercise_name = (TextView) rowView.findViewById(R.id.name_entry);
            view.description = (TextView) rowView.findViewById(R.id.description);

            rowView.setTag(view);
        } else {
            view = (ViewHolder) rowView.getTag();
        }

        /** Set data to your Views. */
        Exercise item = list.get(position);
        view.exercise_name.setText(item.getName());
        view.description.setText(String.valueOf(item.getWeight()) + " kg     " + String.valueOf(item.getSets()) + "x" + String.valueOf(item.getReps())+ "     workout id: "+String.valueOf(item.getWorkout_id()) );
        return rowView;
    }

    protected static class ViewHolder {
        protected TextView exercise_name;
        protected TextView description;
    }
}
