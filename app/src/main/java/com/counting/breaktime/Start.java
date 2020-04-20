package com.counting.breaktime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Start extends AppCompatActivity {
    private Button fastCount, signSelection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        addListenerOnButton();
        fastCount = (Button)findViewById(R.id.fastcount);
    }

    public void addListenerOnButton(){
        fastCount = (Button)findViewById(R.id.fastcount);
        signSelection = (Button)findViewById(R.id.signselection);
        fastCount.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(".FastCount");
                        startActivity(intent);
                    }
                }
        );
        signSelection.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(".SignSelection");
                        startActivity(intent);
                    }
                }
        );
    }
}
