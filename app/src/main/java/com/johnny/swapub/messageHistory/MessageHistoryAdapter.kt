package com.johnny.swapub.messageHistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.johnny.swapub.data.ChatRoom
import com.johnny.swapub.databinding.ItemMessageHistoryGridBinding

class MessageHistoryAdapter  : androidx.recyclerview.widget.ListAdapter<ChatRoom,
        MessageHistoryAdapter.MessageHistoryViewHolder>(
    DiffCallback
) {

    class MessageHistoryViewHolder(private var binding: ItemMessageHistoryGridBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chatRoom: ChatRoom) {
            binding.message = chatRoom
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ChatRoom>() {
        override fun areItemsTheSame(oldItem: ChatRoom, newItem: ChatRoom): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ChatRoom, newItem: ChatRoom): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MessageHistoryViewHolder {
        return MessageHistoryViewHolder(
            ItemMessageHistoryGridBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        )
    }

    override fun onBindViewHolder(holder:MessageHistoryViewHolder, position: Int) {
        val chatRoom = getItem(position)
        holder.bind(chatRoom)
    }


}