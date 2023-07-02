package com.prasad.abbreviationsfinder.view.ui.dictionary

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import com.prasad.abbreviationsfinder.databinding.FragmentDictionaryBinding
import com.prasad.abbreviationsfinder.db.entity.BookmarkEntity
import com.prasad.abbreviationsfinder.utils.ValidationUtil
import com.prasad.abbreviationsfinder.utils.ValidationUtil.EMPTY_WORD_MESSAGE
import com.prasad.abbreviationsfinder.utils.ValidationUtil.RESPONSE_ERROR_MESSAGE
import com.prasad.abbreviationsfinder.view.adpter.CommonListAdapter
import com.prasad.abbreviationsfinder.view.ui.bookmark.BookmarkViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DictionaryFragment : Fragment() {

    private var _binding: FragmentDictionaryBinding? = null
    private val adapter = CommonListAdapter()
    private val dictionaryViewModel by viewModels<DictionaryViewModel>()
    private val bookmarkViewModel by viewModels<BookmarkViewModel>()
    private val bookmarkData = ArrayList<String>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDictionaryBinding.inflate(inflater, container, false)
        binding.recyclerview.adapter = adapter
        binding.viewmodel = dictionaryViewModel
        binding.lifecycleOwner = this

        dictionaryViewModel.meaningsList.observe(requireActivity()) {
            adapter.setList(it)
            bookmarkData.clear()
            bookmarkData.addAll(it)
            dictionaryViewModel.rvVisibility.postValue(View.VISIBLE)
        }

        dictionaryViewModel.errorMessage.observe(requireActivity()) {
            dictionaryViewModel.rvVisibility.postValue(View.GONE)
            DynamicToast.makeError(requireContext(), it.toString()).show()
        }

        binding.abbEditText.doAfterTextChanged {
            dictionaryViewModel.rvVisibility.postValue(View.GONE)
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
        binding.bookMark.setOnClickListener {
            if (bookmarkData.isNotEmpty() && binding.abbEditText.text.toString().isNotEmpty()) {
                performBookmark(binding.abbEditText.text.toString())
            } else {
                DynamicToast.makeWarning(
                    requireContext(),
                    "$RESPONSE_ERROR_MESSAGE or $EMPTY_WORD_MESSAGE"
                ).show()
            }
        }

        binding.resetBtn.setOnClickListener {
            binding.abbEditText.text?.clear()
            dictionaryViewModel.rvVisibility.postValue(View.GONE)
        }
        return binding.root
    }

    private fun performBookmark(word: String) {
        bookmarkViewModel.insertBookmark(BookmarkEntity(word = word, meanings = bookmarkData))
        Snackbar.make(binding.root, "Bookmark Saved ", Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun performSearch() {
        binding.abbEditText.hideKeyboard()
        val word = binding.abbEditText.text.toString()
        val isValidAbbreviation = ValidationUtil.isValidWord(word)

        when {
            !isValidAbbreviation.first -> {
                Toast.makeText(requireContext(), isValidAbbreviation.second, Toast.LENGTH_LONG)
                    .show()
            }

            else -> {
                dictionaryViewModel.getMeaningsData(word)
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