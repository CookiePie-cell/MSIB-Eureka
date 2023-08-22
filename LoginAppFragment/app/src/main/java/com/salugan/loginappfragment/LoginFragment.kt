package com.salugan.loginappfragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import com.salugan.loginappfragment.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private var emailValid = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

                    val mHomeFragment = HomeFragment()
                    val bundle = Bundle()
                    bundle.putParcelable(HomeFragment.EXTRA_USER, user)

                    mHomeFragment.arguments = bundle

                    parentFragmentManager.commit {
                        addToBackStack(null)
                        replace(R.id.fragment_container, mHomeFragment, HomeFragment::class.java.simpleName)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}