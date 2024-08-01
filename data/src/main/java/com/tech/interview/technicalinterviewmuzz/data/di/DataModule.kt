package com.tech.interview.technicalinterviewmuzz.data.di

import com.tech.interview.technicalinterviewmuzz.data.ChatRepo
import com.tech.interview.technicalinterviewmuzz.data.ChatRepository
import com.tech.interview.technicalinterviewmuzz.data.UserRepo
import com.tech.interview.technicalinterviewmuzz.data.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindChatRepository(chatRepository: ChatRepository): ChatRepo

    @Binds
    abstract fun bindUserRepository(userRepository: UserRepository): UserRepo
}