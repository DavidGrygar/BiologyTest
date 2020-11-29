package android.example.biologytest.view.fragments

import android.example.biologytest.R
import android.example.biologytest.adapters.QuestionListAdapter
import android.example.biologytest.databinding.FragmentExamBinding
import android.example.biologytest.ui.VerticalSpaceItemDecoration
import android.example.biologytest.viewmodels.ExamFragmentViewModel
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
class ExamFragment
constructor() : Fragment(R.layout.fragment_exam) {

    private val navController by lazy { findNavController() }
    private val viewModel: ExamFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentExamBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_exam, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = QuestionListAdapter(viewModel)
        binding.examList.adapter = adapter
        binding.examList.layoutManager = LinearLayoutManager(context)

        val itemDecoration = VerticalSpaceItemDecoration()

        binding.examList.addItemDecoration(itemDecoration)

        this.viewModel.questionRows.observe(viewLifecycleOwner, Observer {
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
        viewModel.navigateToExamResultFragment.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                navController.navigate(R.id.action_examFragment_to_examResultFragment)
                viewModel.doneNavigating()
            }
        })
    }
}