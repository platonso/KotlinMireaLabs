package com.platon.kotlinmirealabs.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FirstViewModel: ViewModel() {

    private val _name = MutableLiveData<String>()
    val name: MutableLiveData<String> = _name

    private val _surname = MutableLiveData<String>()
    val surname: MutableLiveData<String> = _surname

    fun setName(newName: String) {
        _name.value = newName
    }

    fun setSurname(newSurname: String) {
        _surname.value = newSurname
    }

}