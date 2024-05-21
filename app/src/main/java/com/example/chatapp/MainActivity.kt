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
import com.example.chatapp.model.Account
import com.example.chatapp.ui.theme.ChatApp_DACS3Theme
import com.example.chatapp.ui.animations.EnterAnimation
import com.example.chatapp.screens.SplashScreen
import com.example.chatapp.screens.call.CallScreen
import com.example.chatapp.screens.call.CallViewModel
import com.example.chatapp.screens.contact.ContactScreen
import com.example.chatapp.screens.contact.ContactViewModel
import com.example.chatapp.screens.home.HomeScreen
import com.example.chatapp.screens.home.HomeViewModel
import com.example.chatapp.screens.info.InfoScreen
import com.example.chatapp.screens.info.InfoViewModel
import com.example.chatapp.screens.message.MessageScreen
import com.example.chatapp.screens.message.MessageViewModel
import com.example.chatapp.screens.search.SearchScreen
import com.example.chatapp.screens.search.SearchViewModel
import com.example.chatapp.screens.settings.AccountSettingScreen
import com.example.chatapp.screens.settings.DarkThemeScreen
import com.example.chatapp.screens.settings.EditName
import com.example.chatapp.screens.settings.SettingScreen
import com.example.chatapp.screens.signIn.AuthViewModel
import com.example.chatapp.screens.signIn.SignInScreen
import com.example.chatapp.screens.signUp.SignUpScreen
import com.example.chatapp.viewmodel.AccountViewModel
import com.google.firebase.auth.FirebaseAuth


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Main()
        }
    }
}
enum class ThemeOption {
    LIGHT, DARK, SYSTEM
}

@Composable
fun Main() {
//    ViewModel
    val accountViewModel = AccountViewModel()
//    Navigation
    val navController = rememberNavController()
    var themeOption by remember { mutableStateOf(ThemeOption.SYSTEM) }

    var currentAccount by remember { mutableStateOf<Account?>(null) }
    val context = LocalContext.current



    ChatApp_DACS3Theme(
        useSystemTheme = themeOption == ThemeOption.SYSTEM,
        darkTheme = themeOption == ThemeOption.DARK
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
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
                            authViewModel,
                            accountViewModel = accountViewModel
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
                            },


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
                composable(Destination.Contact.route){
                    EnterAnimation {
                        val viewModel: ContactViewModel =  remember { ContactViewModel() }
                        ContactScreen(viewModel = viewModel,
                            popBackStack = { navController.popBackStack() },
                            navController = navController
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
                            authViewModel,
                            currentAccount
                        )
                        LaunchedEffect(Unit) {
                            accountViewModel.setCurrentAccount(context) { account ->
                                currentAccount = account
                            }
                        }
                    }
                }
                composable(Destination.DarkTheme.route){
                    EnterAnimation {
                        DarkThemeScreen (navController,themeOption){ newThemeOption ->
                            themeOption = newThemeOption
                        }
                    }
                }
                composable(Destination.AccountSetting.route){
                    EnterAnimation {
                        AccountSettingScreen (navController)
                    }
                }
                composable(Destination.EditName.route){
                    EnterAnimation {
                        EditName(navController)
                    }
                }
            }
        }
    }
}

sealed class Destination(val route: String){
    object Splash: Destination("splash")
    object Home: Destination("home")
    object SignIn: Destination("sign_in")
    object SignUp: Destination("sign_up")
    object Message: Destination("message")
    object Setting: Destination("setting")
    object DarkTheme: Destination("dark_theme")

    object Contact: Destination("contact")
    object Call: Destination("call")
    object Search: Destination("search")
    object Info: Destination("info")
    object AccountSetting: Destination("account_setting")
    object EditAvatar: Destination("edit_avatar")
    object EditName: Destination("edit_name")



}


@Preview(showBackground = true)
@Composable
fun Preview() {
    Main()
}
