package com.example.chatapp.screens.call

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.ui.components.BottomNavigation
import com.example.chatapp.ui.components.RoundIconButton
import com.example.chatapp.ui.components.RowACall
import com.example.chatapp.ui.theme.Green1


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CallScreen(
    viewModel: CallViewModel,
    navController: NavController,
    openSearch:() -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),

                title = {
                    TopBar()
                },
                navigationIcon = {
                    RoundIconButton(
                        null,
                        imageVector = Icons.Default.Search,
                        modifier = Modifier
                            .fillMaxHeight()
                            .size(46.dp)
                            .aspectRatio(1f),
                        onClick = openSearch,
                    )
                },
                actions = {
                    RoundIconButton(
                        null,
                        imageVector = Icons.Default.Call,
                        modifier = Modifier
                            .fillMaxHeight()
                            .size(43.dp)
                            .aspectRatio(1f),
                        onClick = openSearch,
                    )
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
            ) {

                BottomNavigation(1,navController)
            }
        },

    ) { innerPadding ->
        LaunchedEffect(true) {
            viewModel.fetchMyCall()
        }
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            viewModel.calls?.forEach{ call ->
                RowACall(call)
            }
        }
    }
}


@Composable
fun TopBar() {
    Row (
        modifier = Modifier
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Text("Calls", color = Green1)
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CallPreview() {

    CallScreen (
        viewModel = viewModel(),
        rememberNavController(),
        openSearch = {},
    )
}