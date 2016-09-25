package com.androiddev.josephelliott.workoutcalendar.Activities.Presets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androiddev.josephelliott.workoutcalendar.ObjectData.Preset;
import com.androiddev.josephelliott.workoutcalendar.R;

import java.util.ArrayList;

/**
 * Created by jellio on 9/22/16.
 */

public class PresetsListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Preset> presets;

    public PresetsListAdapter(Context context, ArrayList<Preset> presets) {
        this.context = context;
        this.presets = presets;
        layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return presets.size();
    }

    @Override
    public Object getItem(int position) {
        return presets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Preset preset = presets.get(position);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.presets_list_view_item, parent, false);
        }

        TextView txtTitle       = (TextView) convertView.findViewById(R.id.txt_presets_inner_title);
        TextView txtLocation    = (TextView) convertView.findViewById(R.id.txt_presets_inner_location);
        TextView txtDescription = (TextView) convertView.findViewById(R.id.txt_presets_inner_description);

        txtTitle.setText(preset.getTitle());
        txtLocation.setText(preset.getLocation());
        txtDescription.setText(preset.getDescription());

        return convertView;
    }
}
