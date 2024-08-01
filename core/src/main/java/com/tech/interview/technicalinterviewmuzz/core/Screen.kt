package com.tech.interview.technicalinterviewmuzz.core

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable data object Chat : Screen()
}