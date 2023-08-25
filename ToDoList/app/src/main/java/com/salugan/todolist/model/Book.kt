package com.salugan.todolist.model

data class Book(
    var id: String? = "",
    var cover: String? = "",
    var judul: String? = "",
    var namaPenulis: String? = "",
    var tahunTerbit: Int? = 0,
    var kategori: String? = ""
)
