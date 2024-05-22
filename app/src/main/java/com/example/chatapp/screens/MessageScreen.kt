package com.example.chatapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatapp.R
import com.example.chatapp.model.Message
import com.example.chatapp.viewmodel.MessageViewModel
import com.example.chatapp.ui.components.ImageMessage
import com.example.chatapp.ui.components.Message
import com.example.chatapp.ui.components.RoundIconButton
import com.example.chatapp.ui.components.TextChat
import com.example.chatapp.ui.components.CustomTextField
import com.example.chatapp.ui.components.TextNameUser
import com.fatherofapps.jnav.annotations.JNav
import com.fatherofapps.jnav.annotations.JNavArg

@OptIn(ExperimentalMaterial3Api::class)

@JNav(
    name = "MessageScreenNavigation",
    baseRoute = "message_route",
    destination = "message_destination",
    arguments = [
        JNavArg(name = "uid", type = String::class)
    ]
)
@Composable
fun MessageScreen(
    viewModel: MessageViewModel,
    popBackStack: () -> Unit,
    curentid: String,
    friendid: String
) {

    val text  = remember{ mutableStateOf("") }
    val messages by viewModel.messageList

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
                        onClick = popBackStack,
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
                BottomBarMes(text = text) { message ->
                    viewModel.sendMessage(message,curentid, friendid, 0)
                }
            }
        },
    ) { innerPadding ->
        LaunchedEffect(true) {
            viewModel.fetchMessage(curentid,friendid)
        }

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
            MessageListWithAFriend(messages, curentid, friendid)
        }
    }
}

@Composable
fun MessageListWithAFriend(
    list: List<Message>,
    curentid: String,
    friendid: String
) {
    val typeText = 0
    val typeImage = 1

    list.forEach{mes->
        if(mes.type == typeText){
            Message(
                message = mes.message,
                isMyMessage = mes.idFrom == curentid
            )
        }else if(mes.type == typeImage){
            ImageMessage(
                imageUrl = mes.message,
                isMyImage = mes.idFrom == curentid
            )
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
                .clickable {

                },
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
    onSendMessage: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ){
//        RoundIconButton(
//            imageResId = R.drawable.file,
//            imageVector = null,
//            modifier = Modifier
//                .size(45.dp)
//                .rotate(15f)
//        ) {
//        }
        RoundIconButton(
            imageResId = R.drawable.camera,
            imageVector = null,
            modifier = Modifier.size(45.dp)
        ) {
        }
        CustomTextField(
            value = text,
            leadingIcon = {
            },
            trailingIcon = null,
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.surface,
                    RoundedCornerShape(percent = 20)
                )
                .padding(4.dp)
                .width(240.dp),
            fontSize = 14.sp,
            placeholderText = "Nhắn tin",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            maxLine = 4
        )


        RoundIconButton(
            imageResId = R.drawable.sendd,
            imageVector = null,
            modifier = Modifier.size(50.dp)
        ) {
            onSendMessage(text.value)
            text.value = ""
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MesPreview() {
        MessageScreen(
            viewModel(),
            popBackStack = {},
            "",
            ""
        )
}