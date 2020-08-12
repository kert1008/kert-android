package com.pegasus.retrofittest

import dagger.Component

@Component(modules = [ApplicationModule::class])
interface MainActivityComponent {
    fun inject(mainActivity: MainActivity)
}