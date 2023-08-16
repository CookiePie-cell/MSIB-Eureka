package com.salugan.loginapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.salugan.loginapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = if(Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_USER, User::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_USER)
        }

        if (user != null) {
            binding.apply {
                txtNama.text = user.nama
                txtEmail.text = user.email
                txtJurusan.text = user.jurusan
                txtSemester.text = resources.getString(R.string.semester, user.semester.toString())
            }
        }
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}