package com.myjb.dev.mygaragesale;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.myjb.dev.network.PriceInquiry;
import com.myjb.dev.network.PriceInquiry2Aladin;
import com.myjb.dev.network.PriceInquiry2Yes24;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
@EActivity(R.layout.activity_fullscreen)
public class FullscreenActivity extends AppCompatActivity {

    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();

    @ViewById(R.id.fullscreen_content)
    View mContentView;

    @ViewById(R.id.fullscreen_content_controls)
    View mControlsView;


    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };

    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        delayedHide(AUTO_HIDE_DELAY_MILLIS);
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Click(R.id.fullscreen_content)
    void onToggle(View view) {
        if (mControlsView.getVisibility() == View.VISIBLE) {
            hide();
        } else {
            show();
        }
    }

    @Click(R.id.yes24)
    void onYes24Button(View view) {
        delayedHide(AUTO_HIDE_DELAY_MILLIS);

        new PriceInquiry2Yes24("9788934917915", new PriceInquiry.OnPriceListener() {
            @Override
            public void onPriceResult(List<PriceInquiry.Item> priceList) {
                Toast.makeText(getBaseContext(), "" + priceList.toString(), Toast.LENGTH_LONG).show();

            }
        }).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    ;

    @Click(R.id.aladin)
    void onAladinButton(View view) {
        delayedHide(AUTO_HIDE_DELAY_MILLIS);

        String[] list = new String[0];
        try {
            list = new String[]{
                    "9788991268807",    //프로그래머의 길, 멘토에게 묻다
                    "9788959136797",    //SBS 스페셜 산후조리 100일의 기적
                    "9788960862609",    //입사 후 3년
                    "9788995300961",    //Professional 소프트웨어 개발
                    URLEncoder.encode("MIT 수학천재들의 카지노 무너뜨리기", "EUC-KR").toString(),
            };
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < list.length; i++)
            new PriceInquiry2Aladin(list[i], new PriceInquiry.OnPriceListener() {
                @Override
                public void onPriceResult(List<PriceInquiry.Item> priceList) {
                    Toast.makeText(getBaseContext(), "" + priceList.toString(), Toast.LENGTH_LONG).show();

                }
            }).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }
}
