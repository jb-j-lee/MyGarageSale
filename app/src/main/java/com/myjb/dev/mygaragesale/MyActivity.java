package com.myjb.dev.mygaragesale;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.myjb.dev.network.PriceInquiry;
import com.myjb.dev.network.PriceInquiry2Aladin;
import com.myjb.dev.network.PriceInquiry2Yes24;
import com.myjb.dev.network.PriceItem;
import com.myjb.dev.recyclerView.PriceAdapter;
import com.myjb.dev.view.ClearEditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import static com.myjb.dev.network.PriceItem.ALADIN;
import static com.myjb.dev.network.PriceItem.YES24;

@EActivity(R.layout.activity_fullscreen)
public class MyActivity extends AppCompatActivity implements PriceAdapter.OnItemClickListener {

    @ViewById(R.id.scan_button)
    ImageButton scanButton;

    @ViewById(R.id.editText)
    ClearEditText editText;

    @ViewById(R.id.search_button)
    ImageButton searchButton;

    @ViewById(R.id.recyclerView)
    RecyclerView recyclerView;

    @Bean
    PriceAdapter recyclerAdapter;

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
    }

    @AfterViews
    void bindEditText() {
        editText.setOnTextChangeListener(new ClearEditText.OnTextChangeListener() {
            @Override
            public void onTextChange(int length) {
                searchButton.setEnabled(length > 0);
            }
        });
        searchButton.setEnabled(false);
    }

    @Click(R.id.search_button)
    public void search(View view) {

        new PriceInquiry2Aladin(editText.getText().toString(), new PriceInquiry.OnPriceListener() {
            @Override
            public void onPriceResult(List<PriceItem> priceList) {
                if (priceList != null && !priceList.isEmpty()) {
                    priceList.get(0).company = ALADIN;
                    recyclerAdapter.notifyDataSetChanged();

                    Toast.makeText(getBaseContext(), "Aladin : " + editText.getText().toString() + ", " + priceList.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        new PriceInquiry2Yes24(editText.getText().toString(), new PriceInquiry.OnPriceListener() {
            @Override
            public void onPriceResult(List<PriceItem> priceList) {
                if (priceList != null && !priceList.isEmpty()) {
                    priceList.get(0).company = YES24;
//                    recyclerAdapter.updateView(priceList.get(0), 2 * position + 1);
                    recyclerAdapter.notifyDataSetChanged();

                    Toast.makeText(getBaseContext(), "Yes24 : " + editText.getText().toString() + ", " + priceList.toString(), Toast.LENGTH_LONG).show();
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
            public void onPriceResult(List<PriceItem> priceList) {
                if (priceList != null && !priceList.isEmpty()) {
                    priceList.get(0).company = ALADIN;
                    recyclerAdapter.updateView(priceList.get(0), 2 * position);
                    recyclerAdapter.notifyDataSetChanged();

                    Toast.makeText(getBaseContext(), "Aladin : " + name + ", " + priceList.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        new PriceInquiry2Yes24(isbn, new PriceInquiry.OnPriceListener() {
            @Override
            public void onPriceResult(List<PriceItem> priceList) {
                if (priceList != null && !priceList.isEmpty()) {
                    priceList.get(0).company = YES24;
                    recyclerAdapter.updateView(priceList.get(0), 2 * position + 1);
                    recyclerAdapter.notifyDataSetChanged();

                    Toast.makeText(getBaseContext(), "Yes24 : " + name + ", " + priceList.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }
}
