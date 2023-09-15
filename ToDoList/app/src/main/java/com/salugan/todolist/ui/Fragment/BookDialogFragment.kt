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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookDialogFragment : DialogFragment() {

    private lateinit var binding: AddDialogBinding

    private val bookFragmentViewModel: BookFragmentViewModel by viewModels()

    private var isEdit: Boolean = false
    private var book: Book? = null

    private val SUPPORTED_IMAGE = mutableListOf(".jpeg", ".jpg", ".png")

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
            .setPositiveButton(R.string.ok, null) // Set initially as null
            .setNegativeButton(R.string.cancel) { dialog, id ->
                dialog.cancel()
            }

        val alertDialog = builder.create()

        var isImageValid = false

        alertDialog.setOnShowListener { dialog ->
            val positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)

            positiveButton.setOnClickListener {
                binding.apply {
                    val cover = edtCover.text.toString()
                    val judul = edtJudul.text.toString()
                    val namaPenulis = edtNamaPenulis.text.toString()
                    val tahun = if (edtTahun.text.toString().isNotEmpty()) edtTahun.text.toString().toInt() else 0
                    val kategori = edtKategori.text.toString()

                    if (cover.isEmpty()) edtCover.error = "Tidak boleh kosong"
                    if (judul.isEmpty()) edtJudul.error = "Tidak boleh kosong"
                    if (namaPenulis.isEmpty()) edtNamaPenulis.error = "Tidak boleh kosong"
                    if (tahun == 0) edtTahun.error = "Tidak boleh kosong"
                    if (kategori.isEmpty()) edtKategori.error = "Tidak boleh kosong"

                    if (cover.isNotEmpty()) {
                        for(i in 0 until SUPPORTED_IMAGE.size) {
                            isImageValid = cover.contains(SUPPORTED_IMAGE[i])
                            if (isImageValid) break
                        }
                        if (!isImageValid)  edtCover.error = "URL tidak valid"
                    }

                    if(tahun != 0) {
                        if (tahun < 1800 || tahun > 2023) edtTahun.error = "Tahun tidak valid"
                    }

                    val isValid = cover.isNotEmpty()
                            && judul.isNotEmpty()
                            && namaPenulis.isNotEmpty()
                            && tahun != 0 && (tahun in 1801..2023)
                            && kategori.isNotEmpty()
                            && isImageValid

                    if (isValid) {
                        val bookId = book?.id
                        val addedBook = Book(bookId, cover, judul, namaPenulis, tahun, kategori)

                        if (!isEdit) {
                            bookFragmentViewModel.addBook(addedBook)
                        } else {
                            bookFragmentViewModel.editBook(addedBook)
                        }

                        dialog.dismiss()
                    }
                }
            }
        }

        return alertDialog
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