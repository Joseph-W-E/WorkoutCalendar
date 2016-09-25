package com.androiddev.josephelliott.workoutcalendar.Activities.CustomWorkout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.androiddev.josephelliott.workoutcalendar.Activities.CustomWorkout.CustomWorkoutActivity;
import com.androiddev.josephelliott.workoutcalendar.Activities.Presets.PresetsNewPresetDialog;
import com.androiddev.josephelliott.workoutcalendar.ObjectData.Preset;
import com.androiddev.josephelliott.workoutcalendar.ObjectData.PresetsDataSource;
import com.androiddev.josephelliott.workoutcalendar.R;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jellio on 9/23/16.
 */

public class CustomWorkoutDialogLoadFromPresets extends DialogFragment {

    private RadioGroup radioGroup;
    private ArrayList<RadioButton> radioButtons;
    private ArrayList<Preset> presets;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Initialize the basics of the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Get the view (so we can use findViewById)
        View view = inflater.inflate(R.layout.custom_workout_dialog_load_from_presets_outer, null);

        radioGroup = (RadioGroup)
                view.findViewById(R.id.custom_workout_dialog_load_from_presets_radio_group);

        PresetsDataSource dataSource = new PresetsDataSource(getActivity());
        dataSource.open();
        presets = dataSource.getPresets();
        dataSource.close();

        for (int i = 0; i < presets.size(); i++) {
            RadioButton button = new RadioButton(getActivity());
            button.setId(i);
            button.setText(presets.get(i).getTitle());
            radioGroup.addView(button);
        }

        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        passDataBack();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CustomWorkoutDialogLoadFromPresets.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    private void passDataBack() {
        CustomWorkoutActivity customWorkoutActivity = (CustomWorkoutActivity) getActivity();
        customWorkoutActivity.loadFromPreset(presets.get(radioGroup.getCheckedRadioButtonId()));
    }

}
