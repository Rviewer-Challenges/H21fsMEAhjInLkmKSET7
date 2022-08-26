package com.ucielcorp.chatfirenase.Presenters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
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


class ChatAdapter(private val chatList : ArrayList<Message>): RecyclerView.Adapter<ChatAdapter.ChatViewModel>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewModel {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_room_chat, parent, false)

        return ChatViewModel(itemView)
    }

    override fun onBindViewHolder(holder: ChatViewModel, position: Int) {
        val message: Message = chatList[position]

        holder.bind(message)
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    class ChatViewModel(itemView: View) : RecyclerView.ViewHolder(itemView){

        // My user in the chat
        private val myMessageLayout: ConstraintLayout = itemView.findViewById(R.id.myMessageLayout)
        private val nameUser : TextView = itemView.findViewById(R.id.nameUserText)
        private val myMessageText : TextView = itemView.findViewById(R.id.myMessageText)
        private val imageMyProfile : ImageView = itemView.findViewById(R.id.myImageProf)
        private val timeMyMessage : TextView = itemView.findViewById(R.id.timeMyMessage)

        // Other User in the chat
        private val otherMessageLayout: ConstraintLayout = itemView.findViewById(R.id.otherMessageLayout)
        private val nameOtherUser : TextView = itemView.findViewById(R.id.otherUserText)
        private val otherMessageText : TextView = itemView.findViewById(R.id.otherMessageText)
        private val imageOtherProfile : ImageView = itemView.findViewById(R.id.otherImageProf)
        private val timeOtherMessage : TextView = itemView.findViewById(R.id.timeOtherMessage)

        fun bind(message: Message){

            val auth = FirebaseAuth.getInstance()

            // Seteamos un formato al parametro dob
            val pattern = "HH:mm"
            val simpleDataFormat = SimpleDateFormat(pattern)
            val date = simpleDataFormat.format(message.dob)

            if(auth.currentUser?.email == message.email){

                myMessageLayout.visibility = View.VISIBLE
                otherMessageLayout.visibility = View.GONE

                myMessageText.text = message.message
                nameUser.text = message.nameUser
                timeMyMessage.text = date.toString()

                Glide.with(imageMyProfile.context)
                    .load(message.imageProfile)
                    .into(imageMyProfile)

            } else {

                myMessageLayout.visibility = View.GONE
                otherMessageLayout.visibility = View.VISIBLE

                otherMessageText.text = message.message
                nameOtherUser.text = message.nameUser
                timeOtherMessage.text = date.toString()

                Glide.with(imageOtherProfile.context)
                    .load(message.imageProfile)
                    .into(imageOtherProfile)
            }
        }
    }
}