package com.tech.interview.technicalinterviewmuzz.network.di

import com.tech.interview.technicalinterviewmuzz.network.service.ChatService
import com.tech.interview.technicalinterviewmuzz.network.service.ChatServiceApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface ServiceModule {

    @Binds
    fun binds(service: ChatService): ChatServiceApi
}