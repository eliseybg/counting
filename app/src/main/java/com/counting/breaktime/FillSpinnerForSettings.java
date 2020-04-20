package com.counting.breaktime;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class FillSpinnerForSettings {
    public static String[] timeLengthForFastCount = {"15 sec", "30 sec", "1 min"};
    public static String[] typesOfSpeedForFastCount = {"elementary", "middle", "master", "professional"};
    public static String[] numberRangeForFastCount = {"5 - 15", "5 - 25", "5 - 35", "5 - 50"};
    public static String[] timeLengthForSignSelection = {"1 min", "2 min", "3 min"};
    public static String[] numberOfComparisonsSignSelection = {"1 - 3", "1 - 5", "1 - 7",};
    public static String[] numberRangeForSignSelection = {"5 - 15", "5 - 25", "5 - 35", "5 - 50"};
    public static String[] language = {"Русский", "English"};

    public Spinner fillSpinner(String name, Context context, Spinner spinner) {
        String[] array = new String[6];
        switch (name) {
            case "timeLengthForFastCount":
                array = timeLengthForFastCount;
                break;
            case "typesOfSpeedForFastCount":
                array = typesOfSpeedForFastCount;
                break;
            case "numberRangeForFastCount":
                array = numberRangeForFastCount;
                break;
            case "timeLengthForSignSelection":
                array = timeLengthForSignSelection;
                break;
            case "numberOfComparisonsSignSelection":
                array = numberOfComparisonsSignSelection;
                break;
            case "numberRangeForSignSelection":
                array = numberRangeForSignSelection;
                break;
                case "language":
                array = language;
                break;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return spinner;
    }
}
