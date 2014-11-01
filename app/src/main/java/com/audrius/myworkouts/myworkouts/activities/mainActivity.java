package com.audrius.myworkouts.myworkouts.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;

import com.audrius.myworkouts.myworkouts.R;


public class mainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    public void openNewWorkout(View view){
        Intent intent = new Intent(this, newWorkoutActivity.class);
        startActivity(intent);
    }

    public void openStartWorkout(View view){
        Intent intent = new Intent(this, selectWorkoutActivity.class);
        startActivity(intent);
    }

    public void openStatistics(View view){
        Intent intent = new Intent(this, statisticsActivity.class);
        startActivity(intent);
    }
}