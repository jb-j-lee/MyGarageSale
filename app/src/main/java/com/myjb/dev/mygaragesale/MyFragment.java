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
public class MyFragment extends Fragment implements PriceInquiry.OnPriceListener {

    @ViewById(R.id.recyclerView)
    RecyclerView recyclerView;

    @ViewById(R.id.empty)
    ImageView empty;

    @ViewById(R.id.progress)
    ContentLoadingProgressBar progress;

    @Bean
    CardAdapter recyclerAdapter;

    int company;
    String searchText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        company = ServiceModel.Company.NONE;

        Bundle bundle = getArguments();
        if (bundle != null) {
            company = bundle.getInt(COMPANY, ServiceModel.Company.NONE);
        }

        searchText = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @AfterViews
    void bindAdapter() {
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if (company == ServiceModel.Company.ALADIN)
            empty.setImageResource(R.drawable.logo_aladin);
        else
            empty.setImageResource(R.drawable.logo_yes24);

        setEmptyViewVisibility(true);
    }

    boolean isSearched(String text) {
        //TODO Handling Error
        return text.equalsIgnoreCase(searchText);
    }

    public void search(String text) {
        if (TextUtils.isEmpty(text)) {
            onPriceResult(null);
        } else {
            //TODO Handling Error
            if (isSearched(text))
                return;

            searchText = text;

            setProgressVisibility(true);

            PriceInquiry priceInquiry = null;
            if (company == ServiceModel.Company.ALADIN) {
                priceInquiry = new PriceInquiry2Aladin(text, this);
            } else if (company == ServiceModel.Company.YES24) {
                priceInquiry = new PriceInquiry2Yes24(text, this);
            }
            if (priceInquiry != null)
                priceInquiry.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        }
    }

    @Override
    public void onPriceResult(List<BookInfoItem> priceList) {
        setProgressVisibility(false);

        if (priceList == null || priceList.isEmpty()) {
            setEmptyViewVisibility(true);
            recyclerAdapter.clearView();
        } else {
            setEmptyViewVisibility(false);
            recyclerAdapter.updateView(priceList);
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
