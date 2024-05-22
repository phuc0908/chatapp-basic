package com.example.chatapp.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatapp.R
import com.example.chatapp.model.Account
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
    val typeText = 0
    val typeImage = 1
    val context = LocalContext.current
    val text  = remember{ mutableStateOf("") }
    val messages by viewModel.messageList


    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        selectedImageUri = uri
    }

    val listState = rememberLazyListState()
    LaunchedEffect(messages) {
        listState.animateScrollToItem(messages.size)
    }

    LaunchedEffect(Unit) {
        viewModel.getFriend(friendid)
    }
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
                    viewModel.user.value?.let {
                        TopBarMes(it)
                    }
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
                val focusManager = LocalFocusManager.current

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    RoundIconButton(
                        imageResId = R.drawable.camera,
                        imageVector = null,
                        modifier = Modifier.size(45.dp)
                    ) {
                        launcher.launch("image/*")
                    }
                    CustomTextField(
                        value = text,
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.surface,
                                RoundedCornerShape(percent = 20)
                            )
                            .padding(4.dp)
                            .width(240.dp),
                        fontSize = 14.sp,
                        placeholderText = "Send message...",
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        ),
                        maxLine = 4
                    )
                    if(text.value!=""){
                        RoundIconButton(
                            imageResId = R.drawable.sendd,
                            imageVector = null,
                            modifier = Modifier.size(50.dp)
                        ) {
                            viewModel.sendMessage(text.value,curentid, friendid, typeText)
                            text.value = ""
                        }
                    }else{
                        Spacer( Modifier.size(50.dp))
                    }
                    selectedImageUri?.let { uri ->
                        viewModel.uploadImageMessage(context,uri){imageUrl->
                            viewModel.sendImageMessage(imageUrl,curentid,friendid,typeImage)
                            selectedImageUri = null
                        }
                    }
                }
            }
        },
    ) { innerPadding ->
        LaunchedEffect(true) {
            viewModel.fetchMessage(curentid,friendid)
        }
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            items(messages) { mes ->
                if (mes.type == 0) {
                    Message(
                        message = mes.message,
                        isMyMessage = mes.idFrom == curentid
                    )
                } else if (mes.type == 1) {
                    ImageMessage(
                        imageUrl = mes.message,
                        isMyImage = mes.idFrom == curentid
                    )
                }
            }
        }
    }
}

@Composable
fun TopBarMes(
    account: Account
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
                TextNameUser(account.nickName)
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