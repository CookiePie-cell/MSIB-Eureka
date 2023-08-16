package com.salugan.loginapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class User(
    val nama: String,
    val email: String,
    val jurusan: String,
    val semester: Int
    ) : Parcelable
