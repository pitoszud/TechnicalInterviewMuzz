package com.tech.interview.technicalinterviewmuzz.chat.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tech.interview.technicalinterviewmuzz.core.theme.GreyDarkColor

@Composable
fun TimeSection(timeText: String) {
    val parts = timeText.split(" ")
    val day = parts[0]
    val time = parts[1]

    val annotatedString = buildAnnotatedString {
        append(day)
        addStyle(style = SpanStyle(
            fontWeight = FontWeight.Bold),
            start = 0,
            end = day.length
        )
        append(" $time")
    }

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            color = GreyDarkColor,
            text = annotatedString,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun TimeSectionPreview() {
    TimeSection("Thursday 11:59")
}