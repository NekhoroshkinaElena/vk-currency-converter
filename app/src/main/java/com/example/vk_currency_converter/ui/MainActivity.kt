package com.example.vk_currency_converter.ui

import android.R
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.vk_currency_converter.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()

    private var inputValue: String = ""

    private var arrayAdapter: ArrayAdapter<String>? = null

    private var spinnerInput: Spinner? = null
    private var spinnerOutput: Spinner? = null

    private var inputCurrency: String? = null
    private var outputCurrency: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        arrayAdapter = ArrayAdapter(
            this,
            R.layout.simple_list_item_1
        )
        spinnerInput = binding.spCurrencyInput
        spinnerOutput = binding.spCurrencyOutput

        spinnerInput?.adapter = arrayAdapter
        spinnerOutput?.adapter = arrayAdapter

        initializeObserver()
        initializeListener(binding)
    }

    private fun initializeObserver() {
        viewModel.listCurrency.observe(this) { state ->
            Log.i("TAG2", "onCreate: ${state.javaClass}")
            when (state) {
                is ConverterState.Content -> {
                    showContent(state)
                }

                is ConverterState.Loading -> {

                }

                is ConverterState.ErrorServer -> {

                }

                is ConverterState.ErrorNoInternet -> {
                    showErrorNoInternet()
                }
            }
        }
    }

    private fun initializeListener(binding: ActivityMainBinding) {
        spinnerInput?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                inputCurrency = p0?.selectedItem.toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        spinnerOutput?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                outputCurrency = p0?.selectedItem.toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        binding.btConvert.setOnClickListener {
            if (inputValue.isNotEmpty()) {
                binding.tvCurrencyOutput.text =
                    viewModel.converter(inputCurrency, outputCurrency, inputValue.toDouble())
                        .toString()
            } else {
                Toast.makeText(
                    this, "Введите конвертируемое значение",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        binding.etCurrencyInput.doOnTextChanged { text, _, _, _ ->
            binding.btConvert.isEnabled = text.toString().isNotEmpty()
            inputValue = text.toString()
        }
    }

    private fun showContent(state: ConverterState.Content) {
        arrayAdapter?.clear()
        arrayAdapter?.addAll(state.currency)
        arrayAdapter?.notifyDataSetChanged()
        spinnerOutput?.adapter = arrayAdapter
    }

    private fun showErrorNoInternet() {
        Toast.makeText(
            this, "Проверьте подключение к интернету", Toast.LENGTH_SHORT
        ).show()
    }
}
