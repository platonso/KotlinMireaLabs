package com.platon.kotlinmirealabs.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ThirdViewModel: ViewModel() {
    private val _notificationsEnabled = MutableLiveData<Boolean>()
    val notificationsEnabled: LiveData<Boolean> get() = _notificationsEnabled

    private val _darkThemeEnabled = MutableLiveData<Boolean>()
    val darkThemeEnabled: LiveData<Boolean> get() = _darkThemeEnabled

    private val _batterySaverEnabled = MutableLiveData<Boolean>()
    val batterySaverEnabled: LiveData<Boolean> get() = _batterySaverEnabled

    private val _autoUpdateEnabled = MutableLiveData<Boolean>()
    val autoUpdateEnabled: LiveData<Boolean> get() = _autoUpdateEnabled

    private val _backupEnabled = MutableLiveData<Boolean>()
    val backupEnabled: LiveData<Boolean> get() = _backupEnabled

    private val _vibrationEnabled = MutableLiveData<Boolean>()
    val vibrationEnabled: LiveData<Boolean> get() = _vibrationEnabled

    private val _analyticsEnabled = MutableLiveData<Boolean>()
    val analyticsEnabled: LiveData<Boolean> get() = _analyticsEnabled

    fun setNotificationEnabled(enabled: Boolean) {
        _notificationsEnabled.value = enabled
    }

    fun setDarkThemeEnabled(enabled: Boolean) {
        _darkThemeEnabled.value = enabled
    }

    fun setBatterySaverEnabled(enabled: Boolean) {
        _batterySaverEnabled.value = enabled
    }

    fun setAutoUpdateEnabled(enabled: Boolean) {
        _autoUpdateEnabled.value = enabled
    }

    fun setBackupEnabled(enabled: Boolean) {
        _backupEnabled.value = enabled
    }

    fun setVibrationEnabled(enabled: Boolean) {
        _vibrationEnabled.value = enabled
    }

    fun setAnalyticsEnabled(enabled: Boolean) {
        _analyticsEnabled.value = enabled
    }
}