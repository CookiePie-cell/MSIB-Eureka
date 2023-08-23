package com.salugan.githubuser.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.salugan.githubuser.data.remote.model.responses.UserResponse
import com.salugan.githubuser.databinding.ItemUserBinding
import com.salugan.githubuser.ui.activities.detail.DetailUserActivity

class ListUserAdapter(private val listUsers: List<UserResponse>)
    : RecyclerView.Adapter<ListUserAdapter.UserViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user: UserResponse = listUsers[position]

        holder.binding.username.text = user.login
        Glide.with(holder.context)
            .load(user.avatarUrl)
            .into(holder.binding.userAvatar)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.context, DetailUserActivity::class.java)
            intent.putExtra(DetailUserActivity.EXTRA_USERNAME, holder.binding.username.text)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listUsers.size

    class UserViewHolder(var binding: ItemUserBinding, var context: Context) : RecyclerView.ViewHolder(binding.root)
}