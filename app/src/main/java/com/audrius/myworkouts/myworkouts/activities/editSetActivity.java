package com.audrius.myworkouts.myworkouts.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.audrius.myworkouts.myworkouts.R;
import com.audrius.myworkouts.myworkouts.models.Set;


public class editSetActivity extends Activity {
    private EditText weightField;
    private EditText repsField;
    private Set set;
    private int groupPosition;
    private int childPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_set);
        weightField = (EditText)findViewById(R.id.editText);
        repsField = (EditText)findViewById(R.id.editText2);
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        set = (Set)getIntent().getSerializableExtra("set");
        groupPosition = extras.getInt("groupPosition");
        childPosition = extras.getInt("childPosition");
        weightField.setText(String.valueOf(set.getWeight()));
        repsField.setText(String.valueOf(set.getReps()));
    }

    public void save(View view){

        Set newSet = new Set();
        EditText editText = (EditText)findViewById(R.id.editText);
        newSet.setWeight(Integer.parseInt(editText.getText().toString()));
        editText = (EditText)findViewById(R.id.editText2);
        newSet.setReps(Integer.parseInt(editText.getText().toString()));
        Intent intent = new Intent();
        intent.putExtra("set", newSet);
        intent.putExtra("groupPosition", groupPosition);
        intent.putExtra("childPosition", childPosition);

        setResult(RESULT_OK, intent);
        Log.d("cia", "------");

        finish();
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
