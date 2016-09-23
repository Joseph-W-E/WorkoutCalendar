package com.androiddev.josephelliott.workoutcalendar.Activities.Presets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androiddev.josephelliott.workoutcalendar.ObjectData.Workout;
import com.androiddev.josephelliott.workoutcalendar.R;

import java.util.ArrayList;

/**
 * Created by jellio on 9/22/16.
 */

public class PresetsListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Workout> workouts;

    public PresetsListAdapter(Context context, ArrayList<Workout> workouts) {
        this.context = context;
        this.workouts = workouts;
        layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return workouts.size();
    }

    @Override
    public Object getItem(int position) {
        return workouts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Workout currentWorkout = workouts.get(position);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.preset_inner_workout, parent, false);
        }

        TextView txtTitle       = (TextView) convertView.findViewById(R.id.txt_presets_inner_title);
        TextView txtLocation    = (TextView) convertView.findViewById(R.id.txt_presets_inner_location);
        TextView txtDescription = (TextView) convertView.findViewById(R.id.txt_presets_inner_description);
        TextView txtFrequency   = (TextView) convertView.findViewById(R.id.txt_presets_inner_frequency);
        TextView txtExpires     = (TextView) convertView.findViewById(R.id.txt_presets_inner_expires);

        txtTitle.setText(currentWorkout.getTitle());
        txtLocation.setText(currentWorkout.getLocation());
        txtDescription.setText(currentWorkout.getDescription());

        return convertView;
    }
}
