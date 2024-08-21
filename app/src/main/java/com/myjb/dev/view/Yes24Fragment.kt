package com.myjb.dev.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import com.myjb.dev.mygaragesale.databinding.FragmentYes24Binding
import com.myjb.dev.view.adapter.BookInfoAdapter
import com.myjb.dev.viewmodel.Yes24ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Yes24Fragment : Fragment() {
    private val binding by lazy {
        FragmentYes24Binding.inflate(layoutInflater).apply {
            model = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    private val viewModel: Yes24ViewModel by viewModels()

    private val adapter: BookInfoAdapter by lazy {
        BookInfoAdapter(requireContext())
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
    }

    fun search(text: String) {
        if (text.isEmpty()) {
            adapter.submitList(mutableListOf())
        } else {
            viewModel.getBooks(text = text)
        }
    }
}