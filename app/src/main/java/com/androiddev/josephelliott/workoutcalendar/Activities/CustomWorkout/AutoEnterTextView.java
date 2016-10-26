package com.androiddev.josephelliott.workoutcalendar.Activities.CustomWorkout;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;
import android.widget.Filterable;
import android.widget.ListAdapter;

/**
 * Created by jellio on 10/26/16.
 * This is a slight extension of the existing AutoCompleteTextView.
 * The purpose of this class is to include a way to access the top element
 * in the drop down menu.
 */

class AutoEnterTextView extends AutoCompleteTextView {

    private Object dropDownTopElement;

    public AutoEnterTextView(Context context) {
        super(context);
    }

    public AutoEnterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoEnterTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public <T extends ListAdapter & Filterable> void setAdapter(T adapter) {
        super.setAdapter(adapter);
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                if (getDropDownAnchor() == 0) return;
                dropDownTopElement = getAdapter().getItem(0);
            }
        });
    }

    public String getFirstElementOfDropDownList() {
        return dropDownTopElement == null ? "" : dropDownTopElement.toString();
    }

}
