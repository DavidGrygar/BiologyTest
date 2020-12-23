package android.example.biologytest.view.fragments

import android.example.biologytest.R
import android.example.biologytest.adapters.TopicGroupListAdapter
import android.example.biologytest.databinding.FragmentOpeningBinding
import android.example.biologytest.ui.VerticalSpaceItemDecoration
import android.example.biologytest.util.EventObserver
import android.example.biologytest.viewmodels.OpeningFragmentViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class OpeningFragment
constructor() : Fragment() {

    private val viewModel: OpeningFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentOpeningBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_opening, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = TopicGroupListAdapter(viewModel)
        binding.topicGroupList.adapter = adapter
        binding.topicGroupList.layoutManager = LinearLayoutManager(context)

        val itemDecoration = VerticalSpaceItemDecoration()
        binding.topicGroupList.addItemDecoration(itemDecoration)

        this.viewModel.topicGroups.observe(viewLifecycleOwner, Observer {
            it.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.navigateToExamFragment.observe(viewLifecycleOwner, EventObserver {
            val action = OpeningFragmentDirections.actionOpeningFragmentToExamFragment(it)
            findNavController().navigate(action)
        })
    }
}