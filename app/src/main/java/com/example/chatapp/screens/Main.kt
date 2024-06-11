package com.example.chatapp.screens

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.Downloader
import com.example.chatapp.model.Account
import com.example.chatapp.ui.theme.ChatApp_DACS3Theme
import com.example.chatapp.ui.animations.EnterAnimation
import com.example.chatapp.viewmodel.CallViewModel
import com.example.chatapp.viewmodel.ContactViewModel
import com.example.chatapp.viewmodel.HomeViewModel
import com.example.chatapp.viewmodel.MessageViewModel
import com.example.chatapp.viewmodel.SearchViewModel
import com.example.chatapp.screens.settings.AccountSettingScreen
import com.example.chatapp.screens.settings.ActiveStatusScreen
import com.example.chatapp.screens.settings.DarkThemeScreen
import com.example.chatapp.screens.settings.EditName
import com.example.chatapp.screens.settings.SettingScreen
import com.example.chatapp.viewmodel.AuthViewModel
import com.example.chatapp.ui.animations.BottomToTopAnimation
import com.example.chatapp.viewmodel.AccountViewModel
import com.example.chatapp.viewmodel.ActiveStatusViewModel
import com.example.chatapp.viewmodel.InfoViewModel


enum class ThemeOption {
    LIGHT, DARK, SYSTEM
}

@Composable
fun Main() {
//    ViewModel
    val context = LocalContext.current
    val accountViewModel = AccountViewModel()
    val statusViewModel = ActiveStatusViewModel()
    val authViewModel = AuthViewModel(context)

//    Navigation
    val navController = rememberNavController()
    var themeOption by remember { mutableStateOf(ThemeOption.SYSTEM) }
    var statusOption = statusViewModel.statusOption
    var currentAccount by remember { mutableStateOf<Account?>(null) }
    val currentUser = authViewModel.currentUser

    val downloader = Downloader(context)


    ChatApp_DACS3Theme(
        useSystemTheme = themeOption == ThemeOption.SYSTEM,
        darkTheme = themeOption == ThemeOption.DARK
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
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
                            currentAccount = currentAccount,
                            navController = navController,
                            openSearch = {
                                navController.navigate(Destination.Search.route)
                            },
                            openMyInfo = {
                                navController.navigate(Destination.Setting.route)
                            },
                            openChat = { uid->
                                navController.navigate(MessageScreenNavigation.createRoute(uid))
                            }
                        )
                        LaunchedEffect(currentUser) {
                            currentUser?.let { user ->
                                accountViewModel.getAccount(user, context) { account ->
                                    currentAccount = account
                                }
                            }
                        }
                        LaunchedEffect(currentAccount) {
                            currentAccount?.let {
                                viewModel.fetchAccountListWithLastMessages(it.uid)
                            }
                        }
                    }
                }
                composable(
                    route = MessageScreenNavigation.route,
                    arguments = MessageScreenNavigation.arguments()
                ){
                    val viewModel: MessageViewModel = remember { MessageViewModel(context) }
                    val uid = it.arguments?.getString("uidArg") ?: throw Exception("")

                    EnterAnimation {
                        if (currentUser != null) {
                            MessageScreen(
                                viewModel = viewModel,
                                navController,
                                downloader,
                                curentid = currentUser.uid,
                                friendid = uid,
                                context = context
                            )
                            LaunchedEffect(currentAccount) {
                                currentAccount?.let {
                                    viewModel.fetchMessage(currentUser.uid,uid)
                                }
                            }
                        }else{
                            navController.navigate(Destination.SignIn.route)
                            Toast.makeText(
                                context,
                                "Error-1: currentUser is null",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                composable(
                    route = InfoScreenNavigation.route,
                    arguments = InfoScreenNavigation.arguments()
                ){
                    val uid = it.arguments?.getString("uidArg") ?: throw Exception("")

                    val viewModel: InfoViewModel = remember { InfoViewModel() }
                    BottomToTopAnimation {
                        InfoScreen(
                            viewModel,
                            popBackStack = {
                                navController.popBackStack()
                            },
                            navController,
                            uid = uid
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
                    route = Destination.Search.route,
                ){
                    val viewModel: SearchViewModel = remember { SearchViewModel() }
                    EnterAnimation {
                        SearchScreen(
                            viewModel = viewModel,
                            navController,
                            popBackStack = {
                                navController.popBackStack()
                            },
                            openChat = { uid->
                                navController.navigate(MessageScreenNavigation.createRoute(uid))
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
                        LaunchedEffect(currentUser) {
                            currentUser?.let { user ->
                                accountViewModel.getAccount(user, context) { account ->
                                    currentAccount = account
                                }
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
                        AccountSettingScreen (navController,authViewModel)
                    }
                }
                composable(Destination.EditName.route){
                    EnterAnimation {
                        EditName(navController,authViewModel)
                    }
                }
                composable(Destination.ActiveStatus.route){
                    EnterAnimation {
                        ActiveStatusScreen (
                            navController,
                            statusOption.value,
                            currentUser,
                            statusViewModel
                        )
                    }
                }
            }
        }
    }
}

sealed class Destination(val route: String){
    data object Splash: Destination("splash")
    data object Home: Destination("home_route")
    data object SignIn: Destination("sign_in")
    data object SignUp: Destination("sign_up")
    data object Message: Destination("message_route")
    data object Setting: Destination("setting")
    data object DarkTheme: Destination("dark_theme")
    data object Contact: Destination("contact")
    data object Call: Destination("call")
    data object Search: Destination("search")
    data object Info: Destination("info")
    data object AccountSetting: Destination("account_setting")
    data object EditName: Destination("edit_name")
    data object ActiveStatus: Destination("active_status")
}
