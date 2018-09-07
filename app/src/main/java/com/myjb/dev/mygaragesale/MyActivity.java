package com.myjb.dev.mygaragesale;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.myjb.dev.network.BookInfoItem;
import com.myjb.dev.network.NetworkConstraint;
import com.myjb.dev.network.PriceInquiry;
import com.myjb.dev.network.PriceInquiry2Aladin;
import com.myjb.dev.network.PriceInquiry2Yes24;
import com.myjb.dev.recyclerView.CardAdapter;
import com.myjb.dev.view.ClearEditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EActivity(R.layout.activity_fullscreen)
public class MyActivity extends AppCompatActivity implements CardAdapter.OnItemClickListener {

    @ViewById(R.id.scan_button)
    ImageButton scanButton;

    @ViewById(R.id.editText)
    ClearEditText editText;

    @ViewById(R.id.search_button)
    ImageButton searchButton;

    @ViewById(R.id.recyclerView)
    RecyclerView recyclerView;

    @Bean
    CardAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
    }

    @AfterViews
    void bindAdapter() {
        recyclerAdapter.setOnItemClickListener(this);

        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @AfterViews
    void bindEditText() {
        editText.setOnTextChangeListener(new ClearEditText.OnTextChangeListener() {
            @Override
            public void onTextChange(int length) {
                searchButton.setEnabled(length > 0);
            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    search(v);
                }
                return false;
            }
        });

        searchButton.setEnabled(false);
    }

    @AfterViews
    void bindViewePager() {
    }

    @Click(R.id.search_button)
    public void search(View view) {
        final String text = editText.getText().toString();

        if (TextUtils.isEmpty(text)) {
            //TODO MGS
            Toast.makeText(getBaseContext(), "입력하세요", Toast.LENGTH_SHORT).show();
            return;
        }

        new PriceInquiry2Aladin(text, new PriceInquiry.OnPriceListener() {
            @Override
            public void onPriceResult(List<BookInfoItem> priceList) {
                if (priceList != null && !priceList.isEmpty()) {
                    priceList.get(0).company = NetworkConstraint.Company.ALADIN;
                    recyclerAdapter.notifyDataSetChanged();

                    Toast.makeText(getBaseContext(), "Aladin : " + text + ", " + priceList.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        new PriceInquiry2Yes24(text, new PriceInquiry.OnPriceListener() {
            @Override
            public void onPriceResult(List<BookInfoItem> priceList) {
                if (priceList != null && !priceList.isEmpty()) {
                    priceList.get(0).company = NetworkConstraint.Company.YES24;
//                    recyclerAdapter.updateView(priceList.get(0), 2 * position + 1);
                    recyclerAdapter.notifyDataSetChanged();

                    Toast.makeText(getBaseContext(), "Yes24 : " + text + ", " + priceList.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    @Click(R.id.scan_button)
    public void scanBarcode(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt(getString(R.string.scan_barcode));
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (!TextUtils.isEmpty(result.getContents())) {
                //TODO call seach(view) - auto searching
                editText.setText(result.getContents());
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onItemClick(final String name, String isbn, final int position) {
        new PriceInquiry2Aladin(isbn, new PriceInquiry.OnPriceListener() {
            @Override
            public void onPriceResult(List<BookInfoItem> priceList) {
                if (priceList != null && !priceList.isEmpty()) {
                    priceList.get(0).company = NetworkConstraint.Company.ALADIN;
                    recyclerAdapter.updateView(priceList.get(0), position);
                    recyclerAdapter.notifyItemChanged(position);

                    Toast.makeText(getBaseContext(), "Aladin : " + name + ", " + priceList.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);

//        new PriceInquiry2Yes24(isbn, new PriceInquiry.OnPriceListener() {
//            @Override
//            public void onPriceResult(List<BookInfoItem> priceList) {
//                if (priceList != null && !priceList.isEmpty()) {
//                    priceList.get(0).company = NetworkConstraint.Company.YES24;
//                    recyclerAdapter.updateView(priceList.get(0), position);
//                    recyclerAdapter.notifyItemChanged(position);
//
//                    Toast.makeText(getBaseContext(), "Yes24 : " + name + ", " + priceList.toString(), Toast.LENGTH_LONG).show();
//                }
//            }
//        }).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }
}
