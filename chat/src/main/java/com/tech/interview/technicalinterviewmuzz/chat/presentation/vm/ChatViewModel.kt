package com.tech.interview.technicalinterviewmuzz.chat.presentation.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech.interview.technicalinterviewmuzz.chat.R
import com.tech.interview.technicalinterviewmuzz.core.Result
import com.tech.interview.technicalinterviewmuzz.data.ChatRepo
import com.tech.interview.technicalinterviewmuzz.data.UserRepo
import com.tech.interview.technicalinterviewmuzz.model.ChatMessageUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import java.time.Duration
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: ChatRepo
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow<String?>(null)
    private val _filteredMessages = repository.getMessagesStream()
        .map {
            handleSection(it)
        }
        .map {
            Result.Success(it)
        }
        .catch<Result<List<ChatMessageUi>>> {
            emit(Result.Error(R.string.error_loading_messages))
        }


    val uiState: StateFlow<ChatMessagesUiState> =
        combine(_isLoading, _errorMessage, _filteredMessages) { isLoading, errorMessage, messagesResult ->
            when (messagesResult) {
                is Result.Success -> ChatMessagesUiState(
                    messages = messagesResult.data,
                    isLoading = isLoading,
                    errorMessage = errorMessage
                )

                is Result.Error -> ChatMessagesUiState(
                    messages = emptyList(),
                    isLoading = false,
                    errorMessage = errorMessage
                )

                Result.Loading -> ChatMessagesUiState(
                    messages = emptyList(),
                    isLoading = true,
                    errorMessage = null
                )
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ChatMessagesUiState(isLoading = true)
        )

    init {
        repository.establishSocketConnection()
    }

    override fun onCleared() {
        super.onCleared()
        repository.closeSocketConnection()
    }


    fun handleSection(
        messages: List<ChatMessageUi>
    ): List<ChatMessageUi> {
        if (messages.isEmpty()) return messages
        val updatedMessages = mutableListOf<ChatMessageUi>()
        var previousTimeStamp = Instant.DISTANT_FUTURE
        messages.forEachIndexed { i, m ->
            if (i == 0) {
                updatedMessages.add(m.copy(itemSection = formatInstant(m.timeStamp)))
            } else {
                val shouldAddSection = isGreaterThanOneHour(previousTimeStamp, m.timeStamp)
                if (shouldAddSection) {
                    updatedMessages.add(m.copy(itemSection = formatInstant(m.timeStamp)))
                } else {
                    updatedMessages.add(m)
                }
                previousTimeStamp = m.timeStamp
            }
        }
        return updatedMessages
    }

    fun refreshMessages() {
        _isLoading.value = true
        viewModelScope.launch {
            repository.syncMessages().fold(
                onSuccess = {
                    _isLoading.value = false
                },
                onFailure = {
                    _errorMessage.value = it.message
                    _isLoading.value = false
                }
            )
        }
    }


    fun onSendMessage(message: String) {
        viewModelScope.launch {
            repository.sendMessage(message)
        }
    }


    // TODO - move to utils
    fun formatInstant(instant: Instant): String {
        val formatter = DateTimeFormatter.ofPattern("EEEE HH:mm")
            .withZone(ZoneId.systemDefault())
        return formatter.format(instant.toJavaInstant())
    }

    // TODO - move to utils
    fun isGreaterThanOneHour(start: Instant, end: Instant): Boolean {
        val duration = Duration.between(start.toJavaInstant(), end.toJavaInstant())
        return duration.seconds > 3600
    }

}


data class ChatMessagesUiState(
    val messages: List<ChatMessageUi> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userInput: String = "",
)

