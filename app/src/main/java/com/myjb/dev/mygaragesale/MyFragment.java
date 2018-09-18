package com.myjb.dev.mygaragesale;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.myjb.dev.model.ServiceModel;
import com.myjb.dev.network.BookInfoItem;
import com.myjb.dev.network.PriceInquiry;
import com.myjb.dev.network.PriceInquiry2Aladin;
import com.myjb.dev.network.PriceInquiry2Yes24;
import com.myjb.dev.recyclerView.CardAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import static com.myjb.dev.mygaragesale.MyActivity.COMPANY;

@EFragment(R.layout.fragment_main)
public class MyFragment extends Fragment implements CardAdapter.OnItemClickListener, PriceInquiry.OnPriceListener {

    @ViewById(R.id.recyclerView)
    RecyclerView recyclerView;

    @ViewById(R.id.empty)
    ImageView empty;

    @ViewById(R.id.progress)
    ContentLoadingProgressBar progress;

    @Bean
    CardAdapter recyclerAdapter;

    private int company;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        company = ServiceModel.Company.NONE;

        Bundle bundle = getArguments();
        if (bundle != null) {
            company = bundle.getInt(COMPANY, ServiceModel.Company.NONE);
        }

        Log.e("TEST", "[onCreate] company : " + company);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("TEST", "[onCreateView] company : " + company);
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @AfterViews
    void bindAdapter() {
        Log.e("TEST", "[bindAdapter] company : " + company);

        recyclerAdapter.setOnItemClickListener(this);

        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if (company == ServiceModel.Company.ALADIN)
            empty.setImageResource(R.drawable.logo_aladin);
        else
            empty.setImageResource(R.drawable.logo_yes24);

        setEmptyViewVisibility(true);
    }

    @Override
    public void onItemClick(String name, String isbn, final int position) {
    }

    @Override
    public void onDestroyView() {
        Log.e("TEST", "[onDestroyView] company : " + company);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.e("TEST", "[onDestroy] company : " + company);
        super.onDestroy();
    }

    public void search(String text) {
        setProgressVisibility(true);

        if (TextUtils.isEmpty(text)) {
            onPriceResult(null);
        } else {
            PriceInquiry priceInquiry = null;
            if (company == ServiceModel.Company.ALADIN) {
                priceInquiry = new PriceInquiry2Aladin(text, this);
            } else if (company == ServiceModel.Company.YES24) {
                priceInquiry = new PriceInquiry2Yes24(text, this);
            }
            priceInquiry.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        }
    }

    @Override
    public void onPriceResult(@NonNull List<BookInfoItem> priceList) {
        setProgressVisibility(false);

        if (priceList != null && !priceList.isEmpty()) {
            setEmptyViewVisibility(false);
            recyclerAdapter.updateView(priceList);
        } else {
            setEmptyViewVisibility(true);
            recyclerAdapter.clearView();
        }
    }

    void setEmptyViewVisibility(boolean visible) {
        empty.setVisibility(visible ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(visible ? View.GONE : View.VISIBLE);
        progress.setVisibility(View.GONE);
    }

    void setProgressVisibility(boolean visible) {
        progress.setVisibility(visible ? View.VISIBLE : View.GONE);
//        progress.getRootView().setBackgroundDrawable(visible ? new ColorDrawable(0x7f000000) : getResources().getDrawable(R.color.colorTextWhite));
    }
}
