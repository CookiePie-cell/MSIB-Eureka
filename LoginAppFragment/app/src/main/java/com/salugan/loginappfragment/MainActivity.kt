package com.salugan.loginappfragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.salugan.loginappfragment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mLoginFragment = LoginFragment()
        val fragment = supportFragmentManager.findFragmentByTag(LoginFragment::class.java.simpleName)

        if (fragment !is LoginFragment) {
            supportFragmentManager.commit {
                replace(R.id.fragment_container, mLoginFragment, LoginFragment::class.java.simpleName)
            }
        }
    }
}