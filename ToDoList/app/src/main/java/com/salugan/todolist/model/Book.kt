package com.salugan.todolist.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    var id: String? = "",
    var cover: String? = "",
    var judul: String? = "",
    var namaPenulis: String? = "",
    var tahunTerbit: Int? = 0,
    var kategori: String? = ""
) : Parcelable
