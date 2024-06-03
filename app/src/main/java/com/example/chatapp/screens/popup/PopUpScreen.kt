package com.example.chatapp.screens.popup


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold

import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatapp.R
import com.example.chatapp.ui.components.ImageMessage
import com.example.chatapp.ui.components.Message
import com.example.chatapp.ui.components.RoundIconButton
import com.example.chatapp.ui.components.TextChat
import com.example.chatapp.ui.components.TextNameUser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageScreen() {
    val text  = remember{ mutableStateOf("") }


    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onTertiary,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(0.dp)
                ,
                title = {
                    TopBarMes()
                },
                navigationIcon = {
                    RoundIconButton(
                        null,
                        imageVector = Icons.Default.ArrowBack,
                        modifier = Modifier
                            .fillMaxHeight(),
                        onClick = {
//                            navController.popBackStack()
                        },
                    )
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.onTertiary,
                contentColor = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .border(
                        border = BorderStroke(
                            width = 0.1.dp, color = Color.Gray
                        )
                    )
            ) {
                BottomBarMes(text = text)
            }
        },
    ) { innerPadding ->
        val scrollState = rememberScrollState()

        LaunchedEffect(Unit) {
            scrollState.animateScrollTo(scrollState.maxValue)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState),
        ) {

        }
    }
}

@Composable
fun TopBarMes(
) {
    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Row (
            modifier = Modifier
                .fillMaxHeight()
                .clickable() {},
            verticalAlignment = Alignment.CenterVertically
        ){

            RoundIconButton(
                imageResId = R.drawable.newuser,
                null,
                modifier = Modifier.size(60.dp)
            ) {

            }
            Column {
                TextNameUser("Nguyễn HPhúc")
                TextChat(text = "Active Now")
            }
        }

        Row (
            modifier = Modifier
                .fillMaxHeight()
                .width(50.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            RoundIconButton(
                imageResId = R.drawable.info,
                imageVector = null,
                modifier = Modifier.size(50.dp)
            ) {

            }
        }
    }
}

@Composable
fun BottomBarMes(
    text: MutableState<String>,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(bottom = 10.dp)
        ,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ){
        RoundIconButton(
            imageResId = R.drawable.file,
            imageVector = null,
            modifier = Modifier
                .size(45.dp)
                .rotate(15f)
        ) {
        }

//        TextfieldChatbox(text = text)

        RoundIconButton(
            imageResId = R.drawable.camera,
            imageVector = null,
            modifier = Modifier.size(45.dp)
        ) {
        }
        RoundIconButton(
            imageResId = R.drawable.mic,
            imageVector = null,
            modifier = Modifier.size(45.dp)
        ) {
        }
    }
}





@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PopUpPreview() {
    MessageScreen()
}