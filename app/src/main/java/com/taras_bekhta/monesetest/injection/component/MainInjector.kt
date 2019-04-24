package com.taras_bekhta.monesetest.injection.component

import com.taras_bekhta.monesetest.injection.MainModule
import com.taras_bekhta.monesetest.view.MainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(MainModule::class)])
interface MainInjector {

    fun inject(mainFragment: MainFragment)

    @Component.Builder
    interface Builder {
        fun build(): MainInjector

        fun mainModule(mainModule: MainModule): Builder
    }
}