package com.prasad.abbreviationsfinder.view.adpter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prasad.abbreviationsfinder.databinding.RvItemBinding
import com.prasad.abbreviationsfinder.model.Bookmarks


/**
 * This is ListAdapter class to display list of large forms in recyclerview.
 */
class BookmarkListAdapter : RecyclerView.Adapter<BookmarkViewHolder>() {

    private var resultList = mutableListOf<Bookmarks>()
    private var listener :OnItemClickListener?=null

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Bookmarks>) {
        this.resultList = list.toMutableList()
        notifyDataSetChanged()
    }

    fun setListener(listener: OnItemClickListener){
        this.listener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvItemBinding.inflate(inflater, parent, false)
        return BookmarkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val result = resultList[position]
        holder.pBinding.result.text = result.word
        holder.pBinding.result.setOnClickListener {
            listener?.onItemClick(result)
        }
    }

    override fun getItemCount(): Int {
        return resultList.size
    }

    fun removeAt(position: Int): String {
        val word = resultList[position].word
        resultList.removeAt(position)
        notifyItemRemoved(position)
        return word
    }
}

class BookmarkViewHolder(val pBinding: RvItemBinding) : RecyclerView.ViewHolder(pBinding.root)