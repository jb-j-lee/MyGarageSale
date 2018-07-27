package com.myjb.dev.mygaragesale;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.myjb.dev.network.PriceInquiry;
import com.myjb.dev.network.PriceInquiry2Aladin;
import com.myjb.dev.network.PriceInquiry2Yes24;
import com.myjb.dev.network.PriceItem;
import com.myjb.dev.recyclerView.PriceAdapter;

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

    @ViewById(R.id.recyclerView)
    RecyclerView recyclerView;

    @ViewById(R.id.button)
    Button button;

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

    @Click(R.id.button)
    public void scanBarcode(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("Scan a barcode");
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
