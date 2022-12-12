package com.example.telegram.presentation.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.telegram.domain.models.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {
    protected val context
        get() = getApplication<Application>()
}