package com.platon.kotlinmirealabs.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SecondViewModel: ViewModel() {

    private val _cardHolderName = MutableLiveData<String>()
    val cardHolderName: MutableLiveData<String> = _cardHolderName

    private val _cardNumber = MutableLiveData<String>()
    val cardNumber: MutableLiveData<String> = _cardNumber

    private val _expDate = MutableLiveData<String>()
    val expDate: MutableLiveData<String> = _expDate

    private val _cvv = MutableLiveData<String>()
    val cvv: MutableLiveData<String> = _cvv



    fun setCardHolderName(newCardHolderName: String) {
        _cardHolderName.value = newCardHolderName
    }

    fun setCardNumber(newCardNumber: String) {
        _cardNumber.value = newCardNumber
    }

    fun setExpDate(newExpDate: String) {
        _expDate.value = newExpDate
    }

    fun setCvv(newCvv: String) {
        _cvv.value = newCvv
    }

}