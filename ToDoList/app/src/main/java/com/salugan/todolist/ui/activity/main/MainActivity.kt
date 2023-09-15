package com.salugan.todolist.ui.activity.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kennyc.view.MultiStateView
import com.salugan.todolist.adapter.ListBookAdapter
import com.salugan.todolist.data.BookRepository
import com.salugan.todolist.data.Result
import com.salugan.todolist.databinding.ActivityMainBinding
import com.salugan.todolist.model.Book
import com.salugan.todolist.ui.Fragment.BookDialogFragment
import com.salugan.todolist.ui.activity.detail.DetailBookActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MultiStateView.StateListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var multiStateView: MultiStateView

    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        multiStateView = binding.mainMultiStateView
        multiStateView.listener = this

        val layoutManager = LinearLayoutManager(this)
        binding.rvTodoList.layoutManager = layoutManager

        mainViewModel.listBook.observe(this) { result ->
            when(result) {
                is Result.Loading -> multiStateView.viewState = MultiStateView.ViewState.LOADING
                is Result.Empty -> multiStateView.viewState = MultiStateView.ViewState.EMPTY
                is Result.Success -> {
                    multiStateView.viewState = MultiStateView.ViewState.CONTENT
                    setListBook(result.data)
                }
                is Result.Error -> multiStateView.viewState = MultiStateView.ViewState.ERROR
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

    override fun onStateChanged(viewState: MultiStateView.ViewState) {
        Log.v("MSVSample", "onStateChanged; viewState: $viewState")
    }
}