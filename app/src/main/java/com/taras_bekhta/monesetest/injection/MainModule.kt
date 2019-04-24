package com.taras_bekhta.monesetest.injection

import android.content.Context
import com.taras_bekhta.monesetest.view.adapter.LaunchesAdapter
import dagger.Module
import dagger.Provides

@Module
class MainModule(val context: Context) {

    @Provides
    internal fun provideAdapter(): LaunchesAdapter {
        return LaunchesAdapter(context)
    }
}