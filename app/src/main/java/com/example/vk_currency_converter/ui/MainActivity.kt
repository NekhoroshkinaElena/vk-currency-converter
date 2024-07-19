package com.example.vk_currency_converter.ui

import android.R
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.example.vk_currency_converter.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()

    private var arrayAdapter: ArrayAdapter<String>? = null

    private var inputValue: String = ""

    private var spinnerInput: Spinner? = null
    private var spinnerOutput: Spinner? = null

    private var inputCurrency: String? = null
    private var outputCurrency: String? = null

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
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
        initializeListener()
    }

    private fun initializeObserver() {
        viewModel.listCurrency.observe(this) { state ->
            when (state) {
                is ConverterState.Content -> {
                    showContent(state)
                }

                is ConverterState.Loading -> {
                    showLoading()
                }

                is ConverterState.ErrorServer -> {
                    showErrorServer()
                }

                is ConverterState.ErrorNoInternet -> {
                    showErrorNoInternet()
                }
            }
        }

        viewModel.convertedValue.observe(this) {
            binding.tvCurrencyOutput.text = it
        }
    }

    private fun initializeListener() {
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
            viewModel.converter(inputCurrency, outputCurrency, inputValue.toBigDecimal())
        }

        binding.etCurrencyInput.doOnTextChanged { text, _, _, _ ->
            binding.btConvert.isEnabled = text.toString().isNotEmpty()
            inputValue = text.toString()
        }

        binding.btUpdate.setOnClickListener {
            viewModel.getExchangeRates()
        }
    }

    private fun showContent(state: ConverterState.Content) {
        binding.pbProgressBar.isVisible = false
        binding.converterGroup.isVisible = true
        binding.btUpdate.isVisible = false
        binding.tvErrorMessage.isVisible = false
        arrayAdapter?.clear()
        arrayAdapter?.addAll(state.currency)
        arrayAdapter?.notifyDataSetChanged()
    }

    private fun showErrorNoInternet() {
        binding.pbProgressBar.isVisible = false
        binding.converterGroup.isVisible = false
        binding.btUpdate.isVisible = true
        binding.tvErrorMessage.isVisible = true
    }

    private fun showErrorServer() {
        binding.pbProgressBar.isVisible = false
        binding.converterGroup.isVisible = false
        binding.btUpdate.isVisible = true
        binding.tvErrorMessage.isVisible = true
        binding.tvErrorMessage.text =
            resources.getString(com.example.vk_currency_converter.R.string.server_error)
    }

    private fun showLoading() {
        binding.pbProgressBar.isVisible = true
        binding.converterGroup.isVisible = false
        binding.btUpdate.isVisible = false
        binding.tvErrorMessage.isVisible = false
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
