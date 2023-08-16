package com.salugan.userlist

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val photo: Int,
    val nama: String,
    val email: String,
    val jurusan: String,
    val semester: Int
) : Parcelable

val users = listOf(
    User(R.drawable.chamber, "Chamber", "chamber@gmail.com", "Sastra perancis", 7),
    User(R.drawable.neon, "Neon", "Neon@gmail.com", "Sastra Inggris", 3),
    User(R.drawable.phoenix, "phoenix", "phoenix@gmail.com", "Ekonomi", 7),
    User(R.drawable.sage, "sage", "sage@gmail.com", "Manajemen", 7),
    User(R.drawable.sova, "sova", "sova@gmail.com", "Elektro", 5)
)
