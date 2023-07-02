package com.prasad.abbreviationsfinder.view.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import com.prasad.abbreviationsfinder.databinding.FragmentBookmarkBinding
import com.prasad.abbreviationsfinder.db.entity.BookmarkEntity
import com.prasad.abbreviationsfinder.model.Bookmarks
import com.prasad.abbreviationsfinder.view.adpter.BookmarkListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    private val bookmarkViewModel by viewModels<BookmarkViewModel>()
    private val adapter = BookmarkListAdapter()
    private var _binding: FragmentBookmarkBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater,container,false)
        binding.recyclerview.adapter = adapter
        binding.viewmodel = bookmarkViewModel
        binding.lifecycleOwner = this

        bookmarkViewModel.bookmarkFlowData.observe(viewLifecycleOwner) { allBookmarks ->
            allBookmarks?.let { it ->
                val tempList :MutableList<Bookmarks> = mutableListOf()
                it.forEach {
                    tempList.add(Bookmarks(it.word,it.meanings,false))
                }
                adapter.setList(tempList)
                bookmarkViewModel.rvVisibility.postValue(View.VISIBLE)
            }
        }

        bookmarkViewModel.errorMessage.observe(requireActivity()) {
            bookmarkViewModel.rvVisibility.postValue(View.GONE)
            DynamicToast.makeError(requireContext(), it.toString()).show()
        }
        return binding.root
    }


}