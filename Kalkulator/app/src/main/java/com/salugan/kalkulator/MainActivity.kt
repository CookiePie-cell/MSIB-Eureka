package com.salugan.kalkulator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.salugan.kalkulator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var currentInput = "0"
    private var prevOperator = ""
    private var result = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            tvResult.text = currentInput
            btn1.setOnClickListener { clear() }
            btn2.setOnClickListener { backspace() }
            btn3.setOnClickListener {
                addOperator(btn3.text.toString())
                calculateResult()
            }

            btn4.setOnClickListener { addOperator(btn4.text.toString()) }
            btn8.setOnClickListener { addOperator(btn8.text.toString()) }
            btn12.setOnClickListener { addOperator(btn12.text.toString()) }

            btn16.setOnClickListener { addOperator(btn16.text.toString()) }
            btn18.setOnClickListener { addDecimalPoint() }
            btn19.setOnClickListener { calculateResult() }

            // 1,2,3
            btn13.setOnClickListener { addNumber(btn13.text.toString()) }
            btn14.setOnClickListener { addNumber(btn14.text.toString()) }
            btn15.setOnClickListener { addNumber(btn15.text.toString()) }

            // 4,5,6
            btn9.setOnClickListener { addNumber(btn9.text.toString()) }
            btn10.setOnClickListener { addNumber(btn10.text.toString()) }
            btn11.setOnClickListener { addNumber(btn11.text.toString()) }

            // 7,8,9
            btn5.setOnClickListener { addNumber(btn5.text.toString()) }
            btn6.setOnClickListener { addNumber(btn6.text.toString()) }
            btn7.setOnClickListener { addNumber(btn7.text.toString()) }
            btn17.setOnClickListener { addNumber(btn17.text.toString()) }
        }

    }

    private fun clear() {
        currentInput = "0"
        prevOperator = ""
        binding.tvResult.text = "0"
        binding.tvOperator.text = ""
    }

    private fun addNumber(s: String) {
        if (currentInput == "0") {
            currentInput = s
        } else {
            currentInput += s
        }
        binding.tvResult.text = currentInput
    }

    private fun backspace() {
        if (currentInput.length - 1 < 1) currentInput = "0"
        else if (currentInput.isNotEmpty() && currentInput != "0") {
            currentInput = currentInput.dropLast(1)
        }
        binding.tvResult.text = currentInput
    }

    private fun addDecimalPoint() {
        if (currentInput.isNotEmpty() && !currentInput.contains(".")) {
            currentInput += "."
            binding.tvResult.text = currentInput
        }
    }

    @SuppressLint("SetTextI18n")
    private fun addOperator(operator: String) {
        if (currentInput.isNotEmpty()) {
            val currentValue = currentInput.toDouble()

            if (prevOperator == "/" && currentValue == 0.0) {
                binding.tvResult.text = "Zero division"
                return
            }

            when (prevOperator) {
                "+" -> result += currentValue
                "-" -> result -= currentValue
                "x" -> result *= currentValue
                "/" -> result /= currentValue
                else -> result = currentValue
            }

            currentInput = if (operator == "%") result.toString() else ""
            prevOperator = operator
            binding.tvResult.text = ""
            binding.tvOperator.text = resources.getString(R.string.operation, result.toString(), operator)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun calculateResult() {
        if (currentInput.isNotEmpty() && prevOperator.isNotEmpty()) {
            val currentValue = currentInput.toDouble()

            if (prevOperator == "/" && currentValue == 0.0) {
                binding.tvResult.text = "Zero division"
                return
            }

            when (prevOperator) {
                "+" -> result += currentValue
                "-" -> result -= currentValue
                "x" -> result *= currentValue
                "/" -> result /= currentValue
                "%" -> result /= 100
            }
            currentInput = result.toString()
            binding.tvResult.text = result.toString()
            prevOperator = ""
            binding.tvOperator.text = binding.tvOperator.text.dropLast(1)
        }
    }

}