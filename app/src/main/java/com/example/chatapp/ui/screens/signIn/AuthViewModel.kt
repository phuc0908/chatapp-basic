package com.example.chatapp.ui.screens.signIn


import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseAuth
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.chatapp.Destination
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel(
    private val context: Context
) : ViewModel(){

    private val _nickName = MutableStateFlow("")
    val nickName = _nickName.asStateFlow()

    private val _userName = MutableStateFlow("")
    val userName = _userName.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Empty)
    val uiState = _uiState.asStateFlow()

    private val _loginState = MutableStateFlow(false)
    val loginState = _loginState.asStateFlow()

    private val auth = FirebaseAuth.getInstance()

    var currentUser: FirebaseUser? = null
        public set
    fun updateNickName(newName: String) {
        _nickName.value = newName
    }
    fun updateUserName(newName: String) {
        _userName.value = newName
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun signUp() {
        viewModelScope.launch {
            try {
                _uiState.value = AuthUiState.Loading
                auth.createUserWithEmailAndPassword(userName.value, password.value).await()
                _uiState.value = AuthUiState.Success
                _loginState.value = true
                Toast.makeText(context, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
            }

            catch (e :Exception) {
                Toast.makeText(context, "Đăng nhập không thành công!", Toast.LENGTH_SHORT).show()
                _uiState.value = AuthUiState.Error(e.message ?: "Đã xảy ra lỗi không mong muốn")
            }
        }
    }

//    fun createAccount() {
//        auth.createUserWithEmailAndPassword(userName.value, password.value)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Log.d(TAG, "createUserWithEmail:success")
//                    val user = auth.currentUser
//                    updateUI(user)
//                } else {
//                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
//                    Toast.makeText(
//                        context,
//                        "Authentication failed.",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    updateUI(null)
//                }
//            }
//    }
    fun signInS(navController: NavController) {
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
                    val user = auth.currentUser
                    updateUI(user)

                    navController.navigate(Destination.Home.route)
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


    fun signIn() {
        viewModelScope.launch {
            try {
                _uiState.value = AuthUiState.Loading
                auth.signInWithEmailAndPassword(userName.value, password.value)
                _uiState.value = AuthUiState.Success
                _loginState.value = true
                Toast.makeText(context, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
            }
            catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message ?: "Đã xảy ra lỗi không mong muốn")
                Toast.makeText(context, "Đăng nhập không thành công!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun updateUI(user: FirebaseUser?) {
        currentUser = user
    }

    private fun reload() {

    }

    companion object {
        private const val TAG = "EmailPassword"
    }
}

sealed class AuthUiState {
    object Empty : AuthUiState()
    object Loading : AuthUiState()
    object Success : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}