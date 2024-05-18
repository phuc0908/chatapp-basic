package com.example.chatapp.ui.screens.signIn

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.EmailPasswordActivity
import com.example.chatapp.ui.components.ErrorDialog
import com.example.chatapp.ui.components.GoogleButton
import com.example.chatapp.ui.theme.Green1
import com.fatherofapps.jnav.annotations.JNav
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@JNav(
    name = "SignInScreenNavigation",
    baseRoute = "sign_in_route",
    destination = "sign_in_destination",
)
fun validateInput(username: String, password: String): Boolean {
    return !(username.isEmpty() || password.isEmpty())
}
private var auth: FirebaseAuth = Firebase.auth


@Composable
fun SignInScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val focusManager = LocalFocusManager.current

    val username by authViewModel.userName.collectAsState()
    val password by authViewModel.password.collectAsState()
    val context = LocalContext.current


    var currentProgress by remember { mutableFloatStateOf(0f) }
    var loading by remember { mutableStateOf(false) }

    var showDialog by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (loading) {
            LinearProgressIndicator(
                progress = currentProgress ,
                modifier = Modifier.fillMaxWidth(),
            )
        }else{
            OutlinedTextField(
                value = username,
                onValueChange = {
                    authViewModel.updateUserName(it)
                },
                label = {
                    Text("Your email")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )
            OutlinedTextField(
                value = password,
                onValueChange = {
                    authViewModel.updatePassword(it)
                },
                label = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
            )
            Button(
                onClick = {
                    authViewModel.signIn(navController)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors()
            ) {
                Text("Sign In", color = Color.White)
            }
            Text(
                "Don't have an account? Sign Up",
                color = Green1,
                modifier = Modifier
                    .clickable {
                        navController.navigate("signUp")
                    }
                    .padding(top = 8.dp)
            )

        }
        if (showDialog) {
            ErrorDialog(onDismiss = { showDialog = false })
        }
    }
}

suspend fun loadProgress(updateProgress: (Float) -> Unit) {
    for (i in 1..3) {
        updateProgress(i.toFloat() / 3)
        delay(200)
    }
}



@Preview
@Composable
fun PreviewSignInScreen() {
    val navController = rememberNavController()
    SignInScreen(
        navController = navController,
        authViewModel = AuthViewModel(context = LocalContext.current)
        )
}


