package com.tech.interview.technicalinterviewmuzz.chat.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tech.interview.technicalinterviewmuzz.chat.R
import com.tech.interview.technicalinterviewmuzz.core.theme.SenderColor

@Composable
fun TopRow(
    onReturn: () -> Unit,
) {
    Row(
        modifier = Modifier
            .height(56.dp)
            .imePadding()
            .shadow(1.dp)
            .background(color = Color(0xFFFEFEFE)),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        IconButton(
            onClick = {
                onReturn()
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_return),
                contentDescription = null,
                tint = SenderColor,
            )
        }

        Box(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .fillMaxHeight()
                .weight(1F),
            contentAlignment = Alignment.Center,
            content = {
                Text(
                    text = "Sarah",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF333333),
                )
            }
        )

        Icon(
            modifier = Modifier.padding(end = 12.dp),
            painter = painterResource(id = R.drawable.ic_menu),
            contentDescription = null,
            tint = Color(0xFFC5CBD2),
        )

    }

}

@Composable
@Preview
fun TopRowPreview() {
    TopRow(
        onReturn = {}
    )
}