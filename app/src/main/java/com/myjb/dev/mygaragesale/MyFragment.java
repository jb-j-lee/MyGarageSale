package com.myjb.dev.mygaragesale;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myjb.dev.model.ServiceModel;
import com.myjb.dev.mygaragesale.databinding.FragmentMainBinding;
import com.myjb.dev.network.BookInfoItem;
import com.myjb.dev.network.PriceInquiry;
import com.myjb.dev.network.PriceInquiry2Aladin;
import com.myjb.dev.network.PriceInquiry2Yes24;
import com.myjb.dev.recyclerView.CardAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;

import static com.myjb.dev.mygaragesale.MyActivity.COMPANY;

public class MyFragment extends Fragment implements PriceInquiry.OnPriceListener {

    private FragmentMainBinding binding;

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
        binding = FragmentMainBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        bindAdapter();
        super.onViewCreated(view, savedInstanceState);
    }

    void bindAdapter() {
        recyclerAdapter = new CardAdapter(requireContext());
        binding.recyclerView.setAdapter(recyclerAdapter);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());

        if (company == ServiceModel.Company.ALADIN)
            binding.empty.setImageResource(R.drawable.logo_aladin);
        else
            binding.empty.setImageResource(R.drawable.logo_yes24);

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
        binding.empty.setVisibility(visible ? View.VISIBLE : View.GONE);
        binding.recyclerView.setVisibility(visible ? View.GONE : View.VISIBLE);
        binding.progress.setVisibility(View.GONE);
    }

    void setProgressVisibility(boolean visible) {
        binding.progress.setVisibility(visible ? View.VISIBLE : View.GONE);
//        progress.getRootView().setBackgroundDrawable(visible ? new ColorDrawable(0x7f000000) : getResources().getDrawable(R.color.colorTextWhite));
    }
}
