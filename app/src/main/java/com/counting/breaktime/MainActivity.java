package com.counting.breaktime;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;

import com.tooltip.Tooltip;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Button start, settings, statistics;
    private static final String PREFS_FILE = "Settings";
    SaveAndLoadSettings saveAndLoadSettings;
    ImageButton btnStars, btnInstagram;
    Tooltip tooltipStars, tooltipInstagram;
    ConstraintLayout constraintLayout;
    private boolean tooltipsChecking = false;
    AlertDialog alertAnswer;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saveAndLoadSettings = new SaveAndLoadSettings(getSharedPreferences(PREFS_FILE, MODE_PRIVATE));
        if (!saveAndLoadSettings.wasItOpen()) {
            firstOpen();
        }
        setLocal();
        setContentView(R.layout.activity_main);
        addListenerOnButton();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        setInvisible();

        if (saveAndLoadSettings.getBoolean("buttons in menu"))
            animation();
        if (!saveAndLoadSettings.wasItOpen())
            firstOpen();
        if (saveAndLoadSettings.getBoolean("notifications"))
            new NotificationsSettings(saveAndLoadSettings, (AlarmManager) getSystemService(Context.ALARM_SERVICE), this);
        else
            new NotificationsSettings((AlarmManager) getSystemService(Context.ALARM_SERVICE), this);
    }

    // установка локали по сохраненному
    public void setLocal() {
        String strLocal = "";
        saveAndLoadSettings = new SaveAndLoadSettings(getSharedPreferences(PREFS_FILE, MODE_PRIVATE));
        switch (FillSpinnerForSettings.language[saveAndLoadSettings.getInt("language")]) {
            case "English":
                strLocal = "en";
                break;
            case "Русский":
                strLocal = "ru";
                break;
        }

        Locale locale = new Locale(strLocal);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, null);
    }

    // установка локали для первого уведомления автоматически
    public void setLocalCurrent() {
        Locale locale = getResources().getConfiguration().locale;
        System.out.println(locale.getDisplayLanguage());
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, null);
    }


    // первое открытие приложения
    public void firstOpen() {
        setLocalCurrent();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.first_open_alert, null);
        Button btnCheckAnswer = (Button) mView.findViewById(R.id.btnOk);
        btnCheckAnswer.setOnClickListener(new View.OnClickListener() {
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

    // создание всплывающей подсказки на кнопке
    public void toolTip() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tooltipStars = new Tooltip.Builder(btnStars).setText(getString(R.string.rate_us)).setGravity(Gravity.TOP).setBackgroundColor(Color.parseColor("#ffffff")).setTextSize(R.dimen.tooltips).show();
            tooltipInstagram = new Tooltip.Builder(btnInstagram).setText(getString(R.string.subscribe_us)).setGravity(Gravity.TOP).setBackgroundColor(Color.parseColor("#ffffff")).setTextSize(R.dimen.tooltips).show();
        }
    }

    // скрытие всплывающей подсказки
    public void toolTipOff() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                tooltipStars.dismiss();
                tooltipInstagram.dismiss();
            } catch (Exception e) {
                System.out.println(e + "!");
            }
        }
    }

    // при нажатии на экран скрывать подсказки
    public void addListener() {
        constraintLayout = (ConstraintLayout) findViewById(R.id.layout);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolTipOff();
            }
        });
    }

    // при возвращении на главное меню
    @Override
    protected void onRestart() {
        super.onRestart();
        setInvisible();
        if (tooltipsChecking)
            toolTipOff();
        if (saveAndLoadSettings.getBoolean("buttons in menu"))
            animation();
        recreate();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        toolTipOff();
        return false;
    }


    // параметры окна
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    // обработка нажатий на кнопки
    public void addListenerOnButton() {
        start = (Button) findViewById(R.id.start);
        settings = (Button) findViewById(R.id.settings);
        statistics = (Button) findViewById(R.id.statistics);
        btnStars = (ImageButton) findViewById(R.id.stars);
        btnInstagram = (ImageButton) findViewById(R.id.instagram);
        start.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        toolTipOff();
                        Intent intent = new Intent(".Start");
                        startActivity(intent);
                    }
                }
        );
        settings.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        toolTipOff();
                        Intent intent = new Intent(".Settings");
                        startActivity(intent);
                    }
                }
        );
        statistics.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        toolTipOff();
                        Intent intent = new Intent(".Statistics");
                        startActivity(intent);
                    }
                }
        );
        btnStars.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        toolTipOff();
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.counting.breaktime"));
                        startActivity(intent);
                    }
                }
        );
        btnInstagram.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        toolTipOff();
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/counting_bt/"));
                        startActivity(intent);
                    }
                }
        );
    }

    // отображение кнопок, если позволяют настройки
    public void setInvisible() {
        final ImageButton btnStars = (ImageButton) findViewById(R.id.stars);
        final ImageButton btnInstagram = (ImageButton) findViewById(R.id.instagram);
        btnStars.setVisibility(View.GONE);
        btnInstagram.setVisibility(View.GONE);
    }

    // наимация кнопок
    public void animation() {
        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        final Animation animTranslate = AnimationUtils.loadAnimation(this, R.anim.scale);
        btnStars = (ImageButton) findViewById(R.id.stars);
        btnInstagram = (ImageButton) findViewById(R.id.instagram);
        btnStars.setVisibility(View.VISIBLE);
        btnInstagram.setVisibility(View.VISIBLE);
        btnStars.startAnimation(animAlpha);
        btnInstagram.startAnimation(animAlpha);
        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                btnStars.startAnimation(animTranslate);
                btnInstagram.startAnimation(animTranslate);
                tooltipsChecking = true;
                new CountDownTimer(1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        toolTip();
                        addListener();
                    }
                }.start();

            }
        }.start();
    }
}
