package com.salugan.userlist

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.salugan.userlist.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_USER, User::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_USER)
        }

        if (user != null) {
            binding.apply {
                profilePic.setImageResource(user.photo)
                userInfo.tvName.text = user.nama
                userInfo.tvEmail.text = user.email
                userInfo.tvJurusan.text = user.jurusan
                userInfo.tvSemester.text = user.semester.toString()
            }
        }
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}