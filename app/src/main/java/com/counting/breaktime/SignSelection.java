package com.counting.breaktime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SignSelection extends AppCompatActivity {
    private TextView placeForTask, placeForLevel, lineCount, timer;
    private int[] operations, numbers;
    private int time, upHard = 0, level = 1, answer = 0, amountOfNumbers, startNumber, endNumber, randomnumber = 5, startActions, endActions, randomActions;
    private CountDownTimer countDownTimer;
    private boolean timerbreak = false;
    private MediaPlayer mp;
    private String line = "", linewithsplash = "", linesigns = "";
    private Button plus, minus, multiplication, division;
    private static final String PREFS_FILE = "Settings";
    private ImageButton restart;
    String timeLengthForSignSelection, numberOfComparisonsSignSelection, numberRangeForSignSelection;
    SaveAndLoadSettings saveAndLoadSettings;
    DBHelperSS dbHelperSS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        setContentView(R.layout.activity_signselection);
        placeForTask = (TextView) findViewById(R.id.placefortask);
        placeForLevel = (TextView) findViewById(R.id.placeforlevel);
        lineCount = (TextView) findViewById(R.id.linecount);
        restart = (ImageButton) findViewById(R.id.restart);
        restart.setImageDrawable(getDrawable(R.drawable.ic_media_play));
        addListenerOnButton();
        saveAndLoadSettings = new SaveAndLoadSettings(getSharedPreferences(PREFS_FILE, MODE_PRIVATE));
        getSettingsForNumbers();
//        startFunction();
        dbHelperSS = new DBHelperSS(this);
    }

    public void putData(String level){
        dbHelperSS.addDataToSS(timeLengthForSignSelection, numberOfComparisonsSignSelection, numberRangeForSignSelection, level);
    }

    public void getSettingsForNumbers(){
        timeLengthForSignSelection = FillSpinnerForSettings.timeLengthForSignSelection[saveAndLoadSettings.getInt("timeLengthForSignSelection")];
        switch (timeLengthForSignSelection){
            case "1 min":
                time = 60;
                break;
            case "2 min":
                time = 120;
                break;
            case "3 min":
                time = 180;
                break;
        }

        numberOfComparisonsSignSelection = FillSpinnerForSettings.numberOfComparisonsSignSelection[saveAndLoadSettings.getInt("numberOfComparisonsSignSelection")];
        switch (numberOfComparisonsSignSelection){
            case "1 - 3":
                startActions = 1;
                endActions = 3;
                break;
            case "1 - 5":
                startActions = 1;
                endActions = 5;
                break;
            case "1 - 7":
                startActions = 1;
                endActions = 7;
                break;
        }
        randomActions = startActions + 2;

        numberRangeForSignSelection = FillSpinnerForSettings.numberRangeForSignSelection[saveAndLoadSettings.getInt("numberRangeForSignSelection")];
        switch (numberRangeForSignSelection){
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

    private void startFunction() {
        upHard = 0;
        level = 1;
        answer = 0;
        timerbreak = false;
        line = "";
        linewithsplash = "";
        linesigns = "";
        randomnumber = 5;
        start();
        timer = (TextView) findViewById(R.id.timer);
        timer.setTextColor(Color.BLACK);
        timer();
    }

    //запуск уравнений
    private void start() {
//        restart.setImageDrawable(getDrawable(R.drawable.ic_media_play));
        restart.setVisibility(View.INVISIBLE);
        //количество действий
        int counter = 0;
        for (; ; ) {
            counter++;
            amountOfNumbers = (int) (startActions + Math.random() * (randomActions - startActions) + 1);
            if ((amountOfNumbers >= 2 && amountOfNumbers <= 4) || counter > 15) break;
        }

        //увеличение чисел в уравнении каждые 10 правильных ответов
        if (upHard == 3) {
            randomActions++;
            upHard = 0;
        }
        upHard++;

        //увеличение чисела для счета каждые 5 правильных ответов
        if (upHard % 2 == 0 && randomnumber < endNumber) {
            randomnumber += 5;
        }
        if (randomnumber > endNumber)
            randomnumber = endNumber;

        if (randomActions > endActions)
            randomActions = endActions;

        //получение знаков
        int amountofsigns = amountOfNumbers - 1;
        operations = new int[amountofsigns];
        for (int i = 0; i < amountofsigns; i++) {
            int sing = (int) (1 + Math.random() * 4);
            operations[i] = sing;
            if (i >= 1) {
                while (operations[i] == operations[i - 1])
                    operations[i] = (int) (1 + Math.random() * 4);
            }
        }

        //ввод чисел
        numbers = new int[amountOfNumbers];
        numbers[0] = (int) (startNumber + Math.random() * randomnumber);
        line += numbers[0];
        linesigns += numbers[0];
        for (int i = 1; i < amountOfNumbers; i++) {
            switch (operations[i - 1]) {
                case 1:
                    numbers[i] = (int) (startNumber + Math.random() * randomnumber);
                    line += " _ " + numbers[i];
                    linesigns += "+" + numbers[i];
                    break;
                case 2:
                    numbers[i] = (int) (startNumber + Math.random() * randomnumber);
                    line += " _ " + numbers[i];
                    linesigns += "-" + numbers[i];
                    numbers[i] = -numbers[i];
                    break;
                case 3:
                    numbers[i] = (int) (startNumber + Math.random() * randomnumber);
                    line += " _ " + numbers[i];
                    linesigns += "*" + numbers[i];
                    numbers[i] *= numbers[i - 1];
                    numbers[i - 1] = 0;
                    break;
                case 4:
                    for (; ; ) {
                        int del = (int) (startNumber + Math.random() * randomnumber);
                        if (numbers[i - 1] % del == 0) {
                            numbers[i] = del;
                            line += " _ " + numbers[i];
                            linesigns += "/" + numbers[i];
                            numbers[i] = numbers[i - 1] / numbers[i];
                            numbers[i - 1] = 0;
                            break;
                        }
                    }
                    break;
            }

        }

        //получение ответа
        for (int i = 0; i < amountOfNumbers; i++) {
            answer += numbers[i];
        }

        //формируем и выводим условие
        linewithsplash = line;
        placeForTask.setText(line + " = " + answer);
        lineCount.setText(linesigns + "=" + LineCounter.SolveLine(linesigns));

        if ((linesigns + "=" + LineCounter.SolveLine(linesigns)).length() > 20) {
            float px = 25 * getResources().getDisplayMetrics().scaledDensity;
            lineCount.setTextSize(px);
        }
    }

    //таймер времени действия
    public void timer() {
        countDownTimer = new CountDownTimer(time * 1000, (long) (1000)) {
            // таймер начинается
            @Override
            public void onTick(long l) {
                timer.setText("" + l / 1000);
                if (l / 1000 < 15) {
                    timer.setTextColor(Color.RED);
                    Animation animation = new AlphaAnimation(0.0f, 1.0f);
//                       длительность анимации 5/10 секунды
                    animation.setDuration(500);
//                      сдвижка начала анимации (с середины)
                    animation.setStartOffset(50);
//                      режим повтора - сначала или в обратном порядке
                    animation.setRepeatMode(Animation.REVERSE);
//                      режим повтора
                    animation.setRepeatCount(Animation.ABSOLUTE);
//                      накладываем анимацию на TextView
                    timer.startAnimation(animation);
                }
            }

            //таймер заканчивается
            @Override
            public void onFinish() {
                timer.setText("00");
                putData(String.valueOf(level));
                placeForTask.setText("");
                timerbreak = true;
                restart.setVisibility(View.VISIBLE);
            }
        }.start();
    }


    //нажатие на кнопки
    public void addListenerOnButton() {
        plus = (Button) findViewById(R.id.plus);
        minus = (Button) findViewById(R.id.minus);
        multiplication = (Button) findViewById(R.id.multiplication);
        division = (Button) findViewById(R.id.division);
        plus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (timerbreak == false) {
                            int k = linewithsplash.indexOf("_", 0);
                            linewithsplash = linewithsplash.substring(0, k) + "+" + linewithsplash.substring(k + 1);

                            placeForTask.setText(linewithsplash + " = " + answer);
                            if (linewithsplash.indexOf("_", 0) == -1)
                                checkResult(linewithsplash);
                        }

                    }
                }
        );
        minus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (timerbreak == false) {
                            int k = linewithsplash.indexOf("_", 0);
                            linewithsplash = linewithsplash.substring(0, k) + "-" + linewithsplash.substring(k + 1);
                            placeForTask.setText(linewithsplash + " = " + answer);
                            if (linewithsplash.indexOf("_", 0) == -1)
                                checkResult(linewithsplash);

                        }
                    }
                }
        );
        multiplication.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (timerbreak == false) {
                            int k = linewithsplash.indexOf("_", 0);
                            linewithsplash = linewithsplash.substring(0, k) + "*" + linewithsplash.substring(k + 1);

                            placeForTask.setText(linewithsplash + " = " + answer);
                            if (linewithsplash.indexOf("_", 0) == -1)
                                checkResult(linewithsplash);


                        }
                    }
                }
        );
        division.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (timerbreak == false) {
                            int k = linewithsplash.indexOf("_", 0);
                            linewithsplash = linewithsplash.substring(0, k) + "/" + linewithsplash.substring(k + 1);
                            placeForTask.setText(linewithsplash + " = " + answer);
                            if (linewithsplash.indexOf("_", 0) == -1)
                                checkResult(linewithsplash);

                        }
                    }
                }
        );
        restart.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        restart.setImageDrawable(getDrawable(R.drawable.ic_menu_rotate));
                        restart.setVisibility(View.GONE);
                        startFunction();
                    }
                }
        );
    }

    //сообщение о правильном решении
    public void checkResult(String myResult) {
//      правильный ответ
        if (LineCounter.SolveLine(myResult) == answer) {
            if(saveAndLoadSettings.getBoolean("sound")) {
                mp = MediaPlayer.create(this, R.raw.correct_answer);
                mp.start();
            }
            line = "";
            linesigns = "";

            level++;
            placeForLevel.setText((getString(R.string.level) + " " + level));
            Handler handlerGREEN = new Handler();
            handlerGREEN.postDelayed(new Runnable() {
                @Override
                public void run() {
                    placeForTask.setText((linewithsplash + " = " + answer), TextView.BufferType.SPANNABLE);
                    Spannable s = (Spannable) placeForTask.getText();
                    s.setSpan(new ForegroundColorSpan(0xFF03CD46), 0, (linewithsplash + " = " + answer).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }, 100);

            final Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.right_text_fc), Toast.LENGTH_SHORT);
            toast.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    answer = 0;
                    start();

                    toast.cancel();
                }
            }, 300);
        } else fail();
    }

    //сообщение о неправильном решении
    public void fail() {
        if(saveAndLoadSettings.getBoolean("sound")) {
            mp = MediaPlayer.create(this, R.raw.error_answer);
            mp.start();
        }
        upHard = 0;
        linesigns = "";
        linewithsplash = line;
        Handler handlerRED = new Handler();
        handlerRED.postDelayed(new Runnable() {
            @Override
            public void run() {
                placeForTask.setText((linewithsplash + " = " + answer), TextView.BufferType.SPANNABLE);
                Spannable s = (Spannable) placeForTask.getText();
                s.setSpan(new ForegroundColorSpan(0xFFFF0000), 0, (linewithsplash + " = " + answer).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }, 200);
// Get instance of Vibrator from current Context
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

// Vibrate for 400 milliseconds
        if (v != null && saveAndLoadSettings.getBoolean("vibration"))
            v.vibrate(200);
        randomnumber = 5;
        final Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.wrong_text_fc), Toast.LENGTH_SHORT);
        toast.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
                placeForTask.setText((linewithsplash + " = " + answer));
            }
        }, 300);
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
}

