package com.engineering.baseproj.base_classes

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

abstract class BaseViewModel : ViewModel() {
    protected val coroutineScope = CoroutineScope(
        Job() + Dispatchers.IO
    )

    override fun onCleared() {
        coroutineScope.cancel()
    }
}