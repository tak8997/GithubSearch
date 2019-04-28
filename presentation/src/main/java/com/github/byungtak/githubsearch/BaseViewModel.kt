package com.github.byungtak.githubsearch

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable


internal abstract class BaseViewModel: ViewModel() {

    protected val disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}
