package com.example.trader_project.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trader_project.common.Resource
import com.example.trader_project.model.Currency
import com.example.trader_project.repository.AuthRepository
import com.example.trader_project.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private var _homeState = MutableLiveData<HomeState>()
    val homeState: LiveData<HomeState> get() = _homeState

    private var _dateState = MutableLiveData<DateState>()
    val dateState: LiveData<DateState> get() = _dateState

    fun getPortfolio() = viewModelScope.launch {

        val resultData = currencyRepository.getCurrency()

        _homeState.value = HomeState.Loading

        Log.e("HomeViewModel", "getCurrency result: $resultData") // Verinin geldiği veya gelmediği hakkında bilgi veren log

        _homeState.value = when (resultData) {
            is Resource.Success -> HomeState.SuccessState(resultData.data)
            is Resource.Fail -> HomeState.EmptyScreen(resultData.failMessage)
            is Resource.Error -> HomeState.ShowPopUp(resultData.errorMessage)
        }
    }

    fun getDate() = viewModelScope.launch {

        val resultDate = currencyRepository.getDate()

        _dateState.value = DateState.Loading

        Log.e("HomeViewModel", "getDate result: $resultDate") // Verinin geldiği veya gelmediği hakkında bilgi veren log

        _dateState.value = when (resultDate) {
            is Resource.Success -> DateState.SuccessState(resultDate.data)
            is Resource.Fail -> DateState.EmptyScreen(resultDate.failMessage)
            is Resource.Error -> DateState.ShowPopUp(resultDate.errorMessage)
        }
    }

    fun logOut() {
        authRepository.logOut()
        _homeState.value = HomeState.GoToLogOut
    }
}

sealed interface HomeState {
    object Loading : HomeState
    object GoToLogOut : HomeState
    data class SuccessState(val currencyList: List<Currency>) : HomeState
    data class EmptyScreen(val failMessage: String) : HomeState
    data class ShowPopUp(val errorMessage: String) : HomeState
}

sealed interface DateState {
    object Loading : DateState
    data class SuccessState(val date: String) : DateState
    data class EmptyScreen(val failMessage: String) : DateState
    data class ShowPopUp(val errorMessage: String) : DateState
}
