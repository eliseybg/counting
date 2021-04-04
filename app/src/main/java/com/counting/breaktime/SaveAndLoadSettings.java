package com.counting.breaktime;

import android.content.SharedPreferences;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class SaveAndLoadSettings extends AppCompatActivity {
    private static final String PREF_FIRST_OPEN = "first open";
    private static final String PREF_TIME_LENGTH_FAST_COUNT = "time length fast count";
    private static final String PREF_TYPES_OF_SPEED_FAST_COUNT = "types of speed fast count";
    private static final String PREF_NUMBER_RANGE_FAST_COUNT = "number range fast count";
    private static final String PREF_TIME_LENGTH_SIGN_SELECTION = "time length sign selection";
    private static final String PREF_NUMBER_OF_COMPARISONS_SIGN_SELECTION = "number of comparisons sign selection";
    private static final String PREF_NUMBER_RANGE_SIGN_SELECTION = "number range sign selection";
    private static final String PREF_LANGUAGE = "language";

    private static final String NOTIFICATIONS = "notifications";
    private static final String SOUND = "sound";
    private static final String VIBRATION = "vibration";
    private static final String BUTTONS_IN_MENU = "buttons in menu";

    private static final String PREF_HOURS = "hours";
    private static final String PREF_MINUTES = "minutes";
    SharedPreferences settings;

    SaveAndLoadSettings(SharedPreferences settings) {
        this.settings = settings;
    }

    public void save(String name, Spinner spinner) {
        String placeToSave = "";
        switch (name) {
            case "timeLengthForFastCount":
                placeToSave = PREF_TIME_LENGTH_FAST_COUNT;
                break;
            case "typesOfSpeedForFastCount":
                placeToSave = PREF_TYPES_OF_SPEED_FAST_COUNT;
                break;
            case "numberRangeForFastCount":
                placeToSave = PREF_NUMBER_RANGE_FAST_COUNT;
                break;
            case "timeLengthForSignSelection":
                placeToSave = PREF_TIME_LENGTH_SIGN_SELECTION;
                break;
            case "numberOfComparisonsSignSelection":
                placeToSave = PREF_NUMBER_OF_COMPARISONS_SIGN_SELECTION;
                break;
            case "numberRangeForSignSelection":
                placeToSave = PREF_NUMBER_RANGE_SIGN_SELECTION;
                break;
            case "language":
                placeToSave = PREF_LANGUAGE;
                break;
        }
        // получаем введенное имя
        int temp = spinner.getSelectedItemPosition();
        // сохраняем его в настройках
        SharedPreferences.Editor prefEditor = settings.edit();
        prefEditor.putInt(placeToSave, temp);
        prefEditor.apply();
    }

    public boolean wasItOpen(){
        if(!settings.getBoolean(PREF_FIRST_OPEN, false)) {
            SharedPreferences.Editor prefEditor = settings.edit();
            prefEditor.putBoolean(PREF_FIRST_OPEN, true);
            prefEditor.apply();
            return false;
        }
        return true;
    }

    public void save(String name, SwitchCompat switchCompat) {
        String placeToSave = "";
        switch (name) {
            case "notifications":
                placeToSave = NOTIFICATIONS;
                break;
                case "sound":
                placeToSave = SOUND;
                break;
            case "vibration":
                placeToSave = VIBRATION;
                break;
            case "buttons in menu":
                placeToSave = BUTTONS_IN_MENU;
                break;
        }

        // получаем введенное имя
        boolean temp = switchCompat.isChecked();
        // сохраняем его в настройках
        SharedPreferences.Editor prefEditor = settings.edit();
        prefEditor.putBoolean(placeToSave, temp);
        prefEditor.apply();
    }

    public void save(String name, int data) {
        String placeToSave = "";
        switch (name) {
            case "hours":
                placeToSave = PREF_HOURS;
                break;
            case "minutes":
                placeToSave = PREF_MINUTES;
                break;
        }
        // сохраняем его в настройках
        SharedPreferences.Editor prefEditor = settings.edit();
        prefEditor.putInt(placeToSave, data);
        prefEditor.apply();
    }

    public boolean getBoolean(String name) {
        String whatToLockFor = "";
        switch (name) {
            case "notifications":
                whatToLockFor = NOTIFICATIONS;
                break;
            case "sound":
                whatToLockFor = SOUND;
                break;
            case "vibration":
                whatToLockFor = VIBRATION;
                break;
            case "buttons in menu":
                whatToLockFor = BUTTONS_IN_MENU;
                break;
        }
        return settings.getBoolean(whatToLockFor, true);
    }

    public int getInt(String name) {
        String whatToLockFor = "";
        switch (name) {
            case "timeLengthForFastCount":
                whatToLockFor = PREF_TIME_LENGTH_FAST_COUNT;
                break;
            case "typesOfSpeedForFastCount":
                whatToLockFor = PREF_TYPES_OF_SPEED_FAST_COUNT;
                break;
            case "numberRangeForFastCount":
                whatToLockFor = PREF_NUMBER_RANGE_FAST_COUNT;
                break;
            case "timeLengthForSignSelection":
                whatToLockFor = PREF_TIME_LENGTH_SIGN_SELECTION;
                break;
            case "numberOfComparisonsSignSelection":
                whatToLockFor = PREF_NUMBER_OF_COMPARISONS_SIGN_SELECTION;
                break;
            case "numberRangeForSignSelection":
                whatToLockFor = PREF_NUMBER_RANGE_SIGN_SELECTION;
                break;
            case "language":
                whatToLockFor = PREF_LANGUAGE;
                break;

        }

        // получаем сохраненное имя
        return settings.getInt(whatToLockFor, 1);
    }

    public int getData(String name){
        String whatToLockFor = "";
        switch (name) {
            case "hours":
                whatToLockFor = PREF_HOURS;
                return settings.getInt(whatToLockFor, 18);
            case "minutes":
                whatToLockFor = PREF_MINUTES;
                return settings.getInt(whatToLockFor, 00);
        }
        return 0;
    }

    public void saveLanguage(){
        SharedPreferences.Editor prefEditor = settings.edit();
        prefEditor.putBoolean("language saved", true);
        prefEditor.apply();
    }
}
