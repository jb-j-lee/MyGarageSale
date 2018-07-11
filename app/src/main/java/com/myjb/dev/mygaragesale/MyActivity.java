package com.myjb.dev.mygaragesale;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ArrayRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.myjb.dev.network.PriceInquiry;
import com.myjb.dev.network.PriceInquiry2Aladin;
import com.myjb.dev.network.PriceInquiry2Yes24;
import com.myjb.dev.network.PriceItem;
import com.myjb.dev.recyclerView.PriceAdapter;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringArrayRes;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
@EActivity(R.layout.activity_fullscreen)
public class MyActivity extends AppCompatActivity {

    @ViewById(R.id.fullscreen_content)
    View contentView;

    @ViewById(R.id.fullscreen_content_controls)
    View controlsView;

    @ViewById(R.id.recyclerView)
    RecyclerView recyclerView;

    @Bean
    PriceAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void bindAdapter() {
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setHasFixedSize(true);
    }

    @Click(R.id.yes24)
    void onYes24Button(View view) {
        new PriceInquiry2Yes24("9788934917915", new PriceInquiry.OnPriceListener() {
            @Override
            public void onPriceResult(List<PriceItem> priceList) {
                Toast.makeText(getBaseContext(), "" + priceList.toString(), Toast.LENGTH_LONG).show();

            }
        }).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    @Click(R.id.aladin)
    void onAladinButton(View view) {
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
                public void onPriceResult(List<PriceItem> priceList) {
                    Toast.makeText(getBaseContext(), "" + priceList.toString(), Toast.LENGTH_LONG).show();

                }
            }).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }
}
