package com.vinay.walmartassessment.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vinay.walmartassessment.domain.data.Country
import com.vinay.walmartassessment.domain.usecase.ICountryUseCase
import com.vinay.walmartassessment.state.ApiStates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CountryViewModel(val iCountryUseCase: ICountryUseCase): ViewModel() {
    val _uiState = MutableStateFlow<ApiStates<List<Country>>>(ApiStates.Loading)
    val uiState : StateFlow<ApiStates<List<Country>>> = _uiState
    init {
        getCountries()
    }
    /*
    * Fetching the States to display into UI
    * */
    fun getCountries(){
        viewModelScope.launch(Dispatchers.IO) {
            iCountryUseCase.invoke()
                .catch {e->
                    val errorMsg = e.message
                    errorMsg?.let {
                        _uiState.value = ApiStates.Failure(it)
                    }
                }
                .collect{
                    _uiState.value = it
                }
        }
    }
}

class CountryVMFactory(val iCountryUseCase: ICountryUseCase): ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CountryViewModel(iCountryUseCase) as T
    }
}