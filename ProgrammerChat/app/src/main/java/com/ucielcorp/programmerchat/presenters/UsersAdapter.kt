package com.ucielcorp.programmerchat.presenters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ucielcorp.programmerchat.R
import com.ucielcorp.programmerchat.modals.User
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_chat.view.*
import kotlinx.android.synthetic.main.item_user.view.*

class UsersAdapter(private val context: Context,private val userList : ArrayList<User>): RecyclerView.Adapter<UsersAdapter.UserViewModel>() {

    // Configuración para permitir hacer click en los elementos del RecyclerView
    private lateinit var uListener : onItemClickListner

    interface onItemClickListner{
        fun onItemClick(position: Int)
    }

    fun setOnClickListener(listener: onItemClickListner){
        uListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewModel {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_user, parent, false)

        return UserViewModel(itemView, uListener)
    }

    override fun onBindViewHolder(holder: UserViewModel, position: Int) {
        val user : User = userList[position]
        holder.nameUser.text = user.name
        holder.emailUser.text = user.email

        // Glide para el Logo
        Glide.with(context)
            .load(user.image)
            .into(holder.imageProfUser)
    }

    override fun getItemCount(): Int {
       return userList.size
    }

    class UserViewModel(itemView: View, listener : onItemClickListner) : RecyclerView.ViewHolder(itemView){

        val nameUser : TextView = itemView.findViewById(R.id.nameUserText)
        val emailUser : TextView = itemView.findViewById(R.id.emailUserText)
        val imageProfUser : CircleImageView = itemView.findViewById(R.id.imageProfile)

        init{
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}
