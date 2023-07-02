package com.prasad.abbreviationsfinder.view.adpter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prasad.abbreviationsfinder.databinding.RvItemBinding
import java.util.Random


/**
 * This is ListAdapter class to display list of large forms in recyclerview.
 */
class CommonListAdapter : RecyclerView.Adapter<MainViewHolder>() {

    var mColors = arrayOf(
        "5E97F6",
        "9CCC65",
        "FF8A65",
        "9E9E9E",
        "9FA8DA",
        "90A4AE",
        "AED581",
        "F6BF26",
        "FFA726",
        "4DD0E1",
        "BA68C8",
        "A1887F"
    )
    private var resultList = mutableListOf<String>()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(lfs: List<String>) {
        this.resultList = lfs.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvItemBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val result = resultList[position]
        val i1: Int = Random().nextInt(11)
        val draw = GradientDrawable()
        draw.shape = GradientDrawable.RECTANGLE
        draw.setColor(Color.parseColor("#" + mColors[i1]))
        holder.binding.result.background = draw
        holder.binding.result.text = result
    }

    override fun getItemCount(): Int {
        return resultList.size
    }
}

class MainViewHolder(val binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root)