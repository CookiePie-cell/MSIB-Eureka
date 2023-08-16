package com.salugan.loginapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.Toast
import com.salugan.loginapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var emailValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().isNotEmpty()) {
                    if (!Patterns.EMAIL_ADDRESS.matcher(p0.toString()).matches()) {
                        emailValid = false
                        binding.edtEmail.error = "Masukkan email yang benar"
                    } else {
                        emailValid = true
                        binding.edtNama.error = null
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        setupAction()
    }

    private fun setupAction() {
        binding.apply {
            btnLogin.setOnClickListener {
                val nama = edtNama.text.toString()
                val email = edtEmail.text.toString()
                val jurusan = edtJurusan.text.toString()
                val semester = edtSemester.text.toString()

                if (nama.isEmpty()) edtNama.error = "Nama tidak boleh kosong"
                if (email.isEmpty()) edtEmail.error = "Email tidak boleh kosong"
                if (jurusan.isEmpty()) edtJurusan.error = "Jurusan tidak boleh kosong"
                if (semester.isEmpty()) edtSemester.error = "Semester tidak boleh kosong"
                else {
                    val user = User(nama, email, jurusan, semester.toInt())
                    val intent = Intent(this@MainActivity, HomeActivity::class.java)
                    intent.putExtra(HomeActivity.EXTRA_USER, user)
                    this@MainActivity.startActivity(intent)
                }
            }
        }
    }
}