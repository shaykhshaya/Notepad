package com.shaya.notepad.ui.adapter

import android.app.LauncherActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shaya.notepad.databinding.ListItemBinding
import com.shaya.notepad.model.Item

class ItemListAdapter(private val onClickListener: (Item)-> Unit): ListAdapter<Item, ItemListAdapter.ItemViewHolder>(DiffCallback)
{


    class ItemViewHolder(private var binding: ListItemBinding): RecyclerView.ViewHolder(binding.root)  {
    fun bind(item:Item){
    binding.item = item
    binding.executePendingBindings()
}
    }


    companion object DiffCallback: DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(
            ListItemBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener(item)
        }
        holder.bind(item)
    }


}


