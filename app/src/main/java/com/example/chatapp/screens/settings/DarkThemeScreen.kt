package com.example.chatapp.screens.settings

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
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.ThemeOption

import com.example.chatapp.ui.components.RoundIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DarkThemeScreen(
navController: NavController,
currentTheme: ThemeOption,
onThemeChange: (ThemeOption) -> Unit
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
                        Text(text = "Dark mode")
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
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                RadioButton(
                    selected = currentTheme == ThemeOption.LIGHT,
                    onClick = { onThemeChange(ThemeOption.LIGHT) }
                )
                Text(text = "Off")
            }
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                RadioButton(
                    selected = currentTheme == ThemeOption.DARK,
                    onClick = { onThemeChange(ThemeOption.DARK) }
                )
                Text(text = "On")
            }
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                RadioButton(
                    selected = currentTheme == ThemeOption.SYSTEM,
                    onClick = { onThemeChange(ThemeOption.SYSTEM) }
                )
                Text(text = "System")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DarkPreview() {
    DarkThemeScreen(
        rememberNavController(),
        ThemeOption.DARK,
    ){

    }
}