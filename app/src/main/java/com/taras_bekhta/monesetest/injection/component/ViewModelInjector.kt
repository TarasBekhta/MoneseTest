package com.taras_bekhta.monesetest.injection.component

import com.taras_bekhta.monesetest.injection.NetworkModule
import com.taras_bekhta.monesetest.viewmodel.LaunchesViewModel
import com.taras_bekhta.monesetest.viewmodel.RocketDetailsViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {

    fun injectLaunchesVM(launchesViewModel: LaunchesViewModel)

    fun injectRocketVM(rocketViewModel: RocketDetailsViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}