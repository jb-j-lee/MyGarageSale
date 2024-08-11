package com.myjb.dev.mygaragesale;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.myjb.dev.mygaragesale.databinding.ActivityMainBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MyActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    public static final String COMPANY = "COMPANY";
    public static final String SEARCHED = "SEARCHED";

    MyPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bindViews();
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    void bindViews() {
        binding.scanButton.setOnClickListener(v -> scanBarcode());

        binding.scanButton.setOnTouchListener((v, event) -> {
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
        });

        binding.editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                search(binding.editText.getText().toString());
            }
            return false;
        });

        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        binding.viewPager.setAdapter(pagerAdapter);
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (!TextUtils.isEmpty(binding.editText.getText().toString()))
                    search(binding.editText.getText().toString());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            ViewGroup view = (ViewGroup) binding.tabLayout.getChildAt(0);

            int childCount = view.getChildCount();
            for (int i = 0; i < childCount; i++) {
                ViewGroup viewGroup = (ViewGroup) view.getChildAt(i);
                ((TextView) viewGroup.getChildAt(1)).setAllCaps(false);
            }
        }

        if (BuildConfig.DEBUG) {
            binding.editText.postDelayed(new Runnable() {
                @Override
                public void run() {
                    binding.editText.setText("무례한 사람에게 웃으며 대처하는 법");
                    search(binding.editText.getText().toString());
                }
            }, 1000);
        }
    }

    public void search(@NonNull String text) {
        if (TextUtils.isEmpty(text)) {
            Toast.makeText(getBaseContext(), R.string.hint_edittext, Toast.LENGTH_SHORT).show();
        }

        MyFragment myFragment = (MyFragment) getSupportFragmentManager().getFragments().get(binding.viewPager.getCurrentItem());
        myFragment.search(text);
    }

    public void scanBarcode() {
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
            String text = result.getContents();
            if (!TextUtils.isEmpty(text)) {
                binding.editText.setText(text);
                search(text);
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public class MyPagerAdapter extends FragmentStatePagerAdapter {
        int[] drawableResId = {R.drawable.logo_aladin, R.drawable.logo_yes24};
        int[] tabTitles = {R.string.aladin_button, R.string.yes24_button};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                case 1:
                    MyFragment fragment = new MyFragment();
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
            image.setBounds(0, 0, image.getIntrinsicWidth() / 2, image.getIntrinsicHeight() / 2);

            SpannableString sb = new SpannableString(" " /*+ getString(tabTitles[position])*/);
            ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
            sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            return sb;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(COMPANY, binding.tabLayout.getSelectedTabPosition() + 1);
        outState.putString(SEARCHED, binding.editText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String searched = savedInstanceState.getString(SEARCHED, null);
        if (!TextUtils.isEmpty(searched))
            binding.editText.setText(searched);
        binding.viewPager.setCurrentItem(savedInstanceState.getInt(COMPANY) - 1);
    }

    void applyColorFilter(boolean apply) {
        if (apply)
            binding.scanButton.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        else
            binding.scanButton.clearColorFilter();
    }
}
