package com.salugan.userlist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.salugan.userlist.databinding.UserItemBinding

class UserAdapter(
    private val context: Context,
    private val listUser: List<User>,
    private val onClick: (User) -> Unit
    ) : RecyclerView.Adapter<UserAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    inner class ViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.apply {
                tvName.text = user.nama
                tvJurusan.text = context.resources.getString(R.string.jurusan, user.jurusan)
                tvPhoto.setImageResource(user.photo)
            }
            itemView.setOnClickListener { onClick(user) }
        }
    }
}