package com.example.atividade01dm.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.atividade01dm.api.ApiState
import com.example.atividade01dm.api.request.UsuarioEditaRequestBody
import com.example.atividade01dm.api.response.UploadFotoPerfilResponseBody
import com.example.atividade01dm.api.response.UsuarioEditaResponseBody
import com.example.atividade01dm.api.response.UsuarioResponseBody
import com.example.atividade01dm.api.response.UsuariosResponseBody
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class UsuarioViewModel(
    private val application: Application
) : MainViewModel(application) {

    private val _usuariosResponseBody = mutableStateOf<ApiState<UsuariosResponseBody>>(ApiState.Created())
    val usuariosResponseBody: State<ApiState<UsuariosResponseBody>> = _usuariosResponseBody

    private val _usuarioResponseBody = mutableStateOf<ApiState<UsuarioResponseBody>>(ApiState.Created())
    val usuarioResponseBody: State<ApiState<UsuarioResponseBody>> = _usuarioResponseBody

    private val _usuarioEditaResponseBody = mutableStateOf<ApiState<UsuarioEditaResponseBody>>(ApiState.Created())
    val usuariosEditaResponseBody: State<ApiState<UsuarioEditaResponseBody>> = _usuarioEditaResponseBody

    private val _uploadFotoPerfilResponseBody = mutableStateOf<ApiState<UploadFotoPerfilResponseBody>>(ApiState.Created())
    val uploadFotoPerfilResponseBody: State<ApiState<UploadFotoPerfilResponseBody>> = _uploadFotoPerfilResponseBody

    fun getUsuarios() {
        viewModelScope.launch {
            _usuariosResponseBody.value = ApiState.Loading()
            _usuariosResponseBody.value = apiRepository.getUsuarios()
        }
    }

    fun getUsuarioById(id: String) {
        viewModelScope.launch {
            _usuarioResponseBody.value = ApiState.Loading()
            _usuarioResponseBody.value = apiRepository.getUsuario(id)
        }
    }

    fun usuarioEdita(id: String, usuarioEditaRequestBody: UsuarioEditaRequestBody) {
        viewModelScope.launch {
            _usuarioEditaResponseBody.value = ApiState.Loading()
            _usuarioEditaResponseBody.value = apiRepository.usuarioEdita(id, usuarioEditaRequestBody)
        }
    }

    fun uploadFotoPerfil(imageUri: Uri) {
        viewModelScope.launch {
            val file = File.createTempFile(
                "tmp_" + System.currentTimeMillis().toString(),
                ".jpg",
                application.applicationContext.cacheDir
            )

            val inputStream = application.applicationContext.contentResolver.openInputStream(imageUri)
            var bitmap = BitmapFactory.decodeStream(inputStream);
            bitmap = resizePhoto(bitmap)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, file.outputStream())

            val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val image = MultipartBody.Part.createFormData(name = "image", file.name, requestBody)

            _uploadFotoPerfilResponseBody.value = ApiState.Loading()
            _uploadFotoPerfilResponseBody.value = apiRepository.uploadFotoPerfil(image)
        }
    }

    private fun resizePhoto(bitmap: Bitmap): Bitmap {
        val w = bitmap.width
        val h = bitmap.height
        val aspRat = w.toDouble() / h.toDouble()
        val dstW = 400
        val dstH = dstW * aspRat
        val result =  Bitmap.createScaledBitmap(bitmap, dstW, dstH.toInt(), false)
        return result
    }

    fun clearApiState() {
        _usuarioResponseBody.value = ApiState.Created()
        _usuarioEditaResponseBody.value = ApiState.Created()
        _uploadFotoPerfilResponseBody.value = ApiState.Created()
    }
}