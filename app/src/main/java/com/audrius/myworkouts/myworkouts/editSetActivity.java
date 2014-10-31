package com.audrius.myworkouts.myworkouts;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.audrius.myworkouts.myworkouts.models.Set;


public class editSetActivity extends Activity {
    private EditText weightField;
    private EditText repsField;
    private Set set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_set);
        weightField = (EditText)findViewById(R.id.editText);
        repsField = (EditText)findViewById(R.id.editText2);
        set = (Set)getIntent().getSerializableExtra("set");
        weightField.setText(String.valueOf(set.getWeight()));
        repsField.setText(String.valueOf(set.getReps()));
    }

    public void save(View view){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_set, menu);
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
