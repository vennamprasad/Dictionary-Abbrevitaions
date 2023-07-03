package com.prasad.abbreviationsfinder.view.ui.bookmark

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.prasad.abbreviationsfinder.R
import com.prasad.abbreviationsfinder.databinding.FragmentBookmarksInfoListDialogItemBinding
import com.prasad.abbreviationsfinder.databinding.FragmentBookmarksInfoListDialogBinding
import com.prasad.abbreviationsfinder.view.adpter.BookmarkListAdapter
import com.prasad.abbreviationsfinder.view.adpter.CommonListAdapter

// TODO: Customize parameter argument names
const val ARG_ITEM_COUNT = "item_count"

/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    BookmarksInfoFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 */
class BookmarksInfoFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBookmarksInfoListDialogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val adapter = CommonListAdapter()

    private val args : BookmarksInfoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBookmarksInfoListDialogBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.recyclerview.adapter  = adapter
        adapter.setList(args.bookmark.meanings)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}