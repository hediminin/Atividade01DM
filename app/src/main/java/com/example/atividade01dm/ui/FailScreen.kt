package com.example.atividade01dm.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.atividade01dm.ui.theme.Atividade01DMTheme

@Composable
fun FailScreen(
    message: String
) {
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                message,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FailScreenPreview() {
    Atividade01DMTheme {
        FailScreen("Mensagem de falha.")
    }
}