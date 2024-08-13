package com.example.atividade01dm.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.atividade01dm.R
import com.example.atividade01dm.api.RetrofitClient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FotoPerfil(
    foto: String?,
    tamanho: Dp
) {
    Box(
        modifier = Modifier
            .size(tamanho)
            .clip(CircleShape)
            .border(
                1.dp,
                Color.LightGray,
                CircleShape
            )
    ) {
        if (foto.isNullOrEmpty()) {
            Image(
                painter = painterResource(id = R.drawable.ic_person),
                contentDescription = null,
                modifier = Modifier
                    .size(tamanho)
                    .clip(CircleShape)
            )
        } else {
            val path = RetrofitClient.BASE_URL + "/imagens/" + foto
            AsyncImage(
                model = path,
                error = painterResource(id = R.drawable.ic_person),
                fallback = painterResource(id = R.drawable.ic_person),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(tamanho)
                    .clip(CircleShape)
            )
        }
    }
}