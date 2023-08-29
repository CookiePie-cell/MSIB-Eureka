package com.salugan.todolist.ui.Fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.salugan.todolist.R
import com.salugan.todolist.databinding.AddDialogBinding
import com.salugan.todolist.model.Book
import com.salugan.todolist.ui.ViewModelFactory

class BookDialogFragment : DialogFragment() {

    private lateinit var binding: AddDialogBinding

    private val bookFragmentViewModel: BookFragmentViewModel by viewModels {
        ViewModelFactory.getInstance()
    }

    private var isEdit: Boolean = false
    private var book: Book? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = AddDialogBinding.inflate(layoutInflater)

        if (arguments != null) {
            isEdit = arguments?.getBoolean(EXTRA_OP, false) ?: false
            book = if(Build.VERSION.SDK_INT >= 33) {
                arguments?.getParcelable(EXTRA_USER, Book::class.java)
            } else {
                @Suppress("DEPRECATION")
                arguments?.getParcelable(EXTRA_USER)
            }
        }
        checkInput()

        val builder = AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .setPositiveButton(R.string.ok) { dialog, id ->
                binding.apply {
                    Log.d("zxczxc", book.toString())

                    val cover = edtCover.text.toString()
                    val judul = edtJudul.text.toString()
                    val namaPenulis = edtNamaPenulis.text.toString()
                    val tahun = if (edtTahun.text.toString().isNotEmpty()) edtTahun.text.toString().toInt() else 0
                    val kategori = edtKategori.text.toString()

                    if (cover.isNotEmpty() && judul.isNotEmpty() && namaPenulis.isNotEmpty() && tahun != 0 && kategori.isNotEmpty()) {
                        val bookId = book?.id
                        val addedBook = Book(bookId, cover, judul, namaPenulis, tahun, kategori)

                        if (!isEdit) {
                            bookFragmentViewModel.addBook(addedBook)
                        } else {
                            bookFragmentViewModel.editBook(addedBook)
                        }
                    }
                }
            }
            .setNegativeButton(R.string.cancel) { dialog, id ->
                dialog.cancel()
            }

        return builder.create()
    }

    private fun checkInput() {
        if (book == null) return
        binding.apply {
            edtCover.setText(book?.cover)
            edtJudul.setText(book?.judul)
            edtNamaPenulis.setText(book?.namaPenulis)
            edtTahun.setText(book?.tahunTerbit.toString())
            edtKategori.setText(book?.kategori)
        }
    }

    companion object {
        const val EXTRA_OP = "extra_op"
        const val EXTRA_USER = "extra_user"
    }
}