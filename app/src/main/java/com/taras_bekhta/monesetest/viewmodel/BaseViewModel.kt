package com.taras_bekhta.monesetest.viewmodel

import android.arch.lifecycle.ViewModel
import com.taras_bekhta.monesetest.injection.NetworkModule
import com.taras_bekhta.monesetest.injection.component.DaggerViewModelInjector
import com.taras_bekhta.monesetest.injection.component.ViewModelInjector

abstract class BaseViewModel: ViewModel() {

    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    private fun inject() {
        when(this) {
            is LaunchesViewModel -> injector.injectLaunchesVM(this)
            is RocketDetailsViewModel -> injector.injectRocketVM(this)
        }
    }
}