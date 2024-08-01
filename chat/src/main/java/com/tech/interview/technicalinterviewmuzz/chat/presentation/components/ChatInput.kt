package com.tech.interview.technicalinterviewmuzz.chat.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tech.interview.technicalinterviewmuzz.core.theme.SenderColor

@Composable
fun ChatInput(
    userInput: String,
    onValueChange: (String) -> Unit,
    onSendMessage: () -> Unit
) {

    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        val scrollState = rememberScrollState(0)
        OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState),
            value = userInput,
            onValueChange = { typedValue ->
                onValueChange(typedValue)
            },
            maxLines = 4,
            singleLine = false,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Send,
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Sentences,
            ),
            keyboardActions = KeyboardActions(
                onSend = {
                    onSendMessage()
                }
            ),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedBorderColor = if (userInput.isNotEmpty()) SenderColor else Color.DarkGray,
                unfocusedBorderColor = Color.DarkGray,
            ),
            shape = RoundedCornerShape(24.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))
        SendButton(
            enabled = userInput.isNotEmpty(),
            onSendMessage = onSendMessage
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ChatInputPreview() {
    ChatInput(
        userInput = "",
        onValueChange = {},
        onSendMessage = {}
    )
}