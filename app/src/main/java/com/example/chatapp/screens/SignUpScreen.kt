package com.example.chatapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.chatapp.viewmodel.AuthViewModel
import com.example.chatapp.ui.theme.Green1
import com.example.chatapp.viewmodel.AccountViewModel

@Composable
fun SignUpScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    accountViewModel: AccountViewModel
) {
    val nickname = authViewModel.nickNameRegister
    val username = authViewModel.userNameRegister
    val password = authViewModel.passwordRegister
    val repassword = authViewModel.cfpasswordRegister

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 17.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = nickname,
            onValueChange = {
                authViewModel.updateNickName(it)
            },
            label = { Text("Your nickname") },
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
            value = username,
            onValueChange = {
                authViewModel.updateUserNameRegister(it)
            },
            label = { Text("Your email") },
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
                authViewModel.updatePasswordRegister(it)
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
            )
        )
        OutlinedTextField(
            value = repassword,
            onValueChange = {
                authViewModel.updateCfPassword(it)
            },
            label = { Text("Confirm Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        )
        Button(
            onClick = {
                authViewModel.createAccount(navController,accountViewModel)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors()
        ) {
            Text("Sign Up", color = Color.White)
        }
        Text(
            "Already have an account? Sign In",
            color = Green1,
            modifier = Modifier
                .clickable {
                    navController.navigate(Destination.SignIn.route){
                        launchSingleTop = true
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
                .padding(top = 8.dp)
        )
    }
}

@Preview
@Composable
fun PreviewLoginScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current
    SignUpScreen(
        navController = navController,
        authViewModel = AuthViewModel(LocalContext.current),
        AccountViewModel()
    )
}


