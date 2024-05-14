package com.example.chatapp_dacs3.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatapp_dacs3.model.YourRecentSearch
import com.example.chatapp_dacs3.model.YourRecommendSearch
import com.example.chatapp_dacs3.ui.components.CustomTextField
import com.example.chatapp_dacs3.ui.components.RoundIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    popBackStack: () -> Unit
) {
    val text  = remember{ mutableStateOf("") }

    Scaffold(
        topBar =
        {
            TopAppBar(
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
                    TopBarSearch(
                        text = text
                    )
                },
                navigationIcon = {
                    RoundIconButton(
                        null,
                        imageVector = Icons.Default.ArrowBack,
                        modifier = Modifier
                            .fillMaxHeight(),
                        onClick = popBackStack
                        ,
                    )
                }
            )
        }

    ) {innerPadding->
        LaunchedEffect(true) {
            viewModel.fetchYourRecentSearch()
            viewModel.fetchRecommendSearch()
        }
        Column(
            Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),

            ){
            viewModel.recentSearch?.let {
                RecentSearch(it)
            }
            viewModel.recommendSearch?.let {
                RecommendSearch(it)
            }

        }
    }
}

@Composable
fun RecommendSearch(
    list: List<YourRecommendSearch>
) {
    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(30.dp)
                .padding(top = 10.dp, start = 10.dp)
        ) {
            Text(
                text = "Gợi ý",
                Modifier
                    .fillMaxWidth()
                    .height(30.dp),
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.DarkGray
                )
            )
        }

        Column (
            Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ){
            list.forEach{user->
                OneRowInRecommandSearch(user)
            }
        }
    }
}


@Composable
fun OneRowInRecommandSearch(
    user: YourRecommendSearch
) {
    Row (
        Modifier
            .fillMaxWidth()
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        RoundIconButton(
            imageResId = user.avatar,
            imageVector = null,
            modifier = Modifier
                .size(50.dp)
            ,
            onClick = {}
        )
        Text(text = user.name, Modifier.padding(10.dp))
    }
}


@Composable
fun RecentSearch(
    list: List<YourRecentSearch>
) {
    Column (
        Modifier
            .fillMaxWidth()
    ){
//        Text
        Row(
            Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(top = 10.dp, start = 10.dp)
        ) {
            Text(
                text = "Tìm kiếm gần đây",
                Modifier
                    .fillMaxWidth()
                    .height(30.dp),
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.DarkGray
                )
            )
        }
//        List
        Column (
            Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(start = 10.dp, end = 10.dp)
        ){
            val chunkedList = list.chunked(4)
            for (row in chunkedList) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (item in row) {
                        RoundIconButton(
                            imageResId = item.avatar,
                            imageVector = null,
                            modifier = Modifier.size(90.dp)
                        ) {}

                    }
                }
            }
        }
    }
}

@Composable
fun TopBarSearch(
    text: MutableState<String>,
    ){
    val focusManager = LocalFocusManager.current

    Column(
     Modifier.fillMaxSize(),
     verticalArrangement = Arrangement.Center
    ) {
         CustomTextField(
             value = text,
             leadingIcon = {
                 Icon(
                     Icons.Filled.Search,
                     null,
                     tint = LocalContentColor.current.copy(alpha = 0.3f),
                 )
             },
             trailingIcon = null,
             modifier = Modifier
                 .background(
                     MaterialTheme.colorScheme.surface,
                     RoundedCornerShape(percent = 40)
                 )
                 .padding(2.dp)
                 .height(30.dp),
             fontSize = 14.sp,
             placeholderText = "Search",
             keyboardOptions = KeyboardOptions(
                 keyboardType = KeyboardType.Text,
                 imeAction = ImeAction.Search
             ),
             keyboardActions = KeyboardActions(
                 onDone = { focusManager.clearFocus() }
             ),
             maxLine = 1
         )
    }
}





@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchPreview() {
    SearchScreen(
        viewModel(),
        popBackStack = {}
    )
}