package com.myjb.dev.mygaragesale;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.myjb.dev.view.ClearEditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MyActivity extends AppCompatActivity {

    public static final String COMPANY = "COMPANY";

    @ViewById(R.id.scan_button)
    ImageButton scanButton;

    @ViewById(R.id.editText)
    ClearEditText editText;

    @ViewById(R.id.tabLayout)
    TabLayout tabLayout;

    @ViewById(R.id.viewPager)
    ViewPager viewPager;

    MyPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
    }

    @AfterViews
    void bindViews() {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    search(editText.getText().toString());
                }
                return false;
            }
        });

        scanButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        applyColorFilter(true);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        applyColorFilter(v.isPressed());
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_OUTSIDE:
                    default:
                        applyColorFilter(false);
                        break;
                }
                return false;
            }
        });

        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        if (BuildConfig.DEBUG) {
            editText.postDelayed(new Runnable() {
                @Override
                public void run() {
                    search("docker");
                }
            }, 1000);
        }
    }

    public void search(@NonNull String text) {
        if (TextUtils.isEmpty(text)) {
            Toast.makeText(getBaseContext(), R.string.hint_edittext, Toast.LENGTH_SHORT).show();
        }

        MyFragment myFragment = (MyFragment) getSupportFragmentManager().getFragments().get(viewPager.getCurrentItem());
        myFragment.search(text);
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
//                editText.setText(result.getContents());
                search(result.getContents());
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public class MyPagerAdapter extends FragmentStatePagerAdapter {
        int[] drawableResId = {R.drawable.logo_aladin, R.drawable.logo_yes24};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                case 1:
                    MyFragment fragment = new MyFragment_();
                    Bundle bundle = new Bundle();
                    bundle.putInt(COMPANY, position + 1);
                    fragment.setArguments(bundle);
                    return fragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            Drawable image = getResources().getDrawable(drawableResId[position]);
            image.setBounds(0, 0, image.getIntrinsicWidth() / 3, image.getIntrinsicHeight() / 3);

            SpannableString sb = new SpannableString(" "/* + getString(tabTitles[position])*/);
            ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
            sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            return sb;
        }

        //TODO
        @Override
        public int getItemPosition(@NonNull Object object) {
            if (isChangingConfigurations())
                return POSITION_UNCHANGED;
            return super.getItemPosition(object);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(COMPANY, tabLayout.getSelectedTabPosition() + 1);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        viewPager.setCurrentItem(savedInstanceState.getInt(COMPANY) - 1);
    }

    void applyColorFilter(boolean apply) {
        if (apply)
            scanButton.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        else
            scanButton.clearColorFilter();
    }
}
