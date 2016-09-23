package com.androiddev.josephelliott.workoutcalendar.Activities.Presets;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.androiddev.josephelliott.workoutcalendar.Activities.Help.HelpActivity;
import com.androiddev.josephelliott.workoutcalendar.Activities.Settings.SettingsActivity;
import com.androiddev.josephelliott.workoutcalendar.ObjectData.Workout;
import com.androiddev.josephelliott.workoutcalendar.ObjectData.WorkoutDataSource;
import com.androiddev.josephelliott.workoutcalendar.R;

import java.util.ArrayList;

/**
 * Created by jellio on 9/21/16.
 */
public class PresetsActivity extends Activity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presets);

        ArrayList<Workout> workouts = new ArrayList<>();
        workouts.add(new Workout());

        listView = (ListView) findViewById(R.id.presets_list_view);
        listView.setAdapter(new PresetsListAdapter(this, workouts));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_default_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.menu_default_help) {
            startActivity(new Intent(this, HelpActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
