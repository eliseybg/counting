package com.counting.breaktime;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class FastCount extends AppCompatActivity {
    SaveAndLoadSettings saveAndLoadSettings;
    private TextView numbers;
    private ImageButton start, restart;
    private int checkStart = 0;
    private String strDialogResult;
    private int dialogResult = 0, safe = 0, result = 0, time;
    private MediaPlayer mp;
    private double speed, startNumber, endNumber;
    private CountDownTimer countDownTimer;
    private ProgressBar mProgressBar;
    private ObjectAnimator progressAnimator;
    AlertDialog alertAnswer;
    DBHelperFC dbHelper;
    String timeLengthForFastCount, typesOfSpeedForFastCount, numberRangeForFastCount;
    private static final String PREFS_FILE = "Settings";
    private View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.start:
                    start();
                    break;
                case R.id.restart:
                    restart();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fastcount);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        numbers = (TextView) findViewById(R.id.numbers);
        start = (ImageButton) findViewById(R.id.start);
        start.setOnClickListener(btnClickListener);
        restart = (ImageButton) findViewById(R.id.restart);
        restart.setOnClickListener(btnClickListener);
        saveAndLoadSettings = new SaveAndLoadSettings(getSharedPreferences(PREFS_FILE, MODE_PRIVATE));
        getSettingsForNumbers();
        dbHelper = new DBHelperFC(this);
    }

    public void putData(String result){
        dbHelper.addDataToFC(timeLengthForFastCount, typesOfSpeedForFastCount, numberRangeForFastCount, result);
    }

    // получение данных из настроек
    public void getSettingsForNumbers(){
        timeLengthForFastCount = FillSpinnerForSettings.timeLengthForFastCount[saveAndLoadSettings.getInt("timeLengthForFastCount")];
        switch (timeLengthForFastCount){
            case "15 sec":
                time = 15;
                break;
            case "30 sec":
                time = 30;
                break;
            case "1 min":
                time = 60;
                break;
        }
        typesOfSpeedForFastCount = FillSpinnerForSettings.typesOfSpeedForFastCount[saveAndLoadSettings.getInt("typesOfSpeedForFastCount")];
        switch (typesOfSpeedForFastCount){
            case "elementary":
                speed = 1.5;
                break;
            case "middle":
                speed = 1.2;
                break;
            case "master":
                speed = 0.9;
                break;
            case "professional":
                speed = 0.7;
                break;
        }
        numberRangeForFastCount = FillSpinnerForSettings.numberRangeForFastCount[saveAndLoadSettings.getInt("numberRangeForFastCount")];
        switch (numberRangeForFastCount){
            case "5 - 15":
                startNumber = 5;
                endNumber = 16;
                break;
            case "5 - 25":
                startNumber = 5;
                endNumber = 26;
                break;
            case "5 - 35":
                startNumber = 5;
                endNumber = 36;
                break;
            case "5 - 50":
                startNumber = 5;
                endNumber = 51;
                break;
        }
    }

    // начало подбора цифр
    private void start() {
        //запуск таймера
        if (checkStart == 0) {
            anim();
            result = 0;
            countDownTimer = new CountDownTimer((long)(time * 1000), (long) (1000 * speed)) {
                // таймер начинается
                @Override
                public void onTick(long l) {
                    numbers.setText("");
                    int num = (int) (startNumber + Math.random() * (endNumber - startNumber));
                    if (num > 0 && safe > 0) {
                        num = -num;
                    }
                    if (num < 0 && safe < 0) {
                        num = -num;
                    }
                    if (safe == num || num == 0) num =(int) (startNumber + Math.random() * (endNumber - startNumber));
                    result += num;
                    safe = num;
                    if (num > 0) numbers.setText("+" + num);
                    else numbers.setText("" + num);
                    System.out.println(progressAnimator.getCurrentPlayTime() + "     " + progressAnimator.getCurrentPlayTime()/ time);
                    if(progressAnimator.getCurrentPlayTime() > time * 3 / 4 * 1000){
                        mProgressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
                    }
                }

                //таймер заканчивается
                @Override
                public void onFinish() {
                    numbers.setText("");
                    showDialog();
                }
            }.start();
        }
        checkStart++;
    }

    // анимация ползунка на прогресс-баре
    private void anim(){
        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.setMax(1000);
        progressAnimator = ObjectAnimator.ofInt(mProgressBar, "progress", 1000, 0);
        progressAnimator.setDuration(time * 1000);
        mProgressBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        progressAnimator.start();
    }

    //рестарт
    private void restart() {
        if (countDownTimer != null) {
            mProgressBar.setMax(0);
            countDownTimer.cancel();
            countDownTimer = null;
            numbers.setText("");
            checkStart = 0;
        }
    }

    //нажатие назад
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    //вылетающее окно проверки
    void showDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_input, null);
        final EditText etAnswer = (EditText) mView.findViewById(R.id.answer);
        Button btnCheckAnswer = (Button) mView.findViewById(R.id.checkAnswer);
        btnCheckAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etAnswer.getText().toString().isEmpty()) {
                    strDialogResult = etAnswer.getText().toString();
                    dialogResult = Integer.parseInt(strDialogResult);
                    isCorrect(result, dialogResult);
                    alertAnswer.cancel();
                }
            }
        });
        builder.setView(mView);
        builder.setCancelable(false);
        alertAnswer = builder.create();
        alertAnswer.show();
    }

    //подсказки, при вводе ответа
    private void isCorrect(int a, int b) {
        checkStart = 0;
        if (a == b) {
            putData("Correct");

            if(saveAndLoadSettings.getBoolean("sound")) {
                mp = MediaPlayer.create(this, R.raw.correct_answer);
                mp.start();
            }
            Toast.makeText(
                    FastCount.this, getString(R.string.right_text_fc),
                    Toast.LENGTH_SHORT
            ).show();
        } else {
            putData("Wrong");
            if(saveAndLoadSettings.getBoolean("sound")) {
                mp = MediaPlayer.create(this, R.raw.error_answer);
                mp.start();
            }
            Toast.makeText(
                    FastCount.this, getString(R.string.wrong_text_fc),
                    Toast.LENGTH_SHORT
            ).show();
            Toast.makeText(
                    FastCount.this, getString(R.string.correct_answer_fc) + " " + result,
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}

