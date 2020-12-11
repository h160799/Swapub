package com.johnny.swapub.messageHistory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.johnny.swapub.NavigationDirections
import com.johnny.swapub.data.ChatRoom
import com.johnny.swapub.data.Message
import com.johnny.swapub.databinding.ItemMessageHistoryGridBinding
import com.johnny.swapub.home.item.HomeItemAdapter
import com.johnny.swapub.util.Logger
import com.johnny.swapub.util.UserManager
import kotlinx.android.synthetic.main.item_message_history_grid.view.*

class MessageHistoryAdapter(val onClickListener: OnClickListener, val viewModel: MessageHistoryViewModel)  :
    androidx.recyclerview.widget.ListAdapter<ChatRoom, MessageHistoryAdapter.MessageHistoryViewHolder>(
    DiffCallback
) {

    class MessageHistoryViewHolder(private var binding: ItemMessageHistoryGridBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            chatRoom: ChatRoom, viewModel: MessageHistoryViewModel
        ) {


            binding.message = chatRoom
            Logger.d("ttttt$chatRoom")

            if (chatRoom.text.isNullOrEmpty()){
                binding.messageContent.text = "傳送圖片"
            } else {
                binding.messageContent.text = chatRoom.text
            }

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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MessageHistoryViewHolder {
        return MessageHistoryViewHolder(
            ItemMessageHistoryGridBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        )
    }

    override fun onBindViewHolder(holder:MessageHistoryViewHolder, position: Int) {
        val chatRoom = getItem(position)

//        if (UserManager.userId == chatRoom.senderId) {
//            holder.itemView.user_head.visibility = View.GONE
//            viewModel.isEmpty.value = true
//        }else if (UserManager.userId == chatRoom.ownerId) {
//            holder.itemView.image_chat_owner.visibility = View.GONE
//            viewModel.isEmpty.value = true
//            } else {
//                holder.itemView.visibility = View.GONE
//                holder.itemView.layoutParams.height = 0
//        }

        holder.itemView.setOnClickListener {
            onClickListener.onClick(chatRoom)
        }
        holder.bind(chatRoom,viewModel)
    }

    class OnClickListener(val clickListener: (chatRoom: ChatRoom) -> Unit) {
        fun onClick(chatRoom: ChatRoom) = clickListener(chatRoom)
    }
}