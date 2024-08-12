package com.myjb.dev.mygaragesale

import android.os.AsyncTask
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import com.myjb.dev.model.ServiceModel
import com.myjb.dev.mygaragesale.databinding.FragmentMainBinding
import com.myjb.dev.network.BookInfoItem
import com.myjb.dev.network.PriceInquiry
import com.myjb.dev.network.PriceInquiry.OnPriceListener
import com.myjb.dev.network.PriceInquiry2Aladin
import com.myjb.dev.network.PriceInquiry2Yes24
import com.myjb.dev.recyclerView.CardAdapter

class MyFragment : Fragment(), OnPriceListener {
    private var binding: FragmentMainBinding? = null

    var recyclerAdapter: CardAdapter? = null

    var company: Int = 0
    var searchText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        company = ServiceModel.Company.NONE

        val bundle = arguments
        if (bundle != null) {
            company = bundle.getInt(MyActivity.Companion.COMPANY, ServiceModel.Company.NONE)
        }

        searchText = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindAdapter()
        super.onViewCreated(view, savedInstanceState)
    }

    fun bindAdapter() {
        recyclerAdapter = CardAdapter(requireContext())
        binding!!.recyclerView.adapter = recyclerAdapter
        binding!!.recyclerView.setHasFixedSize(true)
        binding!!.recyclerView.itemAnimator = DefaultItemAnimator()

        if (company == ServiceModel.Company.ALADIN) binding!!.empty.setImageResource(R.drawable.logo_aladin)
        else binding!!.empty.setImageResource(R.drawable.logo_yes24)

        setEmptyViewVisibility(true)
    }

    fun isSearched(text: String): Boolean {
        //TODO Handling Error
        return text.equals(searchText, ignoreCase = true)
    }

    fun search(text: String) {
        if (TextUtils.isEmpty(text)) {
            onPriceResult(null)
        } else {
            //TODO Handling Error
            if (isSearched(text)) return

            searchText = text

            setProgressVisibility(true)

            var priceInquiry: PriceInquiry? = null
            if (company == ServiceModel.Company.ALADIN) {
                priceInquiry = PriceInquiry2Aladin(text, this)
            } else if (company == ServiceModel.Company.YES24) {
                priceInquiry = PriceInquiry2Yes24(text, this)
            }
            priceInquiry?.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR)
        }
    }

    override fun onPriceResult(priceList: List<BookInfoItem>?) {
        setProgressVisibility(false)

        if (priceList == null || priceList.isEmpty()) {
            setEmptyViewVisibility(true)
            recyclerAdapter!!.clearView()
        } else {
            setEmptyViewVisibility(false)
            recyclerAdapter!!.updateView(priceList)
        }
    }

    fun setEmptyViewVisibility(visible: Boolean) {
        binding!!.empty.visibility = if (visible) View.VISIBLE else View.GONE
        binding!!.recyclerView.visibility =
            if (visible) View.GONE else View.VISIBLE
        binding!!.progress.visibility = View.GONE
    }

    fun setProgressVisibility(visible: Boolean) {
        binding!!.progress.visibility =
            if (visible) View.VISIBLE else View.GONE
        //        progress.getRootView().setBackgroundDrawable(visible ? new ColorDrawable(0x7f000000) : getResources().getDrawable(R.color.colorTextWhite));
    }
}
