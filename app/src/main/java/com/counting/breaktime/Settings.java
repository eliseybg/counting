package com.counting.breaktime;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Locale;

public class Settings extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private Button btnDelete;
    private final String PREFS_FILE = "Settings";
    SaveAndLoadSettings saveAndLoadSettings;
    Spinner spinnerForTimeLengthForFastCount, spinnerForTypesOfSpeedForFastCount, spinnerForNumberRangeForFastCount, spinnerForTimeLengthForSignSelection, numberOfComparisonsSignSelection, spinnerForNumberRangeForSignSelection, spinnerForLanguage;
    SwitchCompat switchNotificationSettings, switchSoundSettings, switchVibrationSettings, switchButtonsOnMainActivity;
    TextView currentTime;
    View divider;
    LinearLayout linearLayoutTime;
    AlertDialog alertAnswer;
    Context mContext;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        fill();
        mContext = this;
        saveAndLoadSettings = new SaveAndLoadSettings(getSharedPreferences(PREFS_FILE, MODE_PRIVATE));
        getSettings();
        addListenerOnButton();
    }

    public void deleteDataAlert() {
        final DBHelperFC dbHelperFC = new DBHelperFC(this);
        final DBHelperSS dbHelperSS = new DBHelperSS(this);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.delete_data_alert, null);
        Button btnDelete = (Button) mView.findViewById(R.id.delete);
        Button btnCancel = (Button) mView.findViewById(R.id.cancel);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelperFC.delete();
                dbHelperSS.delete();
                alertAnswer.cancel();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertAnswer.cancel();
            }
        });
        builder.setView(mView);
        builder.setCancelable(false);
        alertAnswer = builder.create();
        alertAnswer.show();
    }

    public void addListenerOnButton() {
        btnDelete = (Button) findViewById(R.id.delete);
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteDataAlert();
                    }
                }
        );
    }

    //заполняем выподающие списки
    public void fill() {
        FillSpinnerForSettings spinnerFill = new FillSpinnerForSettings();
        spinnerForTimeLengthForFastCount = spinnerFill.fillSpinner("timeLengthForFastCount", this, (Spinner) findViewById(R.id.spinnerForTimeLengthFastCount));
        spinnerForTypesOfSpeedForFastCount = spinnerFill.fillSpinner("typesOfSpeedForFastCount", this, (Spinner) findViewById(R.id.spinnerForSpeedFastCount));
        spinnerForNumberRangeForFastCount = spinnerFill.fillSpinner("numberRangeForFastCount", this, (Spinner) findViewById(R.id.spinnerForNumberRangeFastCount));
        spinnerForTimeLengthForSignSelection = spinnerFill.fillSpinner("timeLengthForSignSelection", this, (Spinner) findViewById(R.id.spinnerForTimeLengthSignSelection));
        numberOfComparisonsSignSelection = spinnerFill.fillSpinner("numberOfComparisonsSignSelection", this, (Spinner) findViewById(R.id.spinnerForNumberOfComparisonsSignSelection));
        spinnerForNumberRangeForSignSelection = spinnerFill.fillSpinner("numberRangeForSignSelection", this, (Spinner) findViewById(R.id.spinnerForNumberRangeSignSelection));
        spinnerForLanguage = spinnerFill.fillSpinner("language", this, (Spinner) findViewById(R.id.spinnerForLanguage));

        switchNotificationSettings = (SwitchCompat) findViewById(R.id.switchNotificationSettings);
        switchSoundSettings = (SwitchCompat) findViewById(R.id.switchSoundSettings);
        switchVibrationSettings = (SwitchCompat) findViewById(R.id.switchVibrationSettings);
        switchButtonsOnMainActivity = (SwitchCompat) findViewById(R.id.switchButtonsOnMainActivity);

        currentTime = (TextView) findViewById(R.id.current_time);
        divider = (View) findViewById(R.id.divider_time);
        linearLayoutTime = (LinearLayout) findViewById(R.id.liner_layout_time);
    }

    public void setLanguage(){
        switch (FillSpinnerForSettings.language[saveAndLoadSettings.getInt("language")]){
            case "English":
                Locale localeEn = new Locale("en");
                Locale.setDefault(localeEn);
                Configuration configurationEn = new Configuration();
                configurationEn.locale = localeEn;
                getBaseContext().getResources().updateConfiguration(configurationEn, null);
                flag = false;
                break;

            case "Русский":
                Locale localeRus = new Locale("ru");
                Locale.setDefault(localeRus);
                Configuration configurationRus = new Configuration();
                configurationRus.locale = localeRus;
                getBaseContext().getResources().updateConfiguration(configurationRus, null);
                flag = false;
                break;
        }
    }

    // загружаем старые настройки
    public void getSettings() {
        spinnerForTimeLengthForFastCount.setSelection(saveAndLoadSettings.getInt("timeLengthForFastCount"));
        spinnerForTypesOfSpeedForFastCount.setSelection(saveAndLoadSettings.getInt("typesOfSpeedForFastCount"));
        spinnerForNumberRangeForFastCount.setSelection(saveAndLoadSettings.getInt("numberRangeForFastCount"));
        spinnerForTimeLengthForSignSelection.setSelection(saveAndLoadSettings.getInt("timeLengthForSignSelection"));
        numberOfComparisonsSignSelection.setSelection(saveAndLoadSettings.getInt("numberOfComparisonsSignSelection"));
        spinnerForNumberRangeForSignSelection.setSelection(saveAndLoadSettings.getInt("numberRangeForSignSelection"));
        spinnerForLanguage.setSelection(saveAndLoadSettings.getInt("language"));

        switchNotificationSettings.setChecked(saveAndLoadSettings.getBoolean("notifications"));
        switchSoundSettings.setChecked(saveAndLoadSettings.getBoolean("sound"));
        switchVibrationSettings.setChecked(saveAndLoadSettings.getBoolean("vibration"));
        switchButtonsOnMainActivity.setChecked(saveAndLoadSettings.getBoolean("buttons in menu"));

        switchNotificationSettings.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    linearLayoutTime.setVisibility(View.GONE);
                    divider.setVisibility(View.GONE);
                    new NotificationsSettings((AlarmManager) getSystemService(Context.ALARM_SERVICE), mContext);
                }
                else {
                    linearLayoutTime.setVisibility(View.VISIBLE);
                    divider.setVisibility(View.VISIBLE);
                }
            }

        });

        String minutes = String.valueOf(String.valueOf(saveAndLoadSettings.getData("minutes")).length() == 1 ? "0" + saveAndLoadSettings.getData("minutes") : saveAndLoadSettings.getData("minutes"));
        currentTime.setText(saveAndLoadSettings.getData("hours") + " : " + minutes);
    }

    public void setTime(View view){
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "time picker");
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        saveAndLoadSettings.save("hours", hourOfDay);
        saveAndLoadSettings.save("minutes", minute);
        String minutes = String.valueOf(String.valueOf(saveAndLoadSettings.getData("minutes")).length() == 1 ? "0" + saveAndLoadSettings.getData("minutes") : saveAndLoadSettings.getData("minutes"));
        currentTime.setText(saveAndLoadSettings.getData("hours") + " : " + minutes);
        new NotificationsSettings(saveAndLoadSettings, (AlarmManager) getSystemService(Context.ALARM_SERVICE), this);
    }

    // сохраняем настройки при выходе
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        save();
        setLanguage();
    }

    // сохранение настроек
    public void save() {
        saveAndLoadSettings.save("timeLengthForFastCount", (Spinner) findViewById(R.id.spinnerForTimeLengthFastCount));
        saveAndLoadSettings.save("typesOfSpeedForFastCount", (Spinner) findViewById(R.id.spinnerForSpeedFastCount));
        saveAndLoadSettings.save("numberRangeForFastCount", (Spinner) findViewById(R.id.spinnerForNumberRangeFastCount));
        saveAndLoadSettings.save("timeLengthForSignSelection", (Spinner) findViewById(R.id.spinnerForTimeLengthSignSelection));
        saveAndLoadSettings.save("numberOfComparisonsSignSelection", (Spinner) findViewById(R.id.spinnerForNumberOfComparisonsSignSelection));
        saveAndLoadSettings.save("numberRangeForSignSelection", (Spinner) findViewById(R.id.spinnerForNumberRangeSignSelection));
        saveAndLoadSettings.save("language", (Spinner) findViewById(R.id.spinnerForLanguage));
        saveAndLoadSettings.saveLanguage();

        saveAndLoadSettings.save("notifications", (SwitchCompat) findViewById(R.id.switchNotificationSettings));
        saveAndLoadSettings.save("sound", (SwitchCompat) findViewById(R.id.switchSoundSettings));
        saveAndLoadSettings.save("vibration", (SwitchCompat) findViewById(R.id.switchVibrationSettings));
        saveAndLoadSettings.save("buttons in menu", (SwitchCompat) findViewById(R.id.switchButtonsOnMainActivity));
    }
}
