package com.pegasus.retrofittest

import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {
    @Provides
    fun provideUserRepository(): UserRepository {
        return UserRepository()
    }
}