package com.audrius.myworkouts.myworkouts.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.audrius.myworkouts.myworkouts.R;
import com.audrius.myworkouts.myworkouts.SwipeDismissListViewTouchListener;
import com.audrius.myworkouts.myworkouts.db.WorkoutDataSource;
import com.audrius.myworkouts.myworkouts.models.Workout;



import java.util.ArrayList;


public class selectWorkoutActivity extends ActionBarActivity {
    private WorkoutDataSource workDatasource;
    private ArrayList<Workout> values;
    private ArrayAdapter<Workout> adapter;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_workout);
        workDatasource = new WorkoutDataSource(this);
        workDatasource.open();
        list = (ListView)findViewById(R.id.workoutsListView);
        values = workDatasource.getWorkoutsWithoutDate();
        adapter = new ArrayAdapter<Workout>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        list.setAdapter(adapter);

        SwipeDismissListViewTouchListener touchListener = new SwipeDismissListViewTouchListener(list, new SwipeDismissListViewTouchListener.DismissCallbacks() {
            @Override
            public boolean canDismiss(int position) {
                return true;
            }
            @Override
            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                for (int position : reverseSortedPositions) {
                    Workout workout = values.get(position);
                    workDatasource.deleteWorkout(workout);
                }
                adapter.notifyDataSetChanged();
                onResume();
            }
        });
        list.setOnTouchListener(touchListener);
        list.setOnScrollListener(touchListener.makeScrollListener());

        list.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startWorkout((Workout)parent.getAdapter().getItem(position));
            }
        });

    }

    private void startWorkout(Workout workout){
        Intent intent = new Intent(this, startWorkoutActivity.class);
        intent.putExtra("workout", workout);
        startActivity(intent);
    }


    @Override
    protected void onResume(){

        workDatasource.open();
        values = workDatasource.getWorkoutsWithoutDate();
        list = (ListView)findViewById(R.id.workoutsListView);
        adapter = new ArrayAdapter<Workout>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
        list.setAdapter(adapter);
        super.onResume();
    }

    @Override
    protected void onPause() {
        workDatasource.close();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start_workout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
