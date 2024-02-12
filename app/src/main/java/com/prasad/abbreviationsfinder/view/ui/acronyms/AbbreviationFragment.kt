package com.prasad.abbreviationsfinder.view.ui.acronyms

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import com.prasad.abbreviationsfinder.databinding.FragmentAbbreviationsBinding
import com.prasad.abbreviationsfinder.utils.ValidationUtil
import com.prasad.abbreviationsfinder.view.adpter.CommonListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AbbreviationFragment : Fragment() {

    private var _binding: FragmentAbbreviationsBinding? = null
    private val viewModel by viewModels<AbbreviationViewModel>()
    private val adapter = CommonListAdapter()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAbbreviationsBinding.inflate(inflater, container, false)
        binding.recyclerview.adapter = adapter
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        viewModel.largeFormList.observe(requireActivity()) {
            adapter.setList(it,requireContext())
            viewModel.rvVisibility.postValue(View.VISIBLE)
        }

        viewModel.errorMessage.observe(requireActivity()) {
            viewModel.rvVisibility.postValue(View.GONE)
            DynamicToast.makeError(requireContext(), "Technical Issues From Server").show()
        }

        binding.abbEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
            }
            true
        }

        binding.searchBtn.setOnClickListener {
            performSearch()
        }

        binding.resetBtn.setOnClickListener {
            binding.abbEditText.text?.clear()
            viewModel.rvVisibility.postValue(View.GONE)
        }
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun performSearch() {
        binding.abbEditText.hideKeyboard()
        val abbreviation = binding.abbEditText.text.toString()
        val isValidAbbreviation = ValidationUtil.isValidShorForm(abbreviation)

        when {
            !isValidAbbreviation.first -> {
                Toast.makeText(requireContext(), isValidAbbreviation.second, Toast.LENGTH_LONG)
                    .show()
            }

            else -> {
                viewModel.getMeaningsData(abbreviation)
                binding.recyclerview.smoothScrollToPosition(0)
            }
        }
    }

    private fun View.hideKeyboard() {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }
}