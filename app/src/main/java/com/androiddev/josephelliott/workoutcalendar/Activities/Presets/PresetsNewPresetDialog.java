package com.androiddev.josephelliott.workoutcalendar.Activities.Presets;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.androiddev.josephelliott.workoutcalendar.ObjectData.Preset;
import com.androiddev.josephelliott.workoutcalendar.R;

import java.util.Date;

/**
 * Created by jellio on 9/23/16.
 */

public class PresetsNewPresetDialog extends DialogFragment {

    private EditText etTitle, etLocation, etDescription;
    private EditText etFrequency, etExpires;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Initialize the basics of the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Get the view (so we can use findViewById
        View view = inflater.inflate(R.layout.dialog_new_preset, null);

        etTitle       = (EditText) view.findViewById(R.id.presets_dialog_inner_title);
        etLocation    = (EditText) view.findViewById(R.id.presets_dialog_inner_location);
        etDescription = (EditText) view.findViewById(R.id.presets_dialog_inner_description);
        etFrequency   = (EditText) view.findViewById(R.id.presets_dialog_inner_frequency);
        etExpires     = (EditText) view.findViewById(R.id.presets_dialog_inner_expires);

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
                        PresetsNewPresetDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    private void passDataBack() {
        Preset preset = new Preset();
        preset.setTitle(etTitle.getText().toString());
        preset.setLocation(etLocation.getText().toString());
        preset.setDescription(etDescription.getText().toString());
        preset.setFrequency(etFrequency.getText().toString());
        preset.setExpires(new Date());

        PresetsActivity presetsActivity = (PresetsActivity) getActivity();
        presetsActivity.updateData(preset);
    }

}
