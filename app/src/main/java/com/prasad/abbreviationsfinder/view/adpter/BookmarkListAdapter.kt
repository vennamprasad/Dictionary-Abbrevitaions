package com.prasad.abbreviationsfinder.view.adpter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prasad.abbreviationsfinder.databinding.ChildListItemBinding
import com.prasad.abbreviationsfinder.databinding.RvItemExtBinding
import com.prasad.abbreviationsfinder.model.Bookmarks
import com.prasad.abbreviationsfinder.utils.mColors
import java.util.Random


/**
 * This is ListAdapter class to display list of large forms in recyclerview.
 */

class BookmarkListAdapter : RecyclerView.Adapter<BookmarkViewHolder>() {

    private var resultList = mutableListOf<Bookmarks>()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Bookmarks>) {
        this.resultList = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvItemExtBinding.inflate(inflater, parent, false)
        return BookmarkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val result = resultList[position]
        val i1: Int = Random().nextInt(11)
        val draw = GradientDrawable()
        draw.shape = GradientDrawable.RECTANGLE
        draw.setColor(Color.parseColor("#" + mColors[i1]))
        holder.pBinding.result.background = draw
        holder.pBinding.arrow.background = draw
        holder.pBinding.result.text = result.word
    }

    override fun getItemCount(): Int {
        return resultList.size
    }
}

class BookmarkViewHolder(val pBinding: RvItemExtBinding) : RecyclerView.ViewHolder(pBinding.root)
class MeaningsViewHolder(private val cBinding: ChildListItemBinding):RecyclerView.ViewHolder(cBinding.root)