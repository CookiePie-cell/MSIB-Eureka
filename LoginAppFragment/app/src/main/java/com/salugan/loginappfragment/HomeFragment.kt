package com.salugan.loginappfragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.salugan.loginappfragment.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            user = if (Build.VERSION.SDK_INT >= 33) {
                arguments?.getParcelable(EXTRA_USER, User::class.java)
            } else {
                @Suppress("DEPRECATION")
                arguments?.getParcelable(EXTRA_USER)
            }
        }

        binding.apply {
            txtNama.text = user?.nama
            txtEmail.text = user?.email
            txtJurusan.text = user?.jurusan
            txtSemester.text = resources.getString(R.string.semester, user?.semester.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        const val EXTRA_USER = "extra_user"
    }
}