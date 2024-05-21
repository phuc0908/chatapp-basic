package com.example.chatapp.screens.settings

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.Destination
import com.example.chatapp.ui.components.RoundIconButton
import com.fatherofapps.jnav.annotations.JNav
import com.example.chatapp.R
import com.example.chatapp.viewmodel.AuthViewModel
import com.example.chatapp.viewmodel.AccountViewModel


@OptIn(ExperimentalMaterial3Api::class)

@JNav(
    name = "AccountSettingScreenNavigation",
    baseRoute = "account_setting_route",
    destination = "account_setting_destination",
)

@Composable
fun AccountSettingScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        selectedImageUri = uri
    }
    val context = LocalContext.current
    val accountViewModel = AccountViewModel(context)
    Scaffold(
        topBar =
        {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(0.dp)
                ,
                title = {
                    TopBar("Account Setting")
                },
                navigationIcon = {
                    RoundIconButton(
                        null,
                        imageVector = Icons.Default.ArrowBack,
                        modifier = Modifier
                            .fillMaxHeight(),
                        onClick = { navController.popBackStack() },
                    )
                },
                actions = {
                }
            )
        },
    ) { innerPadding->
        Column(
            Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
        ){
            Column(
                Modifier
                    .fillMaxSize()
            ) {
                RowInSetting(
                    R.drawable.blank_avatar,
                    "Set avatar",
                    null,
                    onClick = {
                        launcher.launch("image/*")
                    }
                )
                RowInSetting(
                    R.drawable.pencil,
                    "Edit name",
                    null,
                    onClick = {
                        navController.navigate(Destination.EditName.route)
                    }
                )
            }
//            End body setting
        }
    }
    selectedImageUri?.let { uri ->
        EditAvatar(
            imageUri = uri,
            onSetProfilePicture = {
                accountViewModel.uploadImageToFirebase(context, uri){ imgUri->
                    authViewModel.currentUser?.let {
                        accountViewModel.updateUserImageUri(imgUri,
                            it
                        )
                    }
                    navController.navigate(Destination.AccountSetting.route)
                }
            }
        )
    }
}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AccountSettingPreview() {
    AccountSettingScreen (
        rememberNavController(),
        AuthViewModel(LocalContext.current)
    )
}
