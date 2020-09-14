package com.cabify.cabifystore.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel(
    private val mainScheduler: Scheduler
) : ViewModel() {

    val disposables = CompositeDisposable()


    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}