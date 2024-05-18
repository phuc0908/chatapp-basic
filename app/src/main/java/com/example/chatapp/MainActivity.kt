package com.example.chatapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.ui.theme.ChatApp_DACS3Theme
import com.example.chatapp.ui.animations.EnterAnimation
import com.example.chatapp.ui.components.ErrorDialog
import com.example.chatapp.ui.screens.SplashScreen
import com.example.chatapp.ui.screens.call.CallScreen
import com.example.chatapp.ui.screens.call.CallViewModel
import com.example.chatapp.ui.screens.home.HomeScreen
import com.example.chatapp.ui.screens.home.HomeViewModel
import com.example.chatapp.ui.screens.info.InfoScreen
import com.example.chatapp.ui.screens.info.InfoViewModel
import com.example.chatapp.ui.screens.message.MessageScreen
import com.example.chatapp.ui.screens.message.MessageScreenNavigation
import com.example.chatapp.ui.screens.message.MessageViewModel
import com.example.chatapp.ui.screens.search.SearchScreen
import com.example.chatapp.ui.screens.search.SearchScreenNavigation
import com.example.chatapp.ui.screens.search.SearchViewModel
import com.example.chatapp.ui.screens.settings.SettingScreen
import com.example.chatapp.ui.screens.signIn.AuthViewModel
import com.example.chatapp.ui.screens.signIn.SignInScreen
import com.example.chatapp.ui.screens.signUp.SignUpScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Main()
        }
    }
}

@Composable
fun Main() {
    val navController = rememberNavController()
    var showDialog by remember { mutableStateOf(false) }
    val auth = FirebaseAuth.getInstance()
    val currentUser by remember { mutableStateOf(auth.currentUser) }

    ChatApp_DACS3Theme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val context = LocalContext.current
            val authViewModel = AuthViewModel(context)


            NavHost(navController = navController, startDestination = Destination.Splash.route ){


                composable(
                    route = Destination.Splash.route
                ) {
                    EnterAnimation {
                        SplashScreen(navController,authViewModel)
                    }
                }

                composable(Destination.SignIn.route){
                    EnterAnimation {
                        SignInScreen (
                            navController = navController,
                            authViewModel
                        )
                    }
                }

                composable(Destination.SignUp.route){
                    EnterAnimation {
                        SignUpScreen (
                            navController = navController,
                            authViewModel
                        )
                    }
                }
                composable(Destination.Home.route){
                    val viewModel: HomeViewModel = remember { HomeViewModel() }
                    EnterAnimation {
                        HomeScreen(
                            viewModel = viewModel,

                            authViewModel,
                            navController,
                            openFriendChat = {
                                navController.navigate(Destination.Message.route)
                            },
                            openSearch = {
                                navController.navigate(Destination.Search.route)
                            },
                            openMyinfo = {
                                navController.navigate(Destination.Info.route)
                            }

                        )
                    }
                }

                composable(Destination.Call.route){
                    val viewModel: CallViewModel = remember { CallViewModel() }
                    EnterAnimation {
                        CallScreen(
                            viewModel = viewModel,
                            navController,
                            openSearch = {
                                navController.navigate(Destination.Search.route)
                            },

                        )
                    }
                }

                composable(
                    route = Destination.Message.route,
                    ){
                    val viewModel: MessageViewModel = remember { MessageViewModel() }
                    EnterAnimation {
                        MessageScreen(
                        viewModel = viewModel,
                            popBackStack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }

                composable(
                    route = Destination.Search.route,
                ){
                    val viewModel: SearchViewModel = remember { SearchViewModel() }
                    EnterAnimation {
                        SearchScreen(
                            viewModel = viewModel,
                            popBackStack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
                composable(Destination.Info.route){
                    val viewModel: InfoViewModel = remember { InfoViewModel() }
                    EnterAnimation {
                        InfoScreen(
                            viewModel = viewModel,
                            popBackStack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }

                composable(Destination.Setting.route){
                    EnterAnimation {
                        SettingScreen(
                            popBackStack = {
                                navController.popBackStack()
                            },
                            navController,
                            authViewModel
                        )
                    }
                }
            }
        }
    }
}

sealed class Destination(val route: String){
    object Splash: Destination("splash")
    object Home: Destination("home")
    object SignIn: Destination("signin")
    object SignUp: Destination("signup")
    object Message: Destination("message")
    object Setting: Destination("setting")
    object Contact: Destination("contact")
    object Call: Destination("call")
    object Search: Destination("search")
    object Info: Destination("info")

}


@Preview(showBackground = true)
@Composable
fun Preview() {
    Main()
}
