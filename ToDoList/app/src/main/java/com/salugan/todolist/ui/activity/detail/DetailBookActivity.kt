package com.salugan.todolist.ui.activity.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.salugan.todolist.data.Result
import com.salugan.todolist.databinding.ActivityDetailBookBinding
import com.salugan.todolist.model.Book
import com.salugan.todolist.ui.Fragment.BookDialogFragment
import com.salugan.todolist.ui.ViewModelFactory

class DetailBookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBookBinding

    private val detailBookViewModel: DetailBookViewModel by viewModels {
        ViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bookId = intent.getStringExtra(EXTRA_BOOK) ?: ""

        detailBookViewModel.getSpecificBook(bookId).observe(this) { result ->
            when (result) {
                is Result.Loading -> ""
                is Result.Empty -> ""
                is Result.Success -> setView(result.data)
                is Result.Error -> ""
            }
        }
    }

    private fun setView(book: Book) {
        binding.apply {
            Glide.with(this@DetailBookActivity)
                .load(book.cover)
                .into(ivCoverDetail)
            tvJudul.text = book.judul
            tvNamaPenulisDt.text = book.namaPenulis
            tvTahun.text = book.tahunTerbit.toString()
            tvKategori.text = book.kategori

            btnDelete.setOnClickListener {
                AlertDialog.Builder(this@DetailBookActivity)
                    .setMessage("Ingin delete?")
                    .setCancelable(true)
                    .setPositiveButton("Yes") { _, _ ->
                        detailBookViewModel.deleteBook(book)
                        finish()
                    }
                    .setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }.create().show()
            }

            btnUpdate.setOnClickListener {
                val newFragment = BookDialogFragment()
                val bundle = Bundle()
                bundle.putParcelable(BookDialogFragment.EXTRA_USER, book)
                bundle.putBoolean(BookDialogFragment.EXTRA_OP, true)
                newFragment.arguments = bundle
                newFragment.show(supportFragmentManager, "edit")
            }
        }
    }

    companion object {
        const val EXTRA_BOOK = "extra_book"
    }
}