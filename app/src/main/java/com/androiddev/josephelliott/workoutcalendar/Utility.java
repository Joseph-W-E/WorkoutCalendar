package com.androiddev.josephelliott.workoutcalendar;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by Joseph Elliott on 10/6/2015.
 */
public class Utility {
    public static ArrayList<Button> getCellsFromCalendarFragment(View rootView) {
        ArrayList<Button> cellList = new ArrayList<>();
        cellList.add((Button) rootView.findViewById(R.id.cell00));
        cellList.add((Button) rootView.findViewById(R.id.cell01));
        cellList.add((Button) rootView.findViewById(R.id.cell02));
        cellList.add((Button) rootView.findViewById(R.id.cell03));
        cellList.add((Button) rootView.findViewById(R.id.cell04));
        cellList.add((Button) rootView.findViewById(R.id.cell05));
        cellList.add((Button) rootView.findViewById(R.id.cell06));

        cellList.add((Button) rootView.findViewById(R.id.cell10));
        cellList.add((Button) rootView.findViewById(R.id.cell11));
        cellList.add((Button) rootView.findViewById(R.id.cell12));
        cellList.add((Button) rootView.findViewById(R.id.cell13));
        cellList.add((Button) rootView.findViewById(R.id.cell14));
        cellList.add((Button) rootView.findViewById(R.id.cell15));
        cellList.add((Button) rootView.findViewById(R.id.cell16));

        cellList.add((Button) rootView.findViewById(R.id.cell20));
        cellList.add((Button) rootView.findViewById(R.id.cell21));
        cellList.add((Button) rootView.findViewById(R.id.cell22));
        cellList.add((Button) rootView.findViewById(R.id.cell23));
        cellList.add((Button) rootView.findViewById(R.id.cell24));
        cellList.add((Button) rootView.findViewById(R.id.cell25));
        cellList.add((Button) rootView.findViewById(R.id.cell26));

        cellList.add((Button) rootView.findViewById(R.id.cell30));
        cellList.add((Button) rootView.findViewById(R.id.cell31));
        cellList.add((Button) rootView.findViewById(R.id.cell32));
        cellList.add((Button) rootView.findViewById(R.id.cell33));
        cellList.add((Button) rootView.findViewById(R.id.cell34));
        cellList.add((Button) rootView.findViewById(R.id.cell35));
        cellList.add((Button) rootView.findViewById(R.id.cell36));

        cellList.add((Button) rootView.findViewById(R.id.cell40));
        cellList.add((Button) rootView.findViewById(R.id.cell41));
        cellList.add((Button) rootView.findViewById(R.id.cell42));
        cellList.add((Button) rootView.findViewById(R.id.cell43));
        cellList.add((Button) rootView.findViewById(R.id.cell44));
        cellList.add((Button) rootView.findViewById(R.id.cell45));
        cellList.add((Button) rootView.findViewById(R.id.cell46));

        cellList.add((Button) rootView.findViewById(R.id.cell50));
        cellList.add((Button) rootView.findViewById(R.id.cell51));
        cellList.add((Button) rootView.findViewById(R.id.cell52));
        cellList.add((Button) rootView.findViewById(R.id.cell53));
        cellList.add((Button) rootView.findViewById(R.id.cell54));
        cellList.add((Button) rootView.findViewById(R.id.cell55));
        cellList.add((Button) rootView.findViewById(R.id.cell56));

        return cellList;
    }
}
