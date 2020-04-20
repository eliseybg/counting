package com.counting.breaktime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class FeedProgrammer extends AppCompatActivity {


    private static final long GAME_LENGTH_MILLISECONDS = 3000;
    private static final String AD_UNIT_ID = "ca-app-pub-9021007628151084/4485164363";

    private InterstitialAd interstitialAd;
    private CountDownTimer countDownTimer;
    private boolean gameIsInProgress;
    private long timerMilliseconds;
    private InterstitialAd mInterstitial;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println(1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_programmer);
//
        mInterstitial = new InterstitialAd(this);
        mInterstitial.setAdUnitId("ca-app-pub-9021007628151084/4485164363");
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitial.loadAd(adRequest);
        mInterstitial.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                if(mInterstitial.isLoaded()){
                    System.out.println("ready");
                    mInterstitial.show();
                }
            }
        });
//        System.out.println(2);
//        // Initialize the Mobile Ads SDK.
//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {}
//        });
//
//        // Create the InterstitialAd and set the adUnitId.
//        interstitialAd = new InterstitialAd(this);
//        // Defined in res/values/strings.xml
//        interstitialAd.setAdUnitId(AD_UNIT_ID);
//
//        interstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                interstitialAd.show();
//                Toast.makeText(FeedProgrammer.this, "onAdLoaded()", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//                Toast.makeText(FeedProgrammer.this,
//                        "onAdFailedToLoad() with error code: " + errorCode,
//                        Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onAdClosed() {
//                startGame();
//            }
//        });
//
//        // Create the "retry" button, which tries to show an interstitial between game plays.
//
//                showInterstitial();
//
//        startGame();
//    }
//
//    private void createTimer(final long milliseconds) {
//        // Create the game timer, which counts down to the end of the level
//        // and shows the "retry" button.
//        if (countDownTimer != null) {
//            countDownTimer.cancel();
//        }
//
//
//        countDownTimer = new CountDownTimer(milliseconds, 50) {
//            @Override
//            public void onTick(long millisUnitFinished) {
//                timerMilliseconds = millisUnitFinished;
//            }
//
//            @Override
//            public void onFinish() {
//                gameIsInProgress = false;
//            }
//        };
//    }
//
//    @Override
//    public void onResume() {
//        // Start or resume the game.
//        super.onResume();
//
//        if (gameIsInProgress) {
//            resumeGame(timerMilliseconds);
//        }
//    }
//
//    @Override
//    public void onPause() {
//        // Cancel the timer if the game is paused.
//        countDownTimer.cancel();
//        super.onPause();
//    }
//
//    private void showInterstitial() {
//        // Show the ad if it's ready. Otherwise toast and restart the game.
//        if (interstitialAd != null && interstitialAd.isLoaded()) {
//            interstitialAd.show();
//        } else {
//            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
//            startGame();
//        }
//    }
//
//    private void startGame() {
//        // Request a new ad if one isn't already loaded, hide the button, and kick off the timer.
//        if (!interstitialAd.isLoading() && !interstitialAd.isLoaded()) {
//            AdRequest adRequest = new AdRequest.Builder().build();
//            interstitialAd.loadAd(adRequest);
//        }
//
//        resumeGame(GAME_LENGTH_MILLISECONDS);
//    }
//
//    private void resumeGame(long milliseconds) {
//        // Create a new timer for the correct length and start it.
//        gameIsInProgress = true;
//        timerMilliseconds = milliseconds;
//        createTimer(milliseconds);
//        countDownTimer.start();
    }
}
