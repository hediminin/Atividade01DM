package com.example.atividade01dm.ui

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.atividade01dm.R
import com.example.atividade01dm.api.ApiState
import com.example.atividade01dm.api.request.UsuarioEditaRequestBody
import com.example.atividade01dm.viewmodel.UsuarioViewModel
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsuarioEditaScreen(
    navController: NavController,
    id: String
) {
    val usuarioViewModel = viewModel<UsuarioViewModel>()

    val usuarioState by usuarioViewModel.usuarioResponseBody
    val usuarioEditaState by usuarioViewModel.usuariosEditaResponseBody
    val usuarioUploadFotoState by usuarioViewModel.uploadFotoPerfilResponseBody

    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var fotoAtual by remember { mutableStateOf("") }

    var alertDialogVisible by remember { mutableStateOf(false) }
    var alertMessage by remember { mutableStateOf("") }

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    var imageUri = remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val authority = stringResource(R.string.fileprovider)

    Scaffold(
        topBar = {
            AppTopAppBar(
                navController,
                "Editar usuário"
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            /*
            Formulário edição.
             */
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.padding(16.dp)
                ) {
                    FotoPerfil(
                        foto = fotoAtual,
                        150.dp
                    )

                    Button(
                        onClick = {
                            showBottomSheet = true
                        },
                        shape = CircleShape,
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .size(50.dp)
                            .align(Alignment.BottomEnd)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_edit),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }

                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text("Nome") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = nome.isEmpty()
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("E-mail") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = email.isEmpty()
                )

                Button(
                    onClick = {
                        val usuarioEditaRequestBody = UsuarioEditaRequestBody()
                        usuarioEditaRequestBody.nome = nome
                        usuarioEditaRequestBody.email = email

                        usuarioViewModel.usuarioEdita(
                            id,
                            usuarioEditaRequestBody
                        )
                    },
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        "Salvar",
                        modifier = Modifier
                            .padding(10.dp)
                    )
                }
            }
            when (usuarioEditaState) {
                is ApiState.Created -> {}
                is ApiState.Loading -> {}
                is ApiState.Success -> {
                    usuarioEditaState.data?.let { responseData ->
                        //navController.popBackStack()
                        //Toast.makeText(LocalContext.current, "Usuário salvo", Toast.LENGTH_SHORT).show()
                        scope.launch {
                            snackbarHostState
                                .showSnackbar(
                                    message = "Usuário salvo",
                                    duration = SnackbarDuration.Short
                                )
                        }
                    }
                }
                is ApiState.Error -> {
                    usuarioEditaState.message?.let {
                        alertMessage = it
                        alertDialogVisible = true
                    }
                }
            }

            if (alertDialogVisible) {
                AlertDialog(
                    text = {
                        Text(alertMessage)
                    },
                    onDismissRequest = {
                        usuarioViewModel.clearApiState()
                        alertDialogVisible = false
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                usuarioViewModel.clearApiState()
                                alertDialogVisible = false
                            }
                        ) {
                            Text("Ok")
                        }
                    }
                )
            }

            /*
            Carrega dados dos usuário e preenche formulário.
             */
            when (usuarioState) {
                is ApiState.Created -> {}
                is ApiState.Loading -> {}
                is ApiState.Success -> {
                    usuarioState.data?.let { responseData ->
                        nome = responseData.nome
                        email = responseData.email
                        responseData.foto?.let { foto ->
                            fotoAtual = foto
                        }
                        usuarioViewModel.clearApiState()
                    }
                }
                is ApiState.Error -> {
                    usuarioState.message?.let { message ->
                        FailScreen(message)
                    }
                }
            }

            /*
            Upload foto perfil
             */
            when (usuarioUploadFotoState) {
                is ApiState.Created -> {}
                is ApiState.Loading -> {}
                is ApiState.Success -> {
                    usuarioUploadFotoState.data?.let { responseData ->
                        val dadosUpload = responseData.dados
                        fotoAtual = dadosUpload.id_imagem

                        scope.launch {
                            snackbarHostState
                                .showSnackbar(
                                    message = "Foto atualizada",
                                    duration = SnackbarDuration.Short
                                )
                        }
                    }
                }
                is ApiState.Error -> {
                    usuarioUploadFotoState.message?.let {
                        alertMessage = it
                        alertDialogVisible = true
                    }
                }
            }
        }
    }

    fun hideSheet() {
        scope.launch { sheetState.hide() }.invokeOnCompletion {
            if (!sheetState.isVisible) {
                showBottomSheet = false
            }
        }
    }

    /*
    Selecionar ou fotografar
     */
    fun createTempImageUri(): Uri? {
        val directory = context.cacheDir
        directory?.let {
            it.mkdirs()

            val file = File.createTempFile(
                "image_" + System.currentTimeMillis().toString(),
                ".jpg",
                it
            )

            return FileProvider.getUriForFile(
                context,
                authority,
                file
            )
        }
        return null
    }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { selectedImageUri ->
            selectedImageUri?.let {
                imageUri.value = it
                usuarioViewModel.uploadFotoPerfil(id, it)
                hideSheet()
            }
        }
    )

    val takePhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { isSaved ->
            if (isSaved) {
                imageUri.value?.let {
                    usuarioViewModel.uploadFotoPerfil(id, it)
                    hideSheet()
                }
            }
        }
    )

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            val tmpUri = createTempImageUri()
            tmpUri?.let {
                imageUri.value = it
                takePhotoLauncher.launch(it)
            }
        } else {
            Toast.makeText(context, "É preciso autorizar o uso da câmera para capturar a foto", Toast.LENGTH_SHORT).show()
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                hideSheet()
            },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier.padding(bottom = 10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .clickable {
                            hideSheet()

                            //Fotografar.
                            val cameraPermission = Manifest.permission.CAMERA
                            if (ContextCompat.checkSelfPermission(
                                    context,
                                    cameraPermission
                                ) == PackageManager.PERMISSION_GRANTED
                            ) {
                                val tmpUri = createTempImageUri()
                                tmpUri?.let {
                                    imageUri.value = it
                                    takePhotoLauncher.launch(it)
                                }
                            } else {
                                cameraPermissionLauncher.launch(cameraPermission)
                            }
                        }
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text("Fotografar")
                }

                Row(
                    modifier = Modifier
                        .clickable {
                            hideSheet()

                            //Selecionar imagem da galeria.
                            imagePicker.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        }
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text("Selecionar da galeria")
                }

                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }

    if (usuarioState is ApiState.Loading ||
        usuarioEditaState is ApiState.Loading ||
        usuarioUploadFotoState is ApiState.Loading)
    {
        LoadScreen()
    }

    LaunchedEffect(Unit) {
        usuarioViewModel.getUsuarioById(id)
    }
}



