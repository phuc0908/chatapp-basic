package com.example.chatapp.screens.settings

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chatapp.ui.components.RoundIconButton
import com.example.chatapp.viewmodel.ActiveStatusViewModel
import com.example.chatapp.viewmodel.StatusOption
import com.google.firebase.auth.FirebaseUser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveStatusScreen(
    navController: NavController,
    currentStatus: StatusOption?,
    currentUser: FirebaseUser?,
    viewModel : ActiveStatusViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),

                title = {
                    Row (
                        Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(text = "Active status")
                    }
                },
                navigationIcon = {
                    RoundIconButton(
                        null,
                        imageVector = Icons.Default.ArrowBack,
                        modifier = Modifier
                            .fillMaxHeight(),
                        onClick = { navController.popBackStack() },
                    )
                }
            )
        }
    ) {innerPadding ->
        Column (
            Modifier.padding(innerPadding)
        ){
            Row {
                var isOn by remember { mutableStateOf(currentStatus == StatusOption.ON
                        || currentStatus == null) }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clickable {
                            isOn = !isOn
                            val newStatus = if (isOn) StatusOption.ON else StatusOption.OFF
                            if (currentUser != null) {
                                viewModel.updateActiveStatus(currentUser,newStatus)
                                Log.d("updateActiveStatusInScreen",newStatus.name)
                            }
                        }
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    Text(text = "Shown when you're active")
                    Switch(
                        checked = isOn,
                        onCheckedChange = {
                            isOn = it
                            val newStatus = if (isOn) StatusOption.ON else StatusOption.OFF
                            if (currentUser != null) {
                                viewModel.updateActiveStatus(currentUser,newStatus)
                                Log.d("updateActiveStatusInScreen",newStatus.name)
                            }
                        },
                    )
                }
            }
        }
    }
}

