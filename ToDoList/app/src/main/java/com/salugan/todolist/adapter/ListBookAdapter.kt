package com.salugan.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.salugan.todolist.databinding.TodoItemBinding
import com.salugan.todolist.model.Book

class ListBookAdapter(private val listBook: ArrayList<Book>)
    : RecyclerView.Adapter<ListBookAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listBook.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val buku = listBook[position]
        holder.bind(buku)
    }

    inner class ViewHolder(val binding: TodoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book) {
            binding.apply {
                Glide.with(itemView)
                    .load(book.cover)
                    .into(ivCover)

                tvJudulBuku.text = book.judul
                tvNamaPenulis.text = book.namaPenulis
            }
        }
    }
}