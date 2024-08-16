package com.myjb.dev.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import com.myjb.dev.model.Repository
import com.myjb.dev.model.data.Company
import com.myjb.dev.model.data.Value
import com.myjb.dev.model.remote.datasource.RemoteDataSource
import com.myjb.dev.mygaragesale.databinding.FragmentMainBinding
import com.myjb.dev.view.adapter.BookInfoAdapter
import com.myjb.dev.viewmodel.MainViewModel

class MainFragment : Fragment() {
    private val binding by lazy {
        FragmentMainBinding.inflate(layoutInflater).apply {
            model = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    private val viewModel: MainViewModel by lazy {
        MainViewModel(
            company = company,
            repository = Repository(dataSource = RemoteDataSource(company = company))
        )
    }

    private val adapter: BookInfoAdapter by lazy {
        BookInfoAdapter(requireContext())
    }

    private var company: Company = Company.NONE
    private var searchText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            company = bundle.getSerializable(Value.COMPANY.name) as Company
        }

        searchText = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            recyclerView.adapter = adapter
            recyclerView.itemAnimator = DefaultItemAnimator()
        }

        viewModel.list.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun isSearched(text: String): Boolean {
        //TODO Handling Error
        return text.equals(searchText, ignoreCase = true)
    }

    fun search(text: String) {
        if (text.isEmpty()) {
            adapter.submitList(mutableListOf())
        } else {
            //TODO Handling Error
            if (isSearched(text)) {
                return
            }

            searchText = text

            viewModel.getBooks(text)
        }
    }
}
