package com.ucielcorp.programmerchat.presenters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ucielcorp.programmerchat.R
import com.ucielcorp.programmerchat.modals.User
import kotlinx.android.synthetic.main.item_chat.view.*
import kotlinx.android.synthetic.main.item_user.view.*

class UsersAdapter(val userClick: (User) -> Unit): RecyclerView.Adapter<UsersAdapter.UserViewHolder>()  {
    var users: List<User> = emptyList()

    fun setData(list: List<User>){
        users = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_user,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.itemView.nameUserText.text = users[position].name
        holder.itemView.emailUserText.text = users[position].email
    }

    override fun getItemCount(): Int {
        return users.size
    }

    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}