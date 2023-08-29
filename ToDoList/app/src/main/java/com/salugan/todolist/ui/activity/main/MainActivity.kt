package com.salugan.todolist.ui.activity.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.salugan.todolist.adapter.ListBookAdapter
import com.salugan.todolist.data.Result
import com.salugan.todolist.databinding.ActivityMainBinding
import com.salugan.todolist.model.Book
import com.salugan.todolist.ui.Fragment.BookDialogFragment
import com.salugan.todolist.ui.ViewModelFactory
import com.salugan.todolist.ui.activity.detail.DetailBookActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvTodoList.layoutManager = layoutManager

        mainViewModel.listBook.observe(this) { result ->
            when(result) {
                is Result.Loading -> Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                is Result.Empty -> Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show()
                is Result.Success -> setListBook(result.data)
                is Result.Error -> Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
            }
        }

        binding.fab.setOnClickListener {
            val newFragment = BookDialogFragment()
            val bundle = Bundle()
            bundle.putBoolean(BookDialogFragment.EXTRA_OP, false)
            newFragment.arguments = bundle
            newFragment.show(supportFragmentManager, "add")
        }

    }

    private fun setListBook(books: List<Book>) {
        val listBook = arrayListOf<Book>()
        listBook.addAll(books)

        val adapter = ListBookAdapter(listBook) {
            val intent = Intent(this, DetailBookActivity::class.java)
            intent.putExtra(DetailBookActivity.EXTRA_BOOK, it.id)
            startActivity(intent)
        }
        binding.rvTodoList.adapter = adapter
    }
}