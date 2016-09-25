package com.androiddev.josephelliott.workoutcalendar.Activities.Presets;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.androiddev.josephelliott.workoutcalendar.Activities.Help.HelpActivity;
import com.androiddev.josephelliott.workoutcalendar.Activities.Settings.SettingsActivity;
import com.androiddev.josephelliott.workoutcalendar.ObjectData.Preset;
import com.androiddev.josephelliott.workoutcalendar.ObjectData.PresetsDataSource;
import com.androiddev.josephelliott.workoutcalendar.R;
import com.github.clans.fab.FloatingActionButton;

/**
 * Created by jellio on 9/21/16.
 */
public class PresetsActivity extends Activity {

    private Context context;

    private ListView listView;

    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presets_activity);

        context = this;

        PresetsDataSource presetsDataSource = new PresetsDataSource(this);
        presetsDataSource.open();
        listView = (ListView) findViewById(R.id.presets_list_view);
        listView.setAdapter(new PresetsListAdapter(this, presetsDataSource.getPresets()));
        presetsDataSource.close();
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Preset?")
                        .setMessage("Are you sure you want to delete this preset?")
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PresetsDataSource dataSource = new PresetsDataSource(context);
                                dataSource.open();
                                dataSource.deletePreset(dataSource.getPresets().get(position));
                                dataSource.close();
                                updateData();
                            }
                        }).show();

                return true;
            }
        });

        fabAdd = (FloatingActionButton) findViewById(R.id.presets_fab);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDialog();
            }
        });
    }

    public void updateData() {
        PresetsDataSource dataSource = new PresetsDataSource(this);
        dataSource.open();
        listView.setAdapter(new PresetsListAdapter(this, dataSource.getPresets()));
        dataSource.close();
    }

    public void updateData(Preset preset) {
        // Save to the database
        PresetsDataSource dataSource = new PresetsDataSource(this);
        dataSource.open();
        dataSource.createPreset(preset);

        // Update the ListView
        listView.setAdapter(new PresetsListAdapter(this, dataSource.getPresets()));

        // Close the database
        dataSource.close();
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

    private void displayDialog() {
        PresetsNewPresetDialog dialog = new PresetsNewPresetDialog();
        dialog.show(getFragmentManager(), "New Preset");
    }
}
