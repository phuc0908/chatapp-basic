package com.example.chatapp.screens.signIn


import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.navigation.NavController
import com.example.chatapp.Destination
import com.google.firebase.auth.FirebaseUser

class AuthViewModel(
    val context: Context
) : ViewModel(){
//    Sign In
    private val _userName = MutableStateFlow("")
    val userName = _userName.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

//    Sign Up
    private val _userNameRegister = MutableStateFlow("")
    val userNameRegister = _userNameRegister.asStateFlow()

    private val _passwordRegister = MutableStateFlow("")
    val passwordRegister = _passwordRegister.asStateFlow()

    private val _nickNameRegister = MutableStateFlow("")
    val nickNameRegister = _nickNameRegister.asStateFlow()

    private val _cfpasswordRegister = MutableStateFlow("")
    val cfpasswordRegister = _cfpasswordRegister.asStateFlow()

//    OTHER
    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Empty)
    val uiState = _uiState.asStateFlow()

    private val _loginState = MutableStateFlow(false)
    val loginState = _loginState.asStateFlow()

    private val auth = FirebaseAuth.getInstance()

    public fun getAuthInstance(): FirebaseAuth {
        return auth
    }

    var currentUser: FirebaseUser? = null
        set
//  SIGN IN
    fun updateUserName(newName: String) {
        _userName.value = newName
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }
//    SIGN UP
    fun updateUserNameRegister(newName: String) {
        _userNameRegister.value = newName
    }

    fun updatePasswordRegister(newPassword: String) {
        _passwordRegister.value = newPassword
    }
    fun updateNickName(newName: String) {
        _nickNameRegister.value = newName
    }
    fun updateCfPassword(newPassword: String) {
        _cfpasswordRegister.value = newPassword
    }


    fun createAccount(navController: NavController) {
        auth.createUserWithEmailAndPassword(userName.value, password.value)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    Toast.makeText(
                        context,
                        "Register Successful.",
                        Toast.LENGTH_SHORT
                    ).show()
                    val user = auth.currentUser
                    updateUI(user)

                    navController.navigate(Destination.SignIn.route)

                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        context,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }
    fun signIn(navController: NavController) {

        if (!isEmailValid(userName.value)) {
            Toast.makeText(context, "Invalid email address.", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isPasswordValid(password.value)) {
            Toast.makeText(context, "Password must be at least 6 characters.", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(userName.value, password.value)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    _loginState.value = true
                    Toast.makeText(
                        context,
                        "Authentication successful.",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d(TAG, "signInWithEmail:success")
                    auth.currentUser?.let { it1 ->
                        Log.d("s", it1.uid)
                    }
                    val user = auth.currentUser
                    updateUI(user)

                    navController.navigate(Destination.Home.route){
                        popUpTo(Destination.SignIn.route) {
                            inclusive = true
                        }
                    }
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        context,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    fun signOut(navController: NavController){
        auth.signOut()
        navController.navigate(Destination.SignIn.route) {
            popUpTo(Destination.Home.route) {
                inclusive = true
            }
        }
        updateUI(null)
    }

    private fun updateUI(user: FirebaseUser?) {
        currentUser = user
    }

    private fun reload() {

    }

    companion object {
        private const val TAG = "EmailPassword"
    }
    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 6
    }
}

sealed class AuthUiState {
    object Empty : AuthUiState()
    object Loading : AuthUiState()
    object Success : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}