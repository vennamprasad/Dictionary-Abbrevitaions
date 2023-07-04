package com.prasad.abbreviationsfinder.view.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import com.prasad.abbreviationsfinder.databinding.FragmentBookmarkBinding
import com.prasad.abbreviationsfinder.model.Bookmarks
import com.prasad.abbreviationsfinder.view.SwipeToDeleteCallback
import com.prasad.abbreviationsfinder.view.adpter.BookmarkListAdapter
import com.prasad.abbreviationsfinder.view.adpter.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment : Fragment(), OnItemClickListener {

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
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        binding.recyclerview.layoutManager = LinearLayoutManager(context)
        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = binding.recyclerview.adapter as BookmarkListAdapter
                val removedWord = adapter.removeAt(viewHolder.absoluteAdapterPosition)
                DynamicToast.makeSuccess(requireContext(), "$removedWord Removed").show()
                bookmarkViewModel.deleteBookmark(removedWord)
            }

        }
        ItemTouchHelper(swipeHandler).attachToRecyclerView(binding.recyclerview)
        binding.recyclerview.adapter = adapter
        binding.viewmodel = bookmarkViewModel
        binding.lifecycleOwner = this
        bookmarkViewModel.bookmarkFlowData.observe(viewLifecycleOwner) { allBookmarks ->
            allBookmarks?.let { it ->
                val tempList: MutableList<Bookmarks> = mutableListOf()
                it.forEach {
                    tempList.add(Bookmarks(it.word, it.meanings))
                }
                adapter.setList(tempList)
                adapter.setListener(this)
                bookmarkViewModel.rvVisibility.postValue(View.VISIBLE)
            }
        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DynamicToast.makeSuccess(requireContext(),"Swipe Left to Delete the Bookmark").show()
    }

    override fun onItemClick(item: Bookmarks) {
        val direction =
            BookmarkFragmentDirections.actionNavigationBookmarksToBookmarksInfoFragment(item)
        findNavController().navigate(direction)
    }

}
