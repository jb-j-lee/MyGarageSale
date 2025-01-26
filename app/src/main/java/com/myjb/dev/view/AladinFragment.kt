package com.myjb.dev.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import com.myjb.dev.mygaragesale.databinding.FragmentAladinBinding
import com.myjb.dev.util.Logger
import com.myjb.dev.view.adapter.BookInfoAdapter
import com.myjb.dev.viewmodel.AladinViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "AladinFragment"

@AndroidEntryPoint
class AladinFragment : Fragment() {
    private val binding by lazy {
        FragmentAladinBinding.inflate(layoutInflater).apply {
            model = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    private val viewModel: AladinViewModel by viewModels()

    private val adapter: BookInfoAdapter by lazy {
        BookInfoAdapter(requireContext())
    }

    private val args by navArgs<AladinFragmentArgs>()

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

        val text = args.aladinArgument
        search(text = text)
    }

    fun search(text: String) {
        Logger.e(TAG, "[search] text : $text")

        viewModel.getBooks(text = text)
    }

    override fun onPrimaryNavigationFragmentChanged(isPrimaryNavigationFragment: Boolean) {
        super.onPrimaryNavigationFragmentChanged(isPrimaryNavigationFragment)

        Logger.e(
            TAG,
            "[onPrimaryNavigationFragmentChanged] isPrimaryNavigationFragment : $isPrimaryNavigationFragment"
        )
    }
}