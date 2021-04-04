package com.counting.breaktime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Statistics extends AppCompatActivity {
    private static final String TAG = "ListDataActivity";
    DBHelperFC dbHelperFC;
    DBHelperSS dbHelperSS;
    private TextView number_FC, time_FC, speed_FC, range_FC, result_FC;
    private TextView number_SS, time_SS, simile_SS, range_SS, level_SS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        dbHelperFC = new DBHelperFC(this);
        dbHelperSS = new DBHelperSS(this);
        number_FC = (TextView) findViewById(R.id.number_fc);
        time_FC = (TextView) findViewById(R.id.time_fc);
        speed_FC = (TextView) findViewById(R.id.speed_fc);
        range_FC = (TextView) findViewById(R.id.range_fc);
        result_FC = (TextView) findViewById(R.id.result_fc);

        number_SS = (TextView) findViewById(R.id.number_ss);
        time_SS = (TextView) findViewById(R.id.time_ss);
        simile_SS = (TextView) findViewById(R.id.simile_ss);
        range_SS = (TextView) findViewById(R.id.range_ss);
        level_SS = (TextView) findViewById(R.id.level_ss);
        getListViewFC();
    }

    private void getListViewFC() {
        String numberFC = "", timeFC = "", speedFC = "", rangeFC = "", resultFC = "";
        Cursor dataFC = dbHelperFC.getData();
        int counterFC = 1;
        while (dataFC.moveToNext()) {
            // 1 - column 2
            numberFC += counterFC + "\n";
            counterFC++;
            timeFC += dataFC.getString(1) + "\n";
            speedFC += dataFC.getString(2) + "\n";
            rangeFC += dataFC.getString(3) + "\n";
            resultFC += dataFC.getString(4) + "\n";
        }
        if (numberFC.length() != 0) {
            number_FC.setText(numberFC.substring(0, numberFC.length() - 1));
            time_FC.setText(timeFC.substring(0, timeFC.length() - 1));
            speed_FC.setText(speedFC.substring(0, speedFC.length() - 1));
            range_FC.setText(rangeFC.substring(0, rangeFC.length() - 1));
            result_FC.setText(resultFC.substring(0, resultFC.length() - 1));
        } else showSpecialTextFC();

        String numberSS = "", timeSS = "", simileSS = "", rangeSS = "", levelSS = "";
        Cursor dataSS = dbHelperSS.getData();
        int counterSS = 1;
        while (dataSS.moveToNext()) {
            // 1 - column 2
            numberSS += counterSS + "\n";
            counterSS++;
            timeSS += dataSS.getString(1) + "\n";
            simileSS += dataSS.getString(2) + "\n";
            rangeSS += dataSS.getString(3) + "\n";
            levelSS += dataSS.getString(4) + "\n";
        }
        if (numberSS.length() != 0) {
            number_SS.setText(numberSS.substring(0, numberSS.length() - 1));
            time_SS.setText(timeSS.substring(0, timeSS.length() - 1));
            simile_SS.setText(simileSS.substring(0, simileSS.length() - 1));
            range_SS.setText(rangeSS.substring(0, rangeSS.length() - 1));
            level_SS.setText(levelSS.substring(0, levelSS.length() - 1));
        }
        else showSpecialTextSS();
    }

    public void showSpecialTextFC() {
        findViewById(R.id.ScrollView_FC).setVisibility(View.GONE);
        findViewById(R.id.linearLayout1).setVisibility(View.VISIBLE);
    }

    public void showSpecialTextSS() {
        findViewById(R.id.ScrollView_SS).setVisibility(View.GONE);
        findViewById(R.id.linearLayout2).setVisibility(View.VISIBLE);
    }

    public void openFastCount(View view) {
        Intent intent = new Intent(".FastCount");
        startActivity(intent);
        onDestroy();
    }

    public void openFSignSelection(View view) {
        Intent intent = new Intent(".SignSelection");
        startActivity(intent);
        onDestroy();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }


}
