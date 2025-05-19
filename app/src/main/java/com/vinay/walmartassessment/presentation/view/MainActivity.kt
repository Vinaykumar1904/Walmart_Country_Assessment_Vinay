package com.vinay.walmartassessment.presentation.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.walmartassessment.databinding.ActivityMainBinding
import com.vinay.walmartassessment.data.mapper.CountryMapper
import com.vinay.walmartassessment.data.remote.ApiClient
import com.vinay.walmartassessment.data.remote.ApiService
import com.vinay.walmartassessment.data.repository.CountryRepository
import com.vinay.walmartassessment.domain.usecase.CountryUseCase
import com.vinay.walmartassessment.presentation.viewModel.CountryVMFactory
import com.vinay.walmartassessment.presentation.viewModel.CountryViewModel
import com.vinay.walmartassessment.state.ApiStates
import kotlinx.coroutines.launch

/*
* This Activity provides UI which displays Country Details To Users
* */
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var countryAdapter: CountryAdapter
    lateinit var countryViewModel: CountryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getViewModelInstance()
        setUpRecyclerView()
        displayCountries()
    }

    private fun getViewModelInstance() {
        val apiService = ApiClient.retrofit.create(ApiService::class.java)
        val iMapper = CountryMapper()
        val iCountryRepository = CountryRepository(
            iMapper = iMapper,
            apiService = apiService
        )
        val iCountryUseCase = CountryUseCase(
            iCountryRepository = iCountryRepository
        )
        val factory = CountryVMFactory(iCountryUseCase)
        countryViewModel = ViewModelProvider(this,factory).get(CountryViewModel::class.java)
    }

    private fun displayCountries() {
        if(!::countryViewModel.isInitialized){
            getViewModelInstance()
        }
        else{
            lifecycleScope.launch {
                countryViewModel.uiState.collect {
                    with(binding) {
                        when (it) {
                            is ApiStates.Failure -> {
                                pbLoading.visibility = View.GONE
                                tvError.visibility = View.VISIBLE
                                tvError.text = it.msg
                                Toast.makeText(this@MainActivity, it.msg, Toast.LENGTH_LONG).show()
                            }

                            is ApiStates.Loading -> {
                                pbLoading.visibility = View.VISIBLE
                                tvError.visibility = View.GONE
                            }

                            is ApiStates.Success -> {
                                pbLoading.visibility = View.GONE
                                tvError.visibility = View.GONE
                                countryAdapter.updateCountries(it.data)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        countryAdapter = CountryAdapter()
        binding.rvCountry.adapter = countryAdapter
    }
}