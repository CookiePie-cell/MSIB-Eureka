package com.salugan.todolist.ui.Fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.salugan.todolist.R
import com.salugan.todolist.databinding.AddDialogBinding
import com.salugan.todolist.model.Book
import com.salugan.todolist.ui.ViewModelFactory

class AddBookDialogFragment : DialogFragment() {

    private lateinit var binding: AddDialogBinding

    private val addBookFragmentViewModel: AddBookFragmentViewModel by viewModels {
        ViewModelFactory.getInstance()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = AddDialogBinding.inflate(layoutInflater)

        val builder = AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .setPositiveButton(R.string.ok) { dialog, id ->
                binding.apply {
                    val cover = edtCover.text.toString()
                    val judul = edtJudul.text.toString()
                    val namaPenulis = edtNamaPenulis.text.toString()
                    val tahun = edtTahun.text.toString().toInt()
                    val kategori = edtKategori.text.toString()

                    val book = Book("", cover, judul, namaPenulis, tahun, kategori)
                    addBookFragmentViewModel.addBook(book)
                }
            }
            .setNegativeButton(R.string.cancel) { dialog, id ->
                dialog.cancel()
            }

        return builder.create()
    }
}