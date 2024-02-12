package com.prasad.abbreviationsfinder.view.adpter

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.prasad.abbreviationsfinder.databinding.RvItemBinding


/**
 * This is ListAdapter class to display list of large forms in recyclerview.
 */
class CommonListAdapter : RecyclerView.Adapter<MainViewHolder>() {

    private var resultList = mutableListOf<String>()
    var context: Context? = null
    @SuppressLint("NotifyDataSetChanged")
    fun setList(lfs: List<String>,context: Context?) {
        this.resultList = lfs.toMutableList()
        this.context = context
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvItemBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val result = resultList[position]
        holder.binding.result.text = result
        holder.binding.result.setOnClickListener {
            try {
                context?.copyToClipboard(result)
                Toast.makeText(holder.itemView.context, "$result copied to clipboard", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                TODO("Not yet implemented")
            }
        }
    }

    override fun getItemCount(): Int {
        return resultList.size
    }
}

class MainViewHolder(val binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root)

fun Context.copyToClipboard(text: CharSequence){
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label",text)
    clipboard.setPrimaryClip(clip)
}