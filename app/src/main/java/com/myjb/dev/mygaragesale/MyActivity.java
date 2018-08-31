package com.myjb.dev.mygaragesale;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

    @ViewById(R.id.isbn_button)
    ImageButton isbnButton;

    @ViewById(R.id.editText)
    EditText editText;

    @ViewById(R.id.search_button)
    ImageButton searchButton;

    @ViewById(R.id.recyclerView)
    RecyclerView recyclerView;

    @ViewById(R.id.button)
    Button button;

    @Bean
    PriceAdapter recyclerAdapter;

    Drawable cancelIcon;

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

        testEditText();
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

    @Click(R.id.isbn_button)
    public void scanBarcode(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("Scan a barcode");
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }

    @Click(R.id.search_button)
    public void test(View view) {

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

    private void testEditText() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cancelIcon = getDrawable(R.mipmap.cancel);
        } else {
            cancelIcon = getResources().getDrawable(R.mipmap.cancel);
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editText.setCompoundDrawablesWithIntrinsicBounds(null, null, (editText.isFocused() && s.length() > 0) ? cancelIcon : null,null);
            }
        });

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                editText.setCompoundDrawablesWithIntrinsicBounds(null, null, (hasFocus && editText.getText().toString().length() > 0) ? cancelIcon : null, null);
            }
        });

        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (editText.getCompoundDrawables()[DRAWABLE_RIGHT] == null)
                        return false;

                    if (event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        Toast.makeText(getBaseContext(), "Hi DRAWABLE_RIGHT", Toast.LENGTH_SHORT).show();
                        editText.setText("");
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
