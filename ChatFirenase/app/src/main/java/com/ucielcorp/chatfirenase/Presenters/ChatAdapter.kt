package com.ucielcorp.chatfirenase.Presenters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ucielcorp.chatfirenase.Models.Message
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.ucielcorp.chatfirenase.R
import kotlinx.android.synthetic.main.item_room_chat.view.*
import java.text.SimpleDateFormat
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.hours
import kotlin.time.seconds


class ChatAdapter(private val context: Context, private val chatList : ArrayList<Message>): RecyclerView.Adapter<ChatAdapter.ChatViewModel>()  {

    private val auth = FirebaseAuth.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewModel {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_room_chat, parent, false)

        return ChatViewModel(itemView)
    }

    override fun onBindViewHolder(holder: ChatViewModel, position: Int) {
        val message: Message = chatList[position]

        // Seteamos un formato al parametro dob
        val pattern = "HH:mm"
        val simpleDataFormat = SimpleDateFormat(pattern)
        val date = simpleDataFormat.format(message.dob)

        if(auth.currentUser?.email == message.email){
            holder.itemView.contentMyMessage.visibility = View.VISIBLE
            holder.itemView.otherMessageLayout.visibility = View.GONE

            holder.myMessageText.text = message.message
            holder.nameUser.text = message.nameUser
            holder.timeMyMessage.text = date.toString()

            Glide.with(context)
                .load(message.imageProfile)
                .into(holder.imageMyProfile)

        } else {
            holder.itemView.myMessageLayout.visibility = View.GONE
            holder.itemView.contentOtherMessage.visibility = View.VISIBLE

            holder.otherMessageText.text = message.message
            holder.nameOtherUser.text = message.nameUser
            holder.timeOtherMessage.text = date.toString()

            Glide.with(context)
                .load(message.imageProfile)
                .into(holder.imageOtherProfile)

        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    class ChatViewModel(itemView: View) : RecyclerView.ViewHolder(itemView){

        // My user in the chat
        val nameUser : TextView = itemView.findViewById(R.id.nameUserText)
        val myMessageText : TextView = itemView.findViewById(R.id.myMessageText)
        val imageMyProfile : ImageView = itemView.findViewById(R.id.myImageProf)
        val timeMyMessage : TextView = itemView.findViewById(R.id.timeMyMessage)

        // Other User in the chat
        val nameOtherUser : TextView = itemView.findViewById(R.id.otherUserText)
        val otherMessageText : TextView = itemView.findViewById(R.id.otherMessageText)
        val imageOtherProfile : ImageView = itemView.findViewById(R.id.otherImageProf)
        val timeOtherMessage : TextView = itemView.findViewById(R.id.timeMyMessage)
    }
}